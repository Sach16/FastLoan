package com.whatsloan.dsa.uiactivities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.ViewStub;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.android.volley.VolleyError;
import com.whatsloan.dsa.R;
import com.whatsloan.dsa.customadapters.PagerAdapterForSearch;
import com.whatsloan.dsa.macros.DSAMacros;
import com.whatsloan.dsa.model.BuilderAll;
import com.whatsloan.dsa.model.BuilderData;
import com.whatsloan.dsa.model.LeadCustHead;
import com.whatsloan.dsa.model.Leads;
import com.whatsloan.dsa.model.Loans;
import com.whatsloan.dsa.model.ProjectData;
import com.whatsloan.dsa.model.Projects;
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
 * Created by S.K. Pissay on 30/3/16.
 */
public class DSASearch extends DSABaseActivity implements AdapterView.OnItemSelectedListener {
    private TabLayout m_cTabLayout;
    private ViewPager m_cPager;
    private View m_cView;
    //    private SwipeRefreshLayout swipeView;
    private PagerAdapterForSearch adapter;
    private int m_cBackPressed;

    private static int m_cWchPos;

    @Nullable
    @Bind(R.id.SPIN_SEARCH)
    Spinner m_cSpinSearch;

    @Nullable
    @Bind(R.id.BUILDER_SPINNER)
    Spinner m_cSpinBuilder;

    @Nullable
    @Bind(R.id.PROJECT_SPINNER)
    Spinner m_cSpinProject;

    @Nullable
    @Bind(R.id.SEARCH_EDITTXT)
    EditText m_cSearchEditTxt;

    @Nullable
    @Bind(R.id.CLOSE_TXT_IMG)
    ImageView m_ctxtCloseImge;

    @Nullable
    @Bind(R.id.SEARCH_GO_IMG)
    ImageView m_cSearchGoImge;

    @Nullable
    @Bind(R.id.BUILDER_LAY)
    RelativeLayout m_cRelBuilderLay;

    @Nullable
    @Bind(R.id.PROJECT_LAY)
    RelativeLayout m_cRelProjLay;

    ArrayList<String> m_cProjectList;
    HashMap<String, String> m_cProjectDic;
    ArrayList<String> m_cBuilderList;
    HashMap<String, String> m_cBuilderDic;

    private int m_cBuilderPos = -1;
    private int m_cProjectPos = -1;

    ArrayList<String> m_cKeyList;
    ArrayList<String> m_cSearchList;
    HashMap<String, String> m_cSearchDic;

    public static final int DATE_PICKER_ID = 113;
    private Calendar m_cCalendar;
    private DatePickerDialog m_cDatePickerDialog;

    private Leads m_cObjLeads;
    private Loans m_cLoans;
    private int m_cSearchPos = -1;

    ArrayAdapter<String> m_cSpinAdapter;

    @Override
    protected void onCreate(Bundle pSavedInstance) {
        super.onCreate(pSavedInstance);
        setContentView(R.layout.activity_slider_layout);

        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.search_layout);
        m_cView = stub.inflate();
        ButterKnife.bind(this);

        /*String lTitle = null;
        if (getIntent().getBooleanExtra("IS_NEW_PROJECT", false)) {
            lTitle = "New Task Assignment";
        } else {
            //TODO show the lead name
            lTitle = "Lead Name";
        }*/

        setTitle("Search", false, true, true, false);
        ISSWIPE = false;

        // Slider Initilization
        m_cObjSliderMenu = (DrawerLayout) findViewById(R.id.drawer_layout);
        m_cObjSliderMenu.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        m_cContainFragment = (FrameLayout) findViewById(R.id.main_content_container);

        m_cObjSliderMenu.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        m_cBackPressed = 0;

        initilization();
    }

    private void initilization() {
        m_cSpinSearch.setOnItemSelectedListener(this);
        m_cSpinBuilder.setOnItemSelectedListener(this);
        m_cSpinProject.setOnItemSelectedListener(this);

        m_cPager = (ViewPager) m_cView.findViewById(R.id.PAGER);
        m_cTabLayout = (TabLayout) m_cView.findViewById(R.id.TAB_LAYOUT);

//        swipeView = (SwipeRefreshLayout) m_cView.findViewById(R.id.SWIPE_REFRESH);

        m_cTabLayout.addTab(m_cTabLayout.newTab().setText("Leads (0)"));
        m_cTabLayout.addTab(m_cTabLayout.newTab().setText("Customers (0)"));
//        m_cTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        m_cPager = (ViewPager) m_cView.findViewById(R.id.PAGER);
        m_cPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(m_cTabLayout));
        adapter = new PagerAdapterForSearch(getSupportFragmentManager(),
                m_cObjFragmentBase,
                m_cTabLayout.getTabCount(),
                "",
                null, "", null, null, true);
        m_cPager.setAdapter(adapter);

        m_cTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                m_cPager.setCurrentItem(tab.getPosition());
//                swipeView.setEnabled(false);
                switch (tab.getPosition()) {
                    case 0:
                        m_cWchPos = 0;
                        // NOTHING TO DO HERE
                        break;
                    case 1:
                        m_cWchPos = 1;
//                        setTitle("Lead Name", false, false, true, false);
//                        swipeView.setEnabled(true);
//                        displayProgressBar(-1, "Loading Packages,..");
//                        m_cObjTransportMgr.getPackages("", EURemediesSpecialityScreen.this);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Nothing to do here
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Nothing to do here
            }
        });

        /*swipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                switch (m_cPager.getCurrentItem()) {
                    case 0:
//                        swipeView.setRefreshing(true);
//                        m_cObjTransportMgr.getSpeciality(EURemediesMacros.getSessionId(EURemediesSpecialityScreen.this), EURemediesSpecialityScreen.this);
                        break;
                    case 1:
//                        ISSWIPE = true;
//                        swipeView.setRefreshing(true);
//                        m_cObjTransportMgr.getPackages("", EURemediesSpecialityScreen.this);
                        break;
                }
            }
        });*/

        m_cKeyList = new ArrayList<>();
        m_cKeyList.add("all");
        m_cKeyList.add("name");
        m_cKeyList.add("phone");
        m_cKeyList.add("unit_number");
        m_cKeyList.add("builder_uuid");
        m_cKeyList.add("project_uuid");
        m_cKeyList.add("loan_status_name");
        m_cKeyList.add("loan_type_name");
        m_cKeyList.add("from_date");
        m_cKeyList.add("login_date");

        m_cSearchList = new ArrayList<>();
        m_cSearchDic = new HashMap<>();
        m_cSearchList.add("All");
        m_cSearchList.add(Constants.CUSTOMER_SEARCH);
        m_cSearchList.add(Constants.PHONE_SEARCH);
        m_cSearchList.add(Constants.UNITNO_SEARCH);
        m_cSearchList.add(Constants.BUILDER_SEARCH);
        m_cSearchList.add(Constants.PROJECT_SEARCH);
        m_cSearchList.add(Constants.LOAN_STATUS);
        m_cSearchList.add(Constants.LOANTYPE_SEARCH);
        m_cSearchList.add(Constants.FROM_DATESER);
        m_cSearchList.add(Constants.TO_DATESER);
        m_cSpinAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item_search, m_cSearchList);
        m_cSpinSearch.setAdapter(m_cSpinAdapter);

//        callSearchServerBoth(0, "", null, null, true);
//        initFragRequest(m_cKeyList.get(m_cSearchPos), "", null, null, true);

    }

    private void callSearchServer(int KeyPos, String Value, String pBuilder_uuid, String pProject_uuid, boolean both) {
        //call lead api
        switch (m_cPager.getCurrentItem()) {
            case 0:
                displayProgressBar(-1, "Loading Leads...");
                HashMap<String, String> lParams = new HashMap<>();
                if (KeyPos > 0) {
                    lParams.put(m_cKeyList.get(KeyPos), Value);
                }
                if (null != pBuilder_uuid && pBuilder_uuid.trim().length() > 0) {
                    lParams.put(Constants.BUILDER_UUID, pBuilder_uuid);
                }
                if (null != pProject_uuid && pProject_uuid.trim().length() > 0) {
                    lParams.put(Constants.PROJECTID, pProject_uuid);
                }
                lParams.put(Constants.INSCLUDE, "source,assignee,user.designation,user.addresses,loan");
                RequestManager.getInstance(this).placeRequest(Constants.SEARCH_LEADS, LeadCustHead.class, this, lParams, false);
                break;
            case 1:
                //call customers api
                displayProgressBar(-1, "Loading Leads...");
                HashMap<String, String> llParams = new HashMap<>();
                llParams.put(DSAMacros.INCLUDE, Constants.LOANS);
                if (KeyPos > 0) {
                    llParams.put(m_cKeyList.get(KeyPos), Value);
                }
                if (null != pBuilder_uuid && pBuilder_uuid.trim().length() > 0) {
                    llParams.put(Constants.BUILDER_UUID, pBuilder_uuid);
                }
                if (null != pProject_uuid && pProject_uuid.trim().length() > 0) {
                    llParams.put(Constants.PROJECTID, pProject_uuid);
                }
                RequestManager.getInstance(this).placeRequest(Constants.SEARCH_CUSTOMERS, LeadCustHead.class, this, llParams, false);
                break;
        }
    }

    private void callSearchServerBoth(int KeyPos, String Value, String pBuilder_uuid, String pProject_uuid, boolean both) {
        if(both) {
            displayProgressBar(-1, "Loading Leads...");
            HashMap<String, String> lParamsLead = new HashMap<>();
            if (KeyPos > 0) {
                lParamsLead.put(m_cKeyList.get(KeyPos), Value);
            }
            if (null != pBuilder_uuid && pBuilder_uuid.trim().length() > 0) {
                lParamsLead.put(Constants.BUILDER_UUID, pBuilder_uuid);
            }
            if (null != pProject_uuid && pProject_uuid.trim().length() > 0) {
                lParamsLead.put(Constants.PROJECTID, pProject_uuid);
            }
            lParamsLead.put(Constants.INSCLUDE, "source,assignee,user.designation,user.addresses,loan");
            RequestManager.getInstance(this).placeRequest(Constants.SEARCH_LEADS, LeadCustHead.class, this, lParamsLead, false);

            HashMap<String, String> llParamsCust = new HashMap<>();
            llParamsCust.put(DSAMacros.INCLUDE, Constants.LOANS);
            if (KeyPos > 0) {
                llParamsCust.put(m_cKeyList.get(KeyPos), Value);
            }
            if (null != pBuilder_uuid && pBuilder_uuid.trim().length() > 0) {
                llParamsCust.put(Constants.BUILDER_UUID, pBuilder_uuid);
            }
            if (null != pProject_uuid && pProject_uuid.trim().length() > 0) {
                llParamsCust.put(Constants.PROJECTID, pProject_uuid);
            }
            RequestManager.getInstance(this).placeRequest(Constants.SEARCH_CUSTOMERS, LeadCustHead.class, this, llParamsCust, false);
        }
    }


    @Override
    protected void handleUIMessage(Message pObjMessage) {

    }

    @OnClick({R.id.CLOSE_TXT_IMG, R.id.SEARCH_GO_IMG, R.id.SEARCH_EDITTXT})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.CLOSE_TXT_IMG:
                m_cSearchEditTxt.setText("");
                break;
            case R.id.SEARCH_GO_IMG:
                try {
                    String lsearchText = m_cSearchEditTxt.getText().toString().trim();
                    if (isAlphaNumSearch(lsearchText)) {
                        if (m_cSearchPos >= 0 && m_cSearchPos != 3) {
                    /*callSearchServer(m_cSearchPos, m_cSearchEditTxt.getText().toString(),
                            null, null, false);*/
                            initFragRequest(m_cKeyList.get(m_cSearchPos),
                                    m_cSearchEditTxt.getText().toString(),
                                    null, null, false);
                        } else if (m_cSearchPos == 3) {
                    /*callSearchServer(m_cSearchPos, m_cSearchEditTxt.getText().toString(),
                            m_cBuilderDic.get(m_cBuilderList.get(m_cBuilderPos)),
                            m_cProjectDic.get(m_cProjectList.get(m_cProjectPos)),
                            false);*/
                            initFragRequest(m_cKeyList.get(m_cSearchPos)
                                    , m_cSearchEditTxt.getText().toString(),
                                    m_cBuilderDic.get(m_cBuilderList.get(m_cBuilderPos)),
                                    m_cProjectDic.get(m_cProjectList.get(m_cProjectPos)),
                                    false);
                        }
                        View view = this.getCurrentFocus();
                        if (view != null) {
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }
                    } else {
                        displaySnack(m_cView, "Please enter valid data");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.SEARCH_EDITTXT:
                if(m_cSearchPos == 8 || m_cSearchPos == 9){
                    showDatePickerDialog(DATE_PICKER_ID);
                    View view = this.getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                }
                break;
        }
    }

    private void initFragRequest(String Key, String Value, String pBuilder_uuid, String pProject_uuid, boolean both) {
        try {
            int pos = m_cPager.getCurrentItem();
            final PagerAdapterForSearch adapter = new PagerAdapterForSearch(getSupportFragmentManager(),
                    m_cObjFragmentBase,
                    m_cTabLayout.getTabCount(),
                    "",
                    Key,
                    Value,
                    pBuilder_uuid,
                    pProject_uuid,
                    both);
            m_cPager.setAdapter(adapter);
            m_cPager.setCurrentItem(pos, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAPIResponse(Object response, String apiMethod) {
        switch (apiMethod){
            /*case Constants.SEARCH_LEADS:
                LeadCustHead lLeadCustHead = (LeadCustHead) response;
                m_cObjLeads = lLeadCustHead.getData().getLeads();
                if(m_cObjLeads.getData().size() > 0) {
                    m_cTabLayout.getTabAt(0).setText("Leads (" + m_cObjLeads.getData().size() + ")");
                }else {
                    m_cTabLayout.getTabAt(0).setText("Leads (0)");
                }
                try {
                    int pos = m_cPager.getCurrentItem();
                    final PagerAdapterForSearch adapter = new PagerAdapterForSearch(getSupportFragmentManager(),
                            m_cObjFragmentBase,
                            m_cTabLayout.getTabCount(),
                            m_cObjLeads,
                            null,
                            "");
                    m_cPager.setAdapter(adapter);
                    m_cPager.setCurrentItem(pos, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                hideDialog();
                break;
            case Constants.SEARCH_CUSTOMERS:
                LeadCustHead llLeadCustHead = (LeadCustHead) response;
                m_cLoans = llLeadCustHead.getData().getLoans();
                if(m_cLoans.getData().size() > 0) {
                    m_cTabLayout.getTabAt(1).setText("Customers (" + m_cLoans.getData().size() + ")");
                }else {
                    m_cTabLayout.getTabAt(1).setText("Customers (0)");
                }
                try {
                    int pos = m_cPager.getCurrentItem();
                    final PagerAdapterForSearch adapter = new PagerAdapterForSearch(getSupportFragmentManager(),
                            m_cObjFragmentBase,
                            m_cTabLayout.getTabCount(),
                            null,
                            m_cLoans,
                            "");
                    m_cPager.setAdapter(adapter);
                    m_cPager.setCurrentItem(pos, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                hideDialog();
                break;*/
            case Constants.BUILDERS_GETBUILDERS:
                BuilderAll builderAll = (BuilderAll) response;
                m_cBuilderList = new ArrayList<>();
                m_cBuilderDic = new HashMap<>();
                if (builderAll.getData().size() > 0) {
                    m_cBuilderList.add("Select Builder");
                }
                for (BuilderData lBuilderData : builderAll.getData()) {
                    m_cBuilderList.add(lBuilderData.getName());
                    m_cBuilderDic.put(lBuilderData.getName(), lBuilderData.getUuid());
                }
                m_cSpinAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item_bold, m_cBuilderList);
                m_cSpinBuilder.setAdapter(m_cSpinAdapter);
                /*if (null != m_cObjectProj) {
                    int lPos = 0;
                    try {
                        lPos = m_cBuilderList.indexOf(m_cObjectProj.getData().getBuilderName().toString());
                        m_cSpinBuilderEdit.setSelection(lPos);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }*/
                this.hideDialog();
                break;
            case Constants.PROJECTS_GETPROJECT:
                Projects lProjectsAll = (Projects) response;
                m_cProjectList = new ArrayList<>();
                m_cProjectDic = new HashMap<>();
                if (lProjectsAll.getProjectData().size() > 0) {
                    m_cProjectList.add("Select Project");
                }
                for (ProjectData lProject : lProjectsAll.getProjectData()) {
                    m_cProjectList.add(lProject.getName());
                    m_cProjectDic.put(lProject.getName(), lProject.getUuid());
                }
                m_cSpinAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item_bold, m_cProjectList);
                m_cSpinProject.setAdapter(m_cSpinAdapter);
                /*if (null != m_cQueryData) {
                    int lPos = 0;
                    try {
                        lPos = m_cProjectList.indexOf(m_cObjectProj.getData().getName());
                        m_cSpinPropertyEdit.setSelection(lPos);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }*/
                hideDialog();
                break;
            default:
                super.onAPIResponse(response, apiMethod);
                break;
        }
    }

    @Override
    public void onErrorResponse(VolleyError error, String apiMethod) {
        switch (apiMethod){
            /*case Constants.SEARCH_LEADS:
                try {
                    m_cTabLayout.getTabAt(0).setText("Leads (0)");
                    int pos = m_cPager.getCurrentItem();
                    final PagerAdapterForSearch adapter = new PagerAdapterForSearch(getSupportFragmentManager(),
                            m_cObjFragmentBase,
                            m_cTabLayout.getTabCount(),
                            null,
                            null,
                            "");
                    m_cPager.setAdapter(adapter);
                    m_cPager.setCurrentItem(pos, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                hideDialog();
                displaySnack(m_cView, "No leads Available");
                break;
            case Constants.SEARCH_CUSTOMERS:
                hideDialog();
                try {
                    m_cTabLayout.getTabAt(1).setText("Customers (0)");
                    int pos = m_cPager.getCurrentItem();
                    final PagerAdapterForSearch adapter = new PagerAdapterForSearch(getSupportFragmentManager(),
                            m_cObjFragmentBase,
                            m_cTabLayout.getTabCount(),
                            null,
                            null,
                            "");
                    m_cPager.setAdapter(adapter);
                    m_cPager.setCurrentItem(pos, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                displaySnack(m_cView, "No Customers Available");
                break;*/
            default:
                super.onErrorResponse(error, apiMethod);
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.SPIN_SEARCH:
                m_cSearchPos = position;
                switch (m_cSearchPos){
                    case 0:
                        setAccessable(false);
                        break;
                    case 1:
                        setAccessable(false);
                        break;
                    case 2:
                        setAccessable(false);
                        break;
                    case 3:
                        setAccessable(true);
                        break;
                    case 4:
                        setAccessable(false);
                        break;
                    case 5:
                        setAccessable(false);
                        break;
                    case 6:
                        setAccessable(false);
                        break;
                    case 7:
                        setAccessable(false);
                        break;
                    case 8:
                        setAccessable(false);
                        break;
                    case 9:
                        setAccessable(false);
                        break;
                }
                break;
            case R.id.BUILDER_SPINNER:
                m_cBuilderPos = position;
                if (position > 0) {
                    HashMap<String, String> lParams = new HashMap<>();
                    if(m_cBuilderPos > 0) {
                        lParams.put(Constants.INSCLUDE, Constants.BUILDER);
                        lParams.put(Constants.BUILDER_UUID, m_cBuilderDic.get(m_cBuilderList.get(m_cBuilderPos)));
                    }
                    RequestManager.getInstance(this).placeRequest(Constants.PROJECTS_GETPROJECT, Projects.class, this, lParams, false);
                }
                break;
            case R.id.PROJECT_SPINNER:
                m_cProjectPos = position;
                break;
        }
    }

    private void setAccessable(boolean pFlag) {
        if (pFlag) {
            m_cRelBuilderLay.setVisibility(View.VISIBLE);
            m_cRelProjLay.setVisibility(View.VISIBLE);
            //call builder api
            displayProgressBar(-1, "Loading");
            RequestManager.getInstance(this).placeRequest(Constants.BUILDERS_GETBUILDERS, BuilderAll.class, this, null, false);

            HashMap<String, String> lParams = new HashMap<>();
            lParams.put(Constants.INSCLUDE, Constants.BUILDER);
            RequestManager.getInstance(this).placeRequest(Constants.PROJECTS_GETPROJECT, Projects.class, this, lParams, false);
        } else {
            m_cRelBuilderLay.setVisibility(View.GONE);
            m_cRelProjLay.setVisibility(View.GONE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void showDatePickerDialog(int pId) {
        m_cCalendar = Calendar.getInstance();
        switch (pId) {
            case DATE_PICKER_ID:
                m_cDatePickerDialog = new DatePickerDialog(this, myDateListener, m_cCalendar.get(Calendar.YEAR),
                        m_cCalendar.get(Calendar.MONTH), m_cCalendar.get(Calendar.DAY_OF_MONTH));
                m_cDatePickerDialog.setTitle("Select From Date");
                break;
        }
        m_cDatePickerDialog.show();
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int year, int month, int day) {
            String lmonth = String.format("%02d", month + 1);
            String lday = String.format("%02d", day);
            m_cSearchEditTxt.setText((lday + "-" + lmonth + "-" + year).toString());
            m_cDatePickerDialog.dismiss();
        }
    };

    public void setLeadCount(int pLeadCount) {
        if(pLeadCount > 0) {
            m_cTabLayout.getTabAt(0).setText("Leads (" + pLeadCount + ")");
        }else {
            m_cTabLayout.getTabAt(0).setText("Leads (0)");
        }
    }

    public void setCustCount(int pCustCount){
        if(pCustCount > 0) {
            m_cTabLayout.getTabAt(1).setText("Customers (" + pCustCount + ")");
        }else {
            m_cTabLayout.getTabAt(1).setText("Customers (0)");
        }
    }

    public Integer getScreenCount() {
        return m_cWchPos;
    }
}