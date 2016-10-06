package com.whatsloan.dsa.uifragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.whatsloan.dsa.R;
import com.whatsloan.dsa.macros.DSAMacros;
import com.whatsloan.dsa.model.AgentData;
import com.whatsloan.dsa.model.Dashboards;
import com.whatsloan.dsa.model.Types;
import com.whatsloan.dsa.network.Constants;
import com.whatsloan.dsa.uiactivities.DSACustomers;
import com.whatsloan.dsa.uiactivities.DSALeads;
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
public class DashboardReferralFragment extends DSAFragmentBaseClass implements AdapterView.OnItemSelectedListener {

    @Nullable
    @Bind(R.id.SELECT_MONTH_FROM)
    RelativeLayout m_cDateFromPick;

    @Bind({ R.id.SELECT_MONTH_FROM, R.id.SELECT_MONTH_TO})
    List<RelativeLayout> nameViews;

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
    Spinner m_cReffSpin;

    @Nullable
    @Bind(R.id.SELECT_PERIOD_SPIN)
    Spinner m_cSelectPeriodSpin;

    @Nullable
    @Bind(R.id.LEADS_TXT_NO)
    TextView m_cLeadTxtNo;

    @Nullable
    @Bind(R.id.LEAD_AMOUNT)
    TextView m_cLeadAmtNo;

    @Nullable
    @Bind(R.id.LEAD_PAYOUT)
    TextView m_cLeadPayoutNo;

    @Nullable
    @Bind(R.id.LOGINS_TXT_NO)
    TextView m_cLoginsTxtNo;

    @Nullable
    @Bind(R.id.LOGIN_AMOUNT)
    TextView m_cLoginsAmtNo;

    @Nullable
    @Bind(R.id.LOGIN_PAYOUT)
    TextView m_cLoginsPayoutNo;

    @Nullable
    @Bind(R.id.LOGINS_TXT_SANCTIONS)
    TextView m_cSancTxtNo;

    @Nullable
    @Bind(R.id.SANCTION_AMOUNT)
    TextView m_cSancAmtNo;

    @Nullable
    @Bind(R.id.SANCTION_PAYOUT)
    TextView m_cSancPayNo;

    @Nullable
    @Bind(R.id.LOGINS_TXT_DISBURSAL)
    TextView m_cDisbTxtNo;

    @Nullable
    @Bind(R.id.DISBURSALS_AMOUNT)
    TextView m_cDisbAmtNo;

    @Nullable
    @Bind(R.id.DISBURDALS_PAYOUT)
    TextView m_cDisbPayNo;


    @Nullable
    @Bind(R.id.TOTAL_PAID_AMT)
    TextView m_cTotalPaidNo;

    @Nullable
    @Bind(R.id.TOTAL_PAYOUT_AMT)
    TextView m_cTotalPayNo;

    @Nullable
    @Bind(R.id.BALANCE_AMT)
    TextView m_cBalanceAMtNo;


    @Nullable
    @Bind(R.id.GO_ARROW_LEADS)
    ImageView m_cGoArrowLeads;

    @Nullable
    @Bind(R.id.GO_ARROW_LOGINS)
    ImageView m_cGoArrowLogins;

    @Nullable
    @Bind(R.id.GO_ARROW_SANCTION)
    ImageView m_cGoArrowSanc;

    @Nullable
    @Bind(R.id.GO_ARROW_DISB)
    ImageView m_cGoArrowDisb;

    public static final int FROM_DATE_PICKER_ID = 101;
    public static final int TO_DATE_PICKER_ID = 102;
    private Calendar m_cCalendar;
    private DatePickerDialog m_cDatePickerDialog;

    ArrayList<String> m_cReffList;
    HashMap<String, String> m_cReffDic;
    ArrayAdapter<String> m_cSpinAdapter;

    ArrayList<String> m_cPeriodList;
    ArrayList<String> m_cPeriodSendList;

    private int m_cReffPos = -1;
    private int m_cPeriodPos = -1;

    private int fromMonth = -1;
    private int fromYear = -1;
    private int fromDate = -1;
    private int toMonth = -1;
    private int toDate = -1;
    private int toYear = -1;

//    StringBuffer m_cStartEndDateBuff;

    String m_cJsonObject;
    int m_cPos;
    private ScrollView m_cScroll;

    @Nullable
    @Bind(R.id.VIEW_PROFILE_VIEW_TXT)
    TextView m_cViewProfile;


    public DashboardReferralFragment() {
        super();
    }

    public static DashboardReferralFragment newInstance(int pPosition, String pJsonObject) {
        DashboardReferralFragment DashboardReferralFragment = new DashboardReferralFragment();

        Bundle args = new Bundle();
        args.putInt("Position", pPosition);
        args.putString("JsonObject", pJsonObject);
        DashboardReferralFragment.setArguments(args);

        return DashboardReferralFragment;
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
        m_cObjMainView = inflater.inflate(R.layout.dashboards_referral_layout, container, false);
        ButterKnife.bind(this, m_cObjMainView);

        m_cObjMainActivity.m_cObjFragmentBase = DashboardReferralFragment.this;
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
        m_cSelectPeriodSpin.setOnItemSelectedListener(DashboardReferralFragment.this);
        m_cReffSpin.setOnItemSelectedListener(DashboardReferralFragment.this);
        m_cSelectPeriodSpin.setOnItemSelectedListener(DashboardReferralFragment.this);

        m_cReffList = new ArrayList<>();
        m_cReffList.add("Select Referral");
        m_cSpinAdapter = new ArrayAdapter<String>(m_cObjMainActivity, R.layout.spinner_item_bold, m_cReffList);
        m_cReffSpin.setAdapter(m_cSpinAdapter);

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
        //calling D teams api
        HashMap<String, String> lParams = new HashMap<>();
        lParams.put(Constants.INSCLUDE, Constants.REFERRALS);
        placeRequest(Constants.TEAMS_REFERRALS, Types.class, lParams, false);

        //Calling Team api
        placeRequest(Constants.DASHBOARD_REFERRALS, Dashboards.class, null, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick({R.id.VIEW_PROFILE_VIEW_TXT, R.id.SELECT_MONTH_FROM, R.id.SELECT_MONTH_TO,
            R.id.GO_ARROW_LEADS, R.id.GO_ARROW_LOGINS, R.id.GO_ARROW_SANCTION, R.id.GO_ARROW_DISB})
    public void onClick(View v) {
        Intent lObjIntent;
        super.onClick(v);
        switch (v.getId()){
            case R.id.VIEW_PROFILE_VIEW_TXT:
                lObjIntent = new Intent(m_cObjMainActivity, ViewProfile.class);
                if (m_cReffPos > 0) {
                    lObjIntent.putExtra(Constants.MEMBER_UUID, m_cReffDic.get(m_cReffList.get(m_cReffPos)));
                    lObjIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(lObjIntent);
                    reFreshDates();
                }else {
                    m_cObjMainActivity.displaySnack(m_cObjMainView, "Referral Required");
                }
                break;
            case R.id.SELECT_MONTH_FROM:
                showDatePickerDialog(FROM_DATE_PICKER_ID);
                break;
            case R.id.SELECT_MONTH_TO:
                showDatePickerDialog(TO_DATE_PICKER_ID);
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
            case R.id.GO_ARROW_SANCTION:
                if (Integer.parseInt(m_cSancTxtNo.getText().toString()) > 0) {
                    lObjIntent = new Intent(m_cObjMainActivity, DSACustomers.class);
                    lObjIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    lObjIntent.putExtra(Constants.STATUSGROUP, Constants.LOGINS);
                    startActivity(lObjIntent);
                    reFreshDates();
                } else {
                    m_cObjMainActivity.displaySnack(m_cObjMainView, "No Sanctions");
                }
                break;
            case R.id.GO_ARROW_DISB:
                if (Integer.parseInt(m_cDisbTxtNo.getText().toString()) > 0) {
                    lObjIntent = new Intent(m_cObjMainActivity, DSACustomers.class);
                    lObjIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    lObjIntent.putExtra(Constants.STATUSGROUP, Constants.DISBURSEMENTS);
                    startActivity(lObjIntent);
                    reFreshDates();
                } else {
                    m_cObjMainActivity.displaySnack(m_cObjMainView, "No Disbursals");
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
    protected void handleUIMessage(Message pObjMessage) {

    }

    @Override
    public void onAPIResponse(Object response, String apiMethod) {
        Intent lObjIntent;
        super.onAPIResponse(response, apiMethod);
        switch (apiMethod) {
            case Constants.TEAMS_REFERRALS:
                Types llTypes = (Types) response;
                if (llTypes.getData().get(0).getReferrals().getData().size() > 0) {
                    m_cReffList = new ArrayList<>();
                    m_cReffDic = new HashMap<>();
                    m_cReffList.add("Select Referral");
                    for (AgentData lAgentData : llTypes.getData().get(0).getReferrals().getData()) {
                        m_cReffList.add(lAgentData.getFirstName() + " " + lAgentData.getLastName());
                        m_cReffDic.put(lAgentData.getFirstName() + " " + lAgentData.getLastName(), lAgentData.getUuid());
                    }
                    m_cSpinAdapter = new ArrayAdapter<String>(m_cObjMainActivity, R.layout.spinner_item_bold, m_cReffList);
                    m_cReffSpin.setAdapter(m_cSpinAdapter);
                /*if (null != m_cObjectProj) {
                    int lPos = 0;
                    try {
                        lPos = m_cCitiesList.indexOf(m_cObjectProj.getData().getAddress().getData().getCity().getData().getName().toString());
                        m_cSpinCityEdit.setSelection(lPos);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }*/
                }
                m_cObjMainActivity.hideDialog();
                break;
            case Constants.DASHBOARD_REFERRALS:
                Dashboards lDashboards = (Dashboards) response;
                if(null != lDashboards.getData()){
                    try {
                        try {
                            m_cLeadTxtNo.setText("" + lDashboards.getData().getLeads().getCount()!= null ?
                                    lDashboards.getData().getLeads().getCount() : "0");
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        try {
                            m_cLeadAmtNo.setText("" + lDashboards.getData().getLeads().getAmount() != null ?
                                    "\u20B9 "+lDashboards.getData().getLeads().getAmount() : "\u20B9 0");
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        try {
                            m_cLeadPayoutNo.setText("" + lDashboards.getData().getLeads().getPayout() != null ?
                                    "\u20B9 "+lDashboards.getData().getLeads().getPayout() : "\u20B9 0");
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        try {
                            m_cLoginsTxtNo.setText("" + lDashboards.getData().getLogins().getCount()!= null ?
                                    lDashboards.getData().getLogins().getCount() : "0");
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        try {
                            m_cLoginsAmtNo.setText("" + lDashboards.getData().getLogins().getAmount() != null ?
                                    "\u20B9 "+lDashboards.getData().getLogins().getAmount() : "\u20B9 0");
                        }catch (Exception e){
                            e.printStackTrace();
                        }try {
                            m_cLoginsPayoutNo.setText("" + lDashboards.getData().getLogins().getPayout() != null ?
                                    "\u20B9 "+lDashboards.getData().getLogins().getPayout() : "\u20B9 0");
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        try {
                            m_cSancTxtNo.setText("" + lDashboards.getData().getSanctions().getCount()!= null ?
                                    lDashboards.getData().getSanctions().getCount() : "0");
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        try {
                            m_cSancAmtNo.setText("" + lDashboards.getData().getSanctions().getAmount() != null ?
                                    "\u20B9 "+lDashboards.getData().getSanctions().getAmount() : "\u20B9 0");
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        try {
                            m_cSancPayNo.setText("" + lDashboards.getData().getSanctions().getPayout() != null ?
                                    "\u20B9 "+lDashboards.getData().getSanctions().getPayout() : "\u20B9 0");
                        }catch (Exception e){
                            e.printStackTrace();
                        }try {
                            m_cDisbTxtNo.setText("" + lDashboards.getData().getDisbursals().getCount()!= null ?
                                    lDashboards.getData().getDisbursals().getCount() : "0");
                        }catch (Exception e){
                            e.printStackTrace();
                        }try {
                            m_cDisbAmtNo.setText("" + lDashboards.getData().getDisbursals().getAmount() != null ?
                                    "\u20B9"+lDashboards.getData().getDisbursals().getAmount() : "\u20B9 0");
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        try {
                            m_cDisbPayNo.setText("" + lDashboards.getData().getDisbursals().getPayout() != null ?
                                    "\u20B9 "+lDashboards.getData().getDisbursals().getPayout() : "\u20B9 0");
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        try {
                            m_cTotalPaidNo.setText("" + lDashboards.getData().getTotalPaid().getAmount() != null ?
                                    "\u20B9 "+lDashboards.getData().getTotalPaid().getAmount() :  "\u20B9 0");
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        try {
                            m_cTotalPayNo.setText("" + lDashboards.getData().getTotalPayoutEarned().getAmount() != null ?
                                    "\u20B9 "+lDashboards.getData().getTotalPayoutEarned().getAmount() :  "\u20B9 0");
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        try {
                            m_cBalanceAMtNo.setText("" + lDashboards.getData().getBalance().getAmount() != null ?
                                    "\u20B9 "+lDashboards.getData().getBalance().getAmount() :  "\u20B9 0");
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }catch (Exception e){
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
            toMonth = Integer.parseInt(lmonth) -1;
            String lday = String.format("%02d", day);
            toDate = Integer.parseInt(lday);
            toYear = year;
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
//            m_cStartEndDateBuff.append(" to " + lday + "-" + lmonth + "-" + year);
            m_cToDateTxt.setText(lday + "-" + lmonth + "-" + year);
            m_cDatePickerDialog.dismiss();

            m_cObjMainActivity.displayProgressBar(-1, "Loading");
            if(m_cToDateTxt.getText().length() > 0){
                HashMap<String, String> lParams = new HashMap<>();
                if (m_cPeriodPos > 0) {
                    lParams.put(Constants.PERIOD, m_cPeriodSendList.get(m_cPeriodPos));
                }
                if(m_cReffPos > 0){
                    lParams.put(Constants.REFERRAL_UUID, m_cReffDic.get(m_cReffList.get(m_cReffPos)));
                }
                if(m_cFromDateTxt.getText().length() > 0){
                    lParams.put(Constants.FROM, DSAMacros.getDateFormatYYYYMMDD(null, m_cFromDateTxt.getText().toString()));
                }
                lParams.put(Constants.TO, DSAMacros.getDateFormatYYYYMMDD(null, m_cToDateTxt.getText().toString()));
                placeRequest(Constants.DASHBOARD_REFERRALS, Dashboards.class, lParams, false);
            }
        }
    };

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.SELF_SPIN:
                m_cReffPos = position;
                if(m_cReffPos > 0){
                    m_cObjMainActivity.displayProgressBar(-1, "Loading");
                    HashMap<String, String> lParams = new HashMap<>();
                    if (m_cPeriodPos > 0) {
                        lParams.put(Constants.PERIOD, m_cPeriodSendList.get(m_cPeriodPos));
                    }
                    if(m_cReffPos > 0){
                        lParams.put(Constants.REFERRAL_UUID, m_cReffDic.get(m_cReffList.get(m_cReffPos)));
                    }
                    if(m_cFromDateTxt.getText().length() > 0){
                        lParams.put(Constants.FROM, DSAMacros.getDateFormatYYYYMMDD(null, m_cFromDateTxt.getText().toString()));
                    }
                    if(m_cToDateTxt.getText().length() > 0){
                        lParams.put(Constants.TO, DSAMacros.getDateFormatYYYYMMDD(null, m_cToDateTxt.getText().toString()));
                    }
                    placeRequest(Constants.DASHBOARD_REFERRALS, Dashboards.class, lParams, false);
                }
                break;
            case R.id.SELECT_PERIOD_SPIN:
                m_cPeriodPos = position;
                if(m_cPeriodPos > 0){
                    ButterKnife.apply(nameViews, DISABLE);
                    m_cObjMainActivity.displayProgressBar(-1, "Loading");
                    HashMap<String, String> lParams = new HashMap<>();
                    if (m_cPeriodPos > 0) {
                        lParams.put(Constants.PERIOD, m_cPeriodSendList.get(m_cPeriodPos));
                    }
                    if(m_cReffPos > 0){
                        lParams.put(Constants.REFERRAL_UUID, m_cReffDic.get(m_cReffList.get(m_cReffPos)));
                    }
                    if(m_cFromDateTxt.getText().length() > 0){
                        lParams.put(Constants.FROM, DSAMacros.getDateFormatYYYYMMDD(null, m_cFromDateTxt.getText().toString()));
                    }
                    if(m_cToDateTxt.getText().length() > 0){
                        lParams.put(Constants.TO, DSAMacros.getDateFormatYYYYMMDD(null, m_cToDateTxt.getText().toString()));
                    }
                    placeRequest(Constants.DASHBOARD_REFERRALS, Dashboards.class, lParams, false);
                }else if (m_cPeriodPos == 0){
                    ButterKnife.apply(nameViews, ENABLED, true);
                }
                break;
        }
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
}

