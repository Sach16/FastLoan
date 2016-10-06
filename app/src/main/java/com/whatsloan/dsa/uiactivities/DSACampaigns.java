package com.whatsloan.dsa.uiactivities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.whatsloan.dsa.R;
import com.whatsloan.dsa.customadapters.CustomRecyclerAdapterForListOfCampaigns;
import com.whatsloan.dsa.interfaces.RecyclerCampaignListener;
import com.whatsloan.dsa.macros.DSAMacros;
import com.whatsloan.dsa.model.Campaigns;
import com.whatsloan.dsa.model.CampaignsData;
import com.whatsloan.dsa.network.Constants;
import com.whatsloan.dsa.network.RequestManager;
import com.whatsloan.dsa.uiframework.DSABaseActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by S.K. Pissay on 4/4/16.
 */
public class DSACampaigns extends DSABaseActivity implements RecyclerCampaignListener {

    private View m_cView;
    private CustomRecyclerAdapterForListOfCampaigns m_cRecyclerAdapter;
    private boolean m_cLoading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private LinearLayoutManager m_cLayoutManager;
    ArrayList<CampaignsData> m_cCampList;
    private static boolean FILTER_CLICKED = false;
    private static int page = 1;

    private int fromMonth = -1;
    private int fromYear = -1;
    private int fromDate = -1;
    private int toMonth = -1;
    private int toDate = -1;
    private int toYear = -1;

    @Nullable
    @Bind(R.id.RECYC_CAMPAIGNS)
    RecyclerView m_cRecyclerCamps;

    @Nullable
    @Bind(R.id.NO_DATA_AVAILABLE)
    TextView m_cNoData;

    @Nullable
    @Bind(R.id.RESULTS_COUNT_TXT)
    TextView m_cResultsCount;

    @Nullable
    @Bind(R.id.SPIN_FILT_BUTTON_TXT)
    TextView m_cFiltbuttontxt;

    @Nullable
    @Bind(R.id.FROM_DATE_LAY)
    RelativeLayout m_cFromLay;

    @Nullable
    @Bind(R.id.TO_DATE_LAY)
    RelativeLayout m_cToLay;

    @Nullable
    @Bind(R.id.FROM_DATE_TXT)
    TextView m_cfromdatetxt;

    @Nullable
    @Bind(R.id.TO_DATE_TXT)
    TextView m_ctodatetxt;

    final String[] MONTH = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep",
            "Oct", "Nov", "Dec"};
    public static final int FROM_DATE_PICKER_ID = 101;
    public static final int TO_DATE_PICKER_ID = 102;
    private Calendar m_cCalendar;
    private DatePickerDialog m_cDatePickerDialog;

    @Override
    protected void onCreate(Bundle pSavedInstance) {
        super.onCreate(pSavedInstance);
        setContentView(R.layout.activity_slider_layout);

        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.campaigns_list);
        m_cView = stub.inflate();
        ButterKnife.bind(this);

        setTitle("Campaigns", false, false, true, false);
        ISSWIPE = false;

        // Slider Initilization
        m_cObjSliderMenu = (DrawerLayout) findViewById(R.id.drawer_layout);
        m_cObjSliderMenu.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        m_cContainFragment = (FrameLayout) findViewById(R.id.main_content_container);

        init();
    }

    private void init() {
        m_cCampList = new ArrayList<>();
        m_cLayoutManager = new LinearLayoutManager(this);
        m_cRecyclerCamps.setLayoutManager(m_cLayoutManager);
        m_cRecyclerCamps.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //check for scroll down
                {
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
                            doPagination(lpage);
                        }
                    }
                }
            }
        });

        m_cResultsCount.setText("(0 campaigns)");

        displayProgressBar(-1, "Loading ...");
        HashMap<String, String> lParams = new HashMap<>();
        lParams.put(DSAMacros.INCLUDE, "team.members");
        RequestManager.getInstance(this).placeRequest(Constants.CAMPAIGNS, Campaigns.class, this, lParams, false);

    }

    private void doPagination(int pPage) {
        m_cCampList.add(null);
        m_cRecyclerAdapter.notifyItemInserted(m_cCampList.size() - 1);
        HashMap<String, String> lParams = new HashMap<>();
        lParams.put(DSAMacros.PAGINATION_PAGE, "" + pPage);
        lParams.put(DSAMacros.INCLUDE, "team.members");
        if (m_cfromdatetxt.getText().toString().length() > 0) {
            lParams.put(Constants.FROM_DATE, DSAMacros.getDateFormatYYYYMMDD(null, m_cfromdatetxt.getText().toString()));
        }
        if (m_ctodatetxt.getText().toString().length() > 0) {
            lParams.put(Constants.TO_DATE, DSAMacros.getDateFormatYYYYMMDD(null, m_ctodatetxt.getText().toString()));
        }
        RequestManager.getInstance(this).placeRequest(Constants.CAMPAIGNS, Campaigns.class, this, lParams, false);
    }

    @Override
    public void onBackPressed() {
        displayYesNoAlert(-1, "Are you sure you want to exit?");
    }

    @Override
    protected void handleUIMessage(Message pObjMessage) {
        switch (pObjMessage.what) {
            case DSAMacros.YES_MESSAGE:
                finish();
                break;
            case DSAMacros.NO_MESSAGE:
                break;
        }
    }

    @Override
    public void onAPIResponse(Object response, String apiMethod) {
        switch (apiMethod) {
            case Constants.CAMPAIGNS:
                if (!FILTER_CLICKED) {
                    List<CampaignsData> lObjCampaignsData = ((Campaigns) response).getData();
                    if (lObjCampaignsData.size() > 0) {
                        if (m_cLoading) {
                            for (CampaignsData lCd : lObjCampaignsData) {
                                m_cCampList.add(lCd);
                            }
                            if (null != m_cCampList && m_cCampList.size() > 0) {
                                m_cRecyclerAdapter = new CustomRecyclerAdapterForListOfCampaigns(this, m_cCampList, this);
                                m_cRecyclerCamps.setAdapter(m_cRecyclerAdapter);
                            }
                            updateCount(m_cCampList.size());
                        } else {
                            m_cCampList.remove(m_cCampList.size() - 1);
                            m_cRecyclerAdapter.notifyItemRemoved(m_cCampList.size());
                            for (CampaignsData lCd : lObjCampaignsData) {
                                m_cCampList.add(lCd);
                            }
                            updateCount(m_cCampList.size());
                            m_cRecyclerAdapter.notifyItemInserted(m_cCampList.size());
                            m_cLoading = true;
                        }
                        m_cNoData.setVisibility(View.GONE);
                    } else {
                        if(m_cCampList.size() > 0) {
                            m_cCampList.remove(m_cCampList.size() - 1);
                            m_cRecyclerAdapter.notifyItemRemoved(m_cCampList.size());
                            m_cLoading = false;
                            page = 1;
                        }else {
                            m_cNoData.setVisibility(View.VISIBLE);
                        }
                    }
                    hideDialog();
                }else {
                    List<CampaignsData> lObjCampaignsData = ((Campaigns) response).getData();
                    if (lObjCampaignsData.size() > 0) {
                        /*m_cRecyclerAdapter.notifyItemRangeRemoved(0, m_cProjectList.size());
                        m_cProjectList = new ArrayList<>();*/
                        if (m_cLoading) {
                            m_cCampList.clear();
                            m_cRecyclerAdapter.notifyDataSetChanged();
                            for (CampaignsData lCd : lObjCampaignsData) {
                                m_cCampList.add(lCd);
                            }
                            if (null != m_cCampList && m_cCampList.size() > 0) {
                                m_cRecyclerAdapter.notifyDataSetChanged();
                            }
                            updateCount(m_cCampList.size());
                        } else {
                            m_cCampList.remove(m_cCampList.size() - 1);
                            m_cRecyclerAdapter.notifyItemRemoved(m_cCampList.size());
                            for (CampaignsData lCd : lObjCampaignsData) {
                                m_cCampList.add(lCd);
                            }
                            updateCount(m_cCampList.size());
                            m_cRecyclerAdapter.notifyItemInserted(m_cCampList.size());
                            m_cLoading = true;
                        }
                        FILTER_CLICKED = false;
                        m_cNoData.setVisibility(View.GONE);
                    } else {
                        if (!m_cLoading) {
                            m_cCampList.remove(m_cCampList.size() - 1);
                            m_cRecyclerAdapter.notifyItemRemoved(m_cCampList.size());
                            m_cLoading = false;
                        } else {
                            if(m_cCampList.size() > 0) {
                                m_cCampList.clear();
                                m_cRecyclerAdapter.notifyDataSetChanged();
                                updateCount(m_cCampList.size());
                                hideDialog();
                                m_cLoading = false;
                                page = 1;
                            }else {
                                m_cNoData.setVisibility(View.VISIBLE);
                            }
                        }
                        FILTER_CLICKED = false;
                    }
                    hideDialog();
                }
                break;
            default:
                super.onAPIResponse(response, apiMethod);
                break;
        }


    }

    private void updateCount(int pSize) {
        if(pSize == 0 || pSize > 1){
            m_cResultsCount.setText("(" + pSize + " Campaigns)");
        }else if (pSize == 1){
            m_cResultsCount.setText("(" + pSize + " Campaign)");
        }

    }

    @Override
    public void onErrorResponse(VolleyError error, String apiMethod) {
        hideDialog();
        switch (apiMethod) {
            case Constants.CAMPAIGNS:
                if (error != null) {
                    if (error.networkResponse.statusCode == 401) {
                        logout();
                        displaySnack(m_cView, "Session Expired");
                    } else {
                        hideDialog();
                        page = 1;
                        if (!FILTER_CLICKED) {
                            hideDialog();
                            FILTER_CLICKED = false;
                        }
                    }
                } else {
                    super.onErrorResponse(error, apiMethod);
                }
                break;
            default:
                super.onErrorResponse(error, apiMethod);
                break;
        }
    }

    @Override
    public void onInfoClick(int pPostion, Campaigns pCampaigns, CampaignsData pCampaignData, View pView) {

    }

    public boolean validateCred() {
        boolean lRetVal = false;
        String lFromDate = m_cfromdatetxt.getText().toString().trim();
        String lToDate = m_ctodatetxt.getText().toString().trim();
        if (lFromDate.length() > 0) {
            if (lToDate.length() > 0) {
                lRetVal = true;
            } else {
                displaySnack(m_cView, "Please Select To Date");
            }
        } else {
            displaySnack(m_cView, "Please Select From Date");
        }
        return lRetVal;
    }

    @OnClick({R.id.SPIN_FILT_BUTTON_TXT, R.id.FROM_DATE_LAY, R.id.TO_DATE_LAY})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.SPIN_FILT_BUTTON_TXT:
                if(validateCred()) {
                    displayProgressBar(-1, "Loading...");
                    HashMap<String, String> lParams = new HashMap<>();
                    lParams.put(Constants.INSCLUDE, Constants.MEMBERS);
                    lParams.put(Constants.FROM_DATE, DSAMacros.getDateFormatYYYYMMDD(null, m_cfromdatetxt.getText().toString()));
                    lParams.put(Constants.TO_DATE, DSAMacros.getDateFormatYYYYMMDD(null, m_ctodatetxt.getText().toString()));
                    RequestManager.getInstance(this).placeRequest(Constants.CAMPAIGNS, Campaigns.class, this, lParams, false);
                    FILTER_CLICKED = true;
                    m_cLoading = true;
                }
                break;
            case R.id.FROM_DATE_LAY:
                showDatePickerDialog(FROM_DATE_PICKER_ID);
                break;
            case R.id.TO_DATE_LAY:
                showDatePickerDialog(TO_DATE_PICKER_ID);
                break;
        }
    }

    private void showDatePickerDialog(int pId) {
        m_cCalendar = Calendar.getInstance();
        switch (pId) {
            case FROM_DATE_PICKER_ID:
                if (fromDate > 0)
                    m_cDatePickerDialog = new DatePickerDialog(this, myFromDateListener, fromYear,
                            fromMonth, fromDate);
                else
                    m_cDatePickerDialog = new DatePickerDialog(this, myFromDateListener, m_cCalendar.get(Calendar.YEAR),
                            m_cCalendar.get(Calendar.MONTH), m_cCalendar.get(Calendar.DAY_OF_MONTH));
                m_cDatePickerDialog.setTitle("From Date");
                break;
            case TO_DATE_PICKER_ID:
                if (toDate > 0)
                    m_cDatePickerDialog = new DatePickerDialog(this, myToDateListener, toYear,
                            toMonth, toDate);
                else
                    m_cDatePickerDialog = new DatePickerDialog(this, myToDateListener, m_cCalendar.get(Calendar.YEAR),
                            m_cCalendar.get(Calendar.MONTH), m_cCalendar.get(Calendar.DAY_OF_MONTH));
                m_cDatePickerDialog.setTitle("To Date");
                try {
                    m_cDatePickerDialog.getDatePicker().setMinDate(DSAMacros.convertStringToDate(m_cfromdatetxt.getText().toString(), DSAMacros.DEFAULT_DATEFORMAT_DDMMYYYY).getTime());
                } catch (Exception e) {
                    e.printStackTrace();
                    m_cDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                }
                break;
        }
        m_cDatePickerDialog.show();
    }

    private DatePickerDialog.OnDateSetListener myFromDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int year, int month, int day) {
            String lmonth = String.format("%02d", month + 1);
            fromMonth = Integer.parseInt(lmonth) -1;
            String lday = String.format("%02d", day);
            fromDate = Integer.parseInt(lday);
            fromYear = year;
            m_cfromdatetxt.setText(lday + "-" + lmonth + "-" + year);
            m_cDatePickerDialog.dismiss();
            showDatePickerDialog(TO_DATE_PICKER_ID);
        }
    };

    private DatePickerDialog.OnDateSetListener myToDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int year, int month, int day) {
            String lmonth = String.format("%02d", month + 1);
            toMonth = Integer.parseInt(lmonth) -1;
            String lday = String.format("%02d", day);
            toDate = Integer.parseInt(lday);
            toYear = year;
            m_ctodatetxt.setText(lday + "-" + lmonth + "-" + year);
            m_cDatePickerDialog.dismiss();
        }
    };
}
