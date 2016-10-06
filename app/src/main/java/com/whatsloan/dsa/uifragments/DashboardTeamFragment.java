package com.whatsloan.dsa.uifragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.orm.query.Select;
import com.whatsloan.dsa.R;
import com.whatsloan.dsa.macros.DSAMacros;
import com.whatsloan.dsa.model.Dashboards;
import com.whatsloan.dsa.model.DataAuth;
import com.whatsloan.dsa.model.Members;
import com.whatsloan.dsa.model.MembersData;
import com.whatsloan.dsa.network.Constants;
import com.whatsloan.dsa.uiactivities.DSACustomers;
import com.whatsloan.dsa.uiactivities.DSALeads;
import com.whatsloan.dsa.uiactivities.DSAViewAttendanceHistory;
import com.whatsloan.dsa.uiactivities.ViewProfile;
import com.whatsloan.dsa.uiframework.DSAFragmentBaseClass;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by S.K. Pissay on 12/4/16.
 */
public class DashboardTeamFragment extends DSAFragmentBaseClass implements AdapterView.OnItemSelectedListener , View.OnTouchListener {

    @Nullable
    @Bind(R.id.VIEW_PROFILE_VIEW_TXT)
    TextView m_cViewProfile;

    @Bind({ R.id.SELECT_MONTH_FROM, R.id.SELECT_MONTH_TO})
    List<RelativeLayout> nameViews;

    @Nullable
    @Bind(R.id.SELECT_MONTH_FROM)
    RelativeLayout m_cDateFromPick;

    @Nullable
    @Bind(R.id.FROM_TXT)
    TextView m_cFromDateTxt;

    @Nullable
    @Bind(R.id.SELECT_MONTH_TO)
    RelativeLayout m_cDateToPick;

    @Nullable
    @Bind(R.id.TO_TXT)
    TextView m_cToDateTxt;

    @Nullable
    @Bind(R.id.SELF_SPIN)
    Spinner m_cMembersSpin;

    @Nullable
    @Bind(R.id.SELECT_PERIOD_SPIN)
    Spinner m_cSelectPeriodSpin;

    @Nullable
    @Bind(R.id.DAYS_ATTEN_TXT_NO)
    TextView m_cAttenTxtNo;

    @Nullable
    @Bind(R.id.LEADS_TXT_NO)
    TextView m_cLeadTxtNo;

    @Nullable
    @Bind(R.id.LEADS_AMT_NO)
    TextView m_cLeadsAmtNo;

    @Nullable
    @Bind(R.id.LOGINS_TXT_NO)
    TextView m_cLoginsTxtNo;

    @Nullable
    @Bind(R.id.LOGINS_AMT_NO)
    TextView m_cLoginsAmtNo;

    @Nullable
    @Bind(R.id.LOGINS_TXT_DISBURSE)
    TextView m_cLoginsTxtDisb;

    @Nullable
    @Bind(R.id.LOGINS_AMT_DISBURSE)
    TextView m_cLoginsAmtDisb;

    @Nullable
    @Bind(R.id.HISTORY_BTN)
    Button m_cAttenHisBtn;

    @Nullable
    @Bind(R.id.GO_ARROW_LEADS)
    ImageView m_cGoLeads;

    @Nullable
    @Bind(R.id.GO_ARROW_LOGINS)
    ImageView m_cGoLogins;

    @Nullable
    @Bind(R.id.GO_ARROW_DISBURSE)
    ImageView m_cGoDisb;

    @Nullable
    @Bind(R.id.INTEREST_WAIVERS)
    TextView m_cInterstWaives;

    @Nullable
    @Bind(R.id.SANCTIONS)
    TextView m_cSanctions;

    @Nullable
    @Bind(R.id.CREDIT_DEVIATIONS)
    TextView m_cCreditDeviations;

    @Nullable
    @Bind(R.id.TARGET)
    TextView m_cTarget;

    @Nullable
    @Bind(R.id.ACHIVED)
    TextView m_cAchived;

    @Nullable
    @Bind(R.id.INCENTIVE_PLAN)
    TextView m_cIncentivPlan;

    @Nullable
    @Bind(R.id.INCENTIVE_EARNED)
    TextView m_cIncentiveEarned;

    private int fromMonth = -1;
    private int fromYear = -1;
    private int fromDate = -1;
    private int toMonth = -1;
    private int toDate = -1;
    private int toYear = -1;

    public static final int FROM_DATE_PICKER_ID = 101;
    public static final int TO_DATE_PICKER_ID = 102;
    private Calendar m_cCalendar;
    private DatePickerDialog m_cDatePickerDialog;
    boolean userSelect = false;

    ArrayList<String> m_cMembersList;
    HashMap<String, String> m_cMembersDic;
    ArrayAdapter<String> m_cSpinAdapter;

    ArrayList<String> m_cPeriodList;
    ArrayList<String> m_cPeriodSendList;

    private int m_cTeamsPos = -1;
    private int m_cPeriodPos = -1;

//    StringBuffer m_cStartEndDateBuff;

    String m_cJsonObject;
    int m_cPos;
//    private ScrollView m_cScroll;

    public DashboardTeamFragment() {
        super();
    }

    public static DashboardTeamFragment newInstance(int pPosition, String pJsonObject) {
        DashboardTeamFragment DashboardTeamFragment = new DashboardTeamFragment();

        Bundle args = new Bundle();
        args.putInt("Position", pPosition);
        args.putString("JsonObject", pJsonObject);
        DashboardTeamFragment.setArguments(args);

        return DashboardTeamFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        m_cIsActivityAttached = true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        m_cObjMainView = inflater.inflate(R.layout.dashboards_team_layout, container, false);
        ButterKnife.bind(this, m_cObjMainView);

        m_cObjMainActivity.m_cObjFragmentBase = DashboardTeamFragment.this;
        m_cPos = getArguments().getInt("Position", 0);
        m_cJsonObject = getArguments().getString("JsonObject");


        /*m_cScroll = (ScrollView) m_cObjMainView.findViewById(R.id.SCROLL);
        m_cObjMainActivity.swipeView.setEnabled(false);

        m_cScroll.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (m_cScroll.getScrollY() == 0) {
                    m_cObjMainActivity.swipeView.setEnabled(true);
                } else {
                    m_cObjMainActivity.swipeView.setEnabled(false);
                }
            }
        });*/

        return m_cObjMainView;
    }

    @Override
    public void onResume() {
        super.onResume();
        init();
    }

    private void init() {
        m_cSelectPeriodSpin.setOnTouchListener(this);
        m_cSelectPeriodSpin.setOnItemSelectedListener(DashboardTeamFragment.this);
        m_cMembersSpin.setOnTouchListener(this);
        m_cMembersSpin.setOnItemSelectedListener(DashboardTeamFragment.this);
        m_cSelectPeriodSpin.setOnTouchListener(this);
        m_cSelectPeriodSpin.setOnItemSelectedListener(DashboardTeamFragment.this);

        m_cMembersList = new ArrayList<>();
        m_cMembersList.add("All");
        m_cSpinAdapter = new ArrayAdapter<String>(m_cObjMainActivity, R.layout.spinner_item_bold, m_cMembersList);
        m_cMembersSpin.setAdapter(m_cSpinAdapter);

        m_cPeriodSendList = new ArrayList<>();
        m_cPeriodSendList.add("Select Period");
        m_cPeriodSendList.add("current_month");
        m_cPeriodSendList.add("current_quarter");
        m_cPeriodSendList.add("current_financial_year");

        m_cPeriodList = new ArrayList<>();
        m_cPeriodList.add("Select Period");
        m_cPeriodList.add("Current Month");
        m_cPeriodList.add("Current Quarter");
        m_cPeriodList.add("Current Financial Year");
        m_cSpinAdapter = new ArrayAdapter<String>(m_cObjMainActivity, R.layout.spinner_item_bold, m_cPeriodList);
        m_cSelectPeriodSpin.setAdapter(m_cSpinAdapter);

        m_cObjMainActivity.displayProgressBar(-1, "Loading");
        //Calling Team api
        HashMap<String, String> lllParams = new HashMap<>();
        lllParams.put(Constants.INSCLUDE, Constants.MEMBERS);
        placeRequest(Constants.USERTEAM_MEMBERS, Members.class, lllParams, false);

        //calling D teams api
        placeRequest(Constants.DASHBOARD_TEAMS, Dashboards.class, null, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick({R.id.VIEW_PROFILE_VIEW_TXT, R.id.SELECT_MONTH_FROM, R.id.SELECT_MONTH_TO, R.id.HISTORY_BTN,
            R.id.GO_ARROW_LEADS, R.id.GO_ARROW_LOGINS, R.id.GO_ARROW_DISBURSE})
    public void onClick(View v) {
        Intent lObjIntent;
        super.onClick(v);
        switch (v.getId()) {
            case R.id.VIEW_PROFILE_VIEW_TXT:
                lObjIntent = new Intent(m_cObjMainActivity, ViewProfile.class);
                Select ldataAuth = Select.from(DataAuth.class);
                DataAuth lDAuth = (DataAuth) ldataAuth.first();
                if (m_cTeamsPos > 0) {
                    lObjIntent.putExtra(Constants.MEMBER_UUID, m_cMembersDic.get(m_cMembersList.get(m_cTeamsPos)));
                } else {
                    lObjIntent.putExtra(Constants.MEMBER_UUID, lDAuth.getUuid());
                }
                lObjIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(lObjIntent);
                reFreshDates();
                break;
            case R.id.SELECT_MONTH_FROM:
                showDatePickerDialog(FROM_DATE_PICKER_ID);
                break;
            case R.id.SELECT_MONTH_TO:
                showDatePickerDialog(TO_DATE_PICKER_ID);
                break;
            case R.id.HISTORY_BTN:
                lObjIntent = new Intent(m_cObjMainActivity, DSAViewAttendanceHistory.class);
                lObjIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(lObjIntent);
                reFreshDates();
                break;
            case R.id.GO_ARROW_LEADS:
                if (Integer.parseInt(m_cLeadTxtNo.getText().toString()) > 0) {
                    lObjIntent = new Intent(m_cObjMainActivity, DSALeads.class);
                    lObjIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(lObjIntent);
                    reFreshDates();
                } else {
                    m_cObjMainActivity.displaySnack(m_cObjMainView, "No Leads");
                }
                break;
            case R.id.GO_ARROW_LOGINS:
                if (Integer.parseInt(m_cLoginsTxtNo.getText().toString()) > 0) {
                    lObjIntent = new Intent(m_cObjMainActivity, DSACustomers.class);
                    lObjIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    lObjIntent.putExtra(Constants.STATUSGROUP, Constants.LOGINS);
                    startActivity(lObjIntent);
                    reFreshDates();
                } else {
                    m_cObjMainActivity.displaySnack(m_cObjMainView, "No Logins");
                }
                break;
            case R.id.GO_ARROW_DISBURSE:
                if (Integer.parseInt(m_cLoginsTxtDisb.getText().toString()) > 0) {
                    lObjIntent = new Intent(m_cObjMainActivity, DSACustomers.class);
                    lObjIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    lObjIntent.putExtra(Constants.STATUSGROUP, Constants.DISBURSEMENTS);
                    startActivity(lObjIntent);
                    reFreshDates();
                }else {
                    m_cObjMainActivity.displaySnack(m_cObjMainView, "No Disbursements");
                }
                break;
        }
    }

    private void reFreshDates() {
        fromMonth = -1;
        fromDate = -1;
        fromYear = -1;
        toMonth = -1;
        toDate = -1;
        toYear = -1;
        m_cFromDateTxt.setText("From");
        m_cToDateTxt.setText("To");
    }

    @Override
    public void onAPIResponse(Object response, String apiMethod) {
        Intent lObjIntent;
        super.onAPIResponse(response, apiMethod);
        switch (apiMethod) {
            case Constants.USERTEAM_MEMBERS:
                Members lMembers = (Members) response;
                if (lMembers.getData().size() > 0) {
                    m_cMembersList = new ArrayList<>();
                    m_cMembersDic = new HashMap<>();
                    m_cMembersList.add("All");
                    for (MembersData lMembersData : lMembers.getData()) {
                        m_cMembersList.add(lMembersData.getFirstName() + " " + lMembersData.getLastName());
                        m_cMembersDic.put(lMembersData.getFirstName() + " " + lMembersData.getLastName(), lMembersData.getUuid());
                    }
                    m_cSpinAdapter = new ArrayAdapter<String>(m_cObjMainActivity, R.layout.spinner_item_bold, m_cMembersList);
                    m_cMembersSpin.setAdapter(m_cSpinAdapter);
                        int lPos = 0;
                        try {
//                            lPos = m_cMembersList.indexOf(m_cObjProjectStruct.getAgent().getData().getFirstName() + " " + m_cObjProjectStruct.getAgent().getData().getLastName());
                            m_cTeamsPos = 1;
                            m_cMembersSpin.setSelection(1);
                            callServer();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                }
                m_cObjMainActivity.hideDialog();
                break;
            case Constants.DASHBOARD_TEAMS:
                Dashboards lDashboards = (Dashboards) response;
                if (null != lDashboards.getData()) {
                    try {
                        try {
                            m_cAttenTxtNo.setText(lDashboards.getData().getAttendance().getCount() != null ?
                                    lDashboards.getData().getAttendance().getCount() : "0");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            m_cLeadTxtNo.setText(lDashboards.getData().getLeads().getCount() != null ?
                                    lDashboards.getData().getLeads().getCount() : "0");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            m_cLeadsAmtNo.setText(lDashboards.getData().getLeads().getAmount() != null ?
                                    "\u20B9 " + lDashboards.getData().getLeads().getAmount() : "\u20B9 0");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            m_cLoginsTxtNo.setText(lDashboards.getData().getLogins().getCount() != null ?
                                    lDashboards.getData().getLogins().getCount() : "0");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            m_cLoginsAmtNo.setText(lDashboards.getData().getLogins().getAmount() != null ?
                                    "\u20B9 " + lDashboards.getData().getLogins().getAmount() : "\u20B9 0");

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            m_cLoginsTxtDisb.setText(lDashboards.getData().getDisbursements().getCount() != null ?
                                    lDashboards.getData().getDisbursements().getCount() : "0");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            m_cLoginsAmtDisb.setText(lDashboards.getData().getDisbursements().getAmount() != null ?
                                    "\u20B9 " + lDashboards.getData().getDisbursements().getAmount() : "\u20B9 0");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            m_cInterstWaives.setText(lDashboards.getData().getWaivers().getCount() != null ?
                                    lDashboards.getData().getWaivers().getCount() : "0");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            m_cSanctions.setText(lDashboards.getData().getSanctions().getCount() != null ?
                                    lDashboards.getData().getSanctions().getCount() : "0");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            m_cCreditDeviations.setText(lDashboards.getData().getDeviations().getCount() != null ?
                                    lDashboards.getData().getDeviations().getCount() : "0");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            m_cTarget.setText(lDashboards.getData().getTarget().getCount() != null ?
                                    lDashboards.getData().getTarget().getCount() : "0");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            m_cAchived.setText(lDashboards.getData().getAchieved().getCount() != null ?
                                    lDashboards.getData().getAchieved().getCount() : "0");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            m_cIncentivPlan.setText(lDashboards.getData().getIncentivePlan().getCount() != null ?
                                    lDashboards.getData().getIncentivePlan().getCount() : "0");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            m_cIncentiveEarned.setText(lDashboards.getData().getIncentiveEarned().getCount() != null ?
                                    lDashboards.getData().getIncentiveEarned().getCount() : "0");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                m_cObjMainActivity.hideDialog();
                break;
        }
    }

    @Override
    public void onErrorResponse(VolleyError error, String apiMethod) {
        super.onErrorResponse(error, apiMethod);
        m_cObjMainActivity.hideDialog();
    }

    @Override
    protected void handleUIMessage(Message pObjMessage) {

    }

    private void showDatePickerDialog(int pId) {
        m_cCalendar = Calendar.getInstance();
        switch (pId) {
            case FROM_DATE_PICKER_ID:
                if (fromDate > 0)
                    m_cDatePickerDialog = new DatePickerDialog(m_cObjMainActivity, myFromDateListener, fromYear,
                            fromMonth, fromDate);
                else
                    m_cDatePickerDialog = new DatePickerDialog(m_cObjMainActivity, myFromDateListener, m_cCalendar.get(Calendar.YEAR),
                            m_cCalendar.get(Calendar.MONTH), m_cCalendar.get(Calendar.DAY_OF_MONTH));
                m_cDatePickerDialog.setTitle("Select From Date");
                break;
            case TO_DATE_PICKER_ID:
                if (toDate > 0)
                    m_cDatePickerDialog = new DatePickerDialog(m_cObjMainActivity, myToDateListener, toYear,
                            toMonth, toDate);
                else
                    m_cDatePickerDialog = new DatePickerDialog(m_cObjMainActivity, myToDateListener, m_cCalendar.get(Calendar.YEAR),
                            m_cCalendar.get(Calendar.MONTH), m_cCalendar.get(Calendar.DAY_OF_MONTH));
                try {
                    m_cDatePickerDialog.getDatePicker().setMinDate(DSAMacros.convertStringToDate(m_cFromDateTxt.getText().toString(), DSAMacros.DEFAULT_DATEFORMAT_DDMMYYYY).getTime());
                } catch (Exception e) {
                    e.printStackTrace();
                    m_cDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                }
                m_cDatePickerDialog.setTitle("Select To Date");
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
//            m_cStartEndDateBuff = new StringBuffer();
//            m_cStartEndDateBuff.append(lday + "-" + lmonth + "-" + year);
            m_cFromDateTxt.setText(lday + "-" + lmonth + "-" + year);
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
            m_cToDateTxt.setText(lday + "-" + lmonth + "-" + year);
            m_cDatePickerDialog.dismiss();

//            m_cStartEndDateBuff.append(" to " + lday + "-" + lmonth + "-" + year);

            m_cObjMainActivity.displayProgressBar(-1, "Loading");
            if (m_cToDateTxt.getText().length() > 0) {
                callServer();
            }
        }
    };

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (userSelect) {
            switch (parent.getId()) {
                case R.id.SELF_SPIN:
                    m_cTeamsPos = position;
                    if (m_cTeamsPos >= 0) {
                        callServer();
                    }
                    break;
                case R.id.SELECT_PERIOD_SPIN:
                    m_cPeriodPos = position;
                    if(m_cPeriodPos > 0){
                        ButterKnife.apply(nameViews, DISABLE);
                        callServer();
                    }else if (m_cPeriodPos == 0){
                        ButterKnife.apply(nameViews, ENABLED, true);
                    }
                    break;
            }
            userSelect = false;
        }
    }

    private void callServer() {
        m_cObjMainActivity.displayProgressBar(-1, "Loading");
        HashMap<String, String> lParams = new HashMap<>();
        if (m_cPeriodPos > 0) {
            lParams.put(Constants.PERIOD, m_cPeriodSendList.get(m_cPeriodPos));
        }
        if (m_cTeamsPos > 0) {
            lParams.put(Constants.MEMBER_UUID, m_cMembersDic.get(m_cMembersList.get(m_cTeamsPos)));
        }
        if (m_cFromDateTxt.getText().length() > 0) {
            lParams.put(Constants.FROM, DSAMacros.getDateFormatYYYYMMDD(null, m_cFromDateTxt.getText().toString()));
        }
        if (m_cToDateTxt.getText().length() > 0) {
            lParams.put(Constants.TO, DSAMacros.getDateFormatYYYYMMDD(null, m_cToDateTxt.getText().toString()));
        }
        placeRequest(Constants.DASHBOARD_TEAMS, Dashboards.class, lParams, false);
    }

    static final ButterKnife.Action<View> DISABLE = new ButterKnife.Action<View>() {
        @Override public void apply(View view, int index) {
            view.setEnabled(false);
        }
    };
    static final ButterKnife.Setter<View, Boolean> ENABLED = new ButterKnife.Setter<View, Boolean>() {
        @Override public void set(View view, Boolean value, int index) {
            view.setEnabled(value);
        }
    };

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        userSelect = true;
        return false;
    }
}

