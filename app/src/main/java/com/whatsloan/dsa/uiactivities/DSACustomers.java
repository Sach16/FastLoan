package com.whatsloan.dsa.uiactivities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.whatsloan.dsa.R;
import com.whatsloan.dsa.customadapters.CustomRecyclerAdapterForListOfCustomersByLoans;
import com.whatsloan.dsa.interfaces.RecyclerCustomersListener;
import com.whatsloan.dsa.macros.DSAMacros;
import com.whatsloan.dsa.model.CustomersData;
import com.whatsloan.dsa.model.CustomersLoan;
import com.whatsloan.dsa.model.LeadCustHead;
import com.whatsloan.dsa.model.LoansData;
import com.whatsloan.dsa.model.MembersData;
import com.whatsloan.dsa.model.TeamMembers;
import com.whatsloan.dsa.network.Constants;
import com.whatsloan.dsa.network.RequestManager;
import com.whatsloan.dsa.uiframework.DSABaseActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by S.K. Pissay on 28/3/16.
 */
public class DSACustomers extends DSABaseActivity implements RecyclerCustomersListener, AdapterView.OnItemSelectedListener, View.OnTouchListener {

    @Nullable
    @Bind(R.id.RECYCCUSTOMERS)
    RecyclerView m_cRecycCustomers;

    @Nullable
    @Bind(R.id.NO_DATA_AVAILABLE)
    TextView m_cNoDataAvail;

    @Nullable
    @Bind(R.id.LOGIN_DATE_REL_LAY)
    RelativeLayout m_cLoginDateRelLay;

    @Nullable
    @Bind(R.id.LEAD_LOGIN_DATE)
    TextView m_cLeadLoginDate;

    @Nullable
    @Bind(R.id.LOGIN_BY_SPIN)
    Spinner m_cLoginBySpin;

    public static final int LOGIC_DATE_PICKER_ID = 111;
    private Calendar m_cCalendar;
    private DatePickerDialog m_cDatePickerDialog;
    private static boolean FILTER_CLICKED = false;
    private static int page = 1;
    private String m_cStatusGroup;

    boolean userSelect = false;

    ArrayList<String> m_cMembersList;
    HashMap<String, String> m_cMembersDic;

    ArrayAdapter<String> m_cSpinAdapter;

    private int m_cMembersPos = -1;

    private int loginMonth = -1;
    private int loginYear = -1;
    private int loginDate = -1;

    ArrayList<LoansData> m_cLoansDataList;
    private CustomRecyclerAdapterForListOfCustomersByLoans m_cRecycAdapter;
    private boolean m_cLoading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    LinearLayoutManager m_cLayoutManager;
    
    private View m_cView;

    @Override
    protected void onCreate(Bundle pSavedInstance) {
        super.onCreate(pSavedInstance);
        setContentView(R.layout.customer_list);

        /*ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.customer_list);
        m_cView = stub.inflate();*/
        ButterKnife.bind(this);

        setTitle("Customers (HL-0, PL-0)", false, true, true, false);
//        ISSWIPE = false;

        // Slider Initilization
//        m_cObjSliderMenu = (DrawerLayout) findViewById(R.id.drawer_layout);
//        m_cObjSliderMenu.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
//        m_cContainFragment = (FrameLayout) findViewById(R.id.main_content_container);

    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    private void init() {
        m_cStatusGroup = getIntent().getStringExtra(Constants.STATUSGROUP);

        m_cLoginBySpin.setOnTouchListener(this);
        m_cLoginBySpin.setOnItemSelectedListener(this);
        m_cLoansDataList = new ArrayList<>();
        m_cLayoutManager = new LinearLayoutManager(this);
        m_cLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        m_cRecycCustomers.setLayoutManager(m_cLayoutManager);
        m_cRecycCustomers.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    visibleItemCount = m_cLayoutManager.getChildCount();
                    totalItemCount = m_cLayoutManager.getItemCount();
                    pastVisiblesItems = m_cLayoutManager.findFirstVisibleItemPosition();

//                    int page = totalItemCount / 15;
                    if (m_cLoading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            m_cLoading = false;
                            Log.v("...", "Last Item Wow !");
                            //Do pagination.. i.e. fetch new data
                            int lpage = page + 1;
                            page = lpage;
                            doPagination(lpage);
                        }
                    }
                }
            }
        });

        m_cMembersList = new ArrayList<>();
        m_cMembersList.add("Login By");
        m_cSpinAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, m_cMembersList);
        m_cLoginBySpin.setAdapter(m_cSpinAdapter);

        //Calling team api
        displayProgressBar(-1, "Loading");
        HashMap<String, String> llParams = new HashMap<>();
        llParams.put(Constants.PAGINATE, Constants.ALL);
        llParams.put(Constants.INSCLUDE, Constants.MEMBERS);
        RequestManager.getInstance(this).placeRequest(Constants.USERTEAM, TeamMembers.class, this, llParams, false);

        //Calling Customers api
        HashMap<String, String> lParams = new HashMap<>();
        if(null != m_cStatusGroup){
            lParams.put(Constants.STATUSGROUP, m_cStatusGroup);
        }
        lParams.put(DSAMacros.INCLUDE, Constants.CUSTOMER_LOANS + "," + Constants.CUSTOMER_SETTINGS + "," + Constants.USER_ADDRESSES);
        if(null != m_cStatusGroup){
            lParams.put(Constants.STATUSGROUP, m_cStatusGroup);
        }
        RequestManager.getInstance(this).placeRequest(Constants.SEARCH_CUSTOMERS, LeadCustHead.class, this, lParams, false);
    }

    private void doPagination(int pPage) {
        m_cLoansDataList.add(null);
        m_cRecycAdapter.notifyItemInserted(m_cLoansDataList.size() - 1);
        HashMap<String, String> lParams = new HashMap<>();
        lParams.put(DSAMacros.PAGINATION_PAGE, "" + pPage);
        lParams.put(DSAMacros.INCLUDE, Constants.CUSTOMER_LOANS + "," + Constants.CUSTOMER_SETTINGS + "," + Constants.USER_ADDRESSES);
        if(m_cLeadLoginDate.getText().toString().contains("-")){
            lParams.put(Constants.LOGIN_DATE, DSAMacros.getDateFormatYYYYMMDD(null, m_cLeadLoginDate.getText().toString()));
        }
        if(null != m_cStatusGroup){
            lParams.put(Constants.STATUSGROUP, m_cStatusGroup);
        }
        RequestManager.getInstance(this).placeRequest(Constants.SEARCH_CUSTOMERS, LeadCustHead.class, this, lParams, false);
    }

    @OnClick(R.id.LOGIN_DATE_REL_LAY)
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.LOGIN_DATE_REL_LAY:
                showDatePickerDialog(LOGIC_DATE_PICKER_ID);
                break;
        }
    }

    @Override
    protected void handleUIMessage(Message pObjMessage) {

    }

    @Override
    public void onAPIResponse(Object response, String apiMethod) {
        switch (apiMethod){
            case Constants.USERTEAM:
                TeamMembers lTeamMembers = (TeamMembers) response;
                if (lTeamMembers.getData().getMembers().getData().size() > 0) {
                    m_cMembersList = new ArrayList<>();
                    m_cMembersDic = new HashMap<>();
                    m_cMembersList.add("Login By");
                    for (MembersData lMembersData : lTeamMembers.getData().getMembers().getData()) {
                        m_cMembersList.add(lMembersData.getFirstName() + " " + lMembersData.getLastName());
                        m_cMembersDic.put(lMembersData.getFirstName() + " " + lMembersData.getLastName(), lMembersData.getUuid());
                    }
                    m_cSpinAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, m_cMembersList);
                    m_cLoginBySpin.setAdapter(m_cSpinAdapter);
                }
//                hideDialog();
                break;
            case Constants.SEARCH_CUSTOMERS:
                if (!FILTER_CLICKED) {
                    LeadCustHead lLeadCustHead = (LeadCustHead) response;
                    if (lLeadCustHead.getData().getLoans().getData().size() > 0) {
                        updateHLPL(lLeadCustHead);
                        if (m_cLoading) {
                            for (LoansData lLoansData : lLeadCustHead.getData().getLoans().getData()) {
                                m_cLoansDataList.add(lLoansData);
                            }
                            if (null != m_cLoansDataList && m_cLoansDataList.size() > 0) {
                                m_cRecycAdapter = new CustomRecyclerAdapterForListOfCustomersByLoans(this, m_cLoansDataList, this);
                                m_cRecycCustomers.setAdapter(m_cRecycAdapter);
                            }
                        } else {
                            m_cLoansDataList.remove(m_cLoansDataList.size() - 1);
                            m_cRecycAdapter.notifyItemRemoved(m_cLoansDataList.size());
                            for (LoansData lLoansData : lLeadCustHead.getData().getLoans().getData()) {
                                m_cLoansDataList.add(lLoansData);
                            }
                            m_cRecycAdapter.notifyItemInserted(m_cLoansDataList.size());
                            m_cLoading = true;
                        }
                        m_cNoDataAvail.setVisibility(View.GONE);
                    } else {
                        if (m_cLoansDataList.size() > 0) {
                            m_cLoansDataList.remove(m_cLoansDataList.size() - 1);
                            m_cRecycAdapter.notifyItemRemoved(m_cLoansDataList.size());
                            m_cLoading = false;
                            page = 1;
                        } else {
                            m_cNoDataAvail.setVisibility(View.VISIBLE);
                        }
                    }
                    hideDialog();
                } else {
                    LeadCustHead lLeadCustHead = (LeadCustHead) response;
                    if (lLeadCustHead.getData().getLoans().getData().size() > 0) {
                        /*m_cRecycCustomers.notifyItemRangeRemoved(0, m_cLoansDataList.size());
                        m_cLoansDataList = new ArrayList<>();*/
                        updateHLPL(lLeadCustHead);
                        if (m_cLoading) {
                            m_cLoansDataList.clear();
                            m_cRecycAdapter.notifyDataSetChanged();
                            for (LoansData lLoansData : lLeadCustHead.getData().getLoans().getData()) {
                                m_cLoansDataList.add(lLoansData);
                            }
                            if (null != m_cLoansDataList && m_cLoansDataList.size() > 0) {
                                m_cRecycAdapter.notifyDataSetChanged();
                            }
                        } else {
                            m_cLoansDataList.remove(m_cLoansDataList.size() - 1);
                            m_cRecycAdapter.notifyItemRemoved(m_cLoansDataList.size());
                            for (LoansData lLoansData : lLeadCustHead.getData().getLoans().getData()) {
                                m_cLoansDataList.add(lLoansData);
                            }
                            m_cRecycAdapter.notifyItemInserted(m_cLoansDataList.size());
                            m_cLoading = true;
                        }
                        FILTER_CLICKED = false;
                        m_cNoDataAvail.setVisibility(View.GONE);
                    } else {
                        if (!m_cLoading) {
                            m_cLoansDataList.remove(m_cLoansDataList.size() - 1);
                            m_cRecycAdapter.notifyItemRemoved(m_cLoansDataList.size());
                            m_cLoading = false;
                        } else {
                            if (m_cLoansDataList.size() > 0) {
                                m_cLoansDataList.clear();
                                m_cRecycAdapter.notifyDataSetChanged();
                                hideDialog();
                                m_cLoading = false;
                                page = 1;
                            } else {
                                m_cNoDataAvail.setVisibility(View.VISIBLE);
                            }
                        }
                        FILTER_CLICKED = false;
                    }
                    hideDialog();
                }
                break;
            default:
                if(apiMethod.contains("/")){
                    CustomersLoan lCustomersLoan = (CustomersLoan) response;
                    Intent lIntent = new Intent(this, DSACustomerProfileLoanTask.class);
                    lIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    lIntent.putExtra("CUSTOMER_ID", lCustomersLoan.getData());
                    startActivity(lIntent);
                    reFreshDate();
                }else {
                    super.onAPIResponse(response, apiMethod);
                }
                hideDialog();
                break;
        }
    }

    private void reFreshDate() {
        loginMonth = -1;
        loginDate = -1;
        loginYear = -1;
        m_cLeadLoginDate.setText("Login Date");
    }

    private void updateHLPL(LeadCustHead pLeadCustHead) {
        int HL = pLeadCustHead.getData().getLoanTypeCount().getData().getCustomers().getData().getHL();
        int PL = pLeadCustHead.getData().getLoanTypeCount().getData().getCustomers().getData().getPL();
        setTitle("Customers (HL-"+HL+", PL-"+PL+")", false, true, true, false);
    }

    @Override
    public void onErrorResponse(VolleyError error, String apiMethod) {
        super.onErrorResponse(error, apiMethod);
        hideDialog();
    }

    @Override
    public void onInfoClick(int pPostion, CustomersData pCustomersData, View pView) {
        displayProgressBar(-1, "Loading...");
        HashMap<String, String> lParams = new HashMap<>();
        lParams.put(DSAMacros.INCLUDE, Constants.SETTINGS + "," + Constants.LOANS  + "," + Constants.ADDRESSES + ","
                + Constants.ATTACHMENTS + "," + Constants.LOANS_ATTACHMENTS + "," + Constants.LOANS_HISTORY
                + "," + Constants.LOANS_AGENT_BANKS + "," + Constants.LOANS_TOTAL_TAT + "," + Constants.LOANS_LOAN_STATUS_TAT);
        RequestManager.getInstance(this).placeRequest(Constants.apiMethodEx(Constants.CUSTOMERS, pCustomersData.getUuid()),
                CustomersLoan.class, this, lParams, false);
    }

    private void showDatePickerDialog(int pId) {
        m_cCalendar = Calendar.getInstance();
        switch (pId) {
            case LOGIC_DATE_PICKER_ID:
                if (loginDate > 0)
                    m_cDatePickerDialog = new DatePickerDialog(this, myLoginDateListener, loginYear,
                            loginMonth, loginDate);
                else
                    m_cDatePickerDialog = new DatePickerDialog(this, myLoginDateListener, m_cCalendar.get(Calendar.YEAR),
                            m_cCalendar.get(Calendar.MONTH), m_cCalendar.get(Calendar.DAY_OF_MONTH));
//                m_cDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                break;
        }
        m_cDatePickerDialog.show();
    }

    private DatePickerDialog.OnDateSetListener myLoginDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int year, int month, int day) {
            String lmonth = String.format("%02d", month + 1);
            loginMonth = Integer.parseInt(lmonth) -1;
            String lday = String.format("%02d", day);
            loginDate = Integer.parseInt(lday);
            loginYear = year;
            m_cLeadLoginDate.setText(lday + "-" + lmonth + "-" + year);
            m_cDatePickerDialog.dismiss();

            // clear the list before calling
            try {
                m_cLoansDataList.clear();
                m_cRecycAdapter.notifyDataSetChanged();
            }catch (Exception e){
                e.printStackTrace();
            }

            displayProgressBar(-1, "Loading");
            HashMap<String, String> lParams = new HashMap<>();
            lParams.put(DSAMacros.INCLUDE, Constants.LOANS);
            if (m_cMembersPos > 0) {
                lParams.put(Constants.USER_UUID, m_cMembersDic.get(m_cMembersList.get(m_cMembersPos)));
            }
            if(m_cLeadLoginDate.getText().toString().contains("-")){
                lParams.put(Constants.LOGIN_DATE, DSAMacros.getDateFormatYYYYMMDD(null, m_cLeadLoginDate.getText().toString()));
            }
            if(null != m_cStatusGroup){
                lParams.put(Constants.STATUSGROUP, m_cStatusGroup);
            }
            RequestManager.getInstance(DSACustomers.this).placeRequest(Constants.SEARCH_CUSTOMERS, LeadCustHead.class, DSACustomers.this, lParams, false);
            m_cLoading = true;
            FILTER_CLICKED = true;
        }
    };

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (userSelect) {
            switch (parent.getId()) {
                case R.id.LOGIN_BY_SPIN:
                    m_cMembersPos = position;
                    if (position >= 0) {
                        // clear the list before calling
                        try {
                            m_cLoansDataList.clear();
                            m_cRecycAdapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        displayProgressBar(-1, "Loading");
                        HashMap<String, String> lParams = new HashMap<>();
                        lParams.put(DSAMacros.INCLUDE, Constants.CUSTOMER_LOANS + "," + Constants.CUSTOMER_SETTINGS + "," + Constants.USER_ADDRESSES);
                        if (m_cMembersPos > 0) {
                            lParams.put(Constants.USER_UUID, m_cMembersDic.get(m_cMembersList.get(m_cMembersPos)));
                        }
                        if (m_cLeadLoginDate.getText().toString().contains("-")) {
                            lParams.put(Constants.LOGIN_DATE, DSAMacros.getDateFormatYYYYMMDD(null, m_cLeadLoginDate.getText().toString()));
                        }
                        if (null != m_cStatusGroup) {
                            lParams.put(Constants.STATUSGROUP, m_cStatusGroup);
                        }
                        RequestManager.getInstance(this).placeRequest(Constants.SEARCH_CUSTOMERS, LeadCustHead.class, this, lParams, false);
                        m_cLoading = true;
                        FILTER_CLICKED = true;
                    }
                    break;
            }
            userSelect = false;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        userSelect = true;
        return false;
    }
}