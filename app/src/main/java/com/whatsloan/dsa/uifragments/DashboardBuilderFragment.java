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
import com.whatsloan.dsa.model.BuilderAll;
import com.whatsloan.dsa.model.BuilderData;
import com.whatsloan.dsa.model.Dashboards;
import com.whatsloan.dsa.network.Constants;
import com.whatsloan.dsa.uiactivities.DSACustomers;
import com.whatsloan.dsa.uiactivities.DSALeads;
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
public class DashboardBuilderFragment extends DSAFragmentBaseClass implements AdapterView.OnItemSelectedListener {

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
    Spinner m_cBuildersSpin;

    @Nullable
    @Bind(R.id.SELECT_PERIOD_SPIN)
    Spinner m_cSelectPeriodSpin;

    @Nullable
    @Bind(R.id.LEADS_TXT_NO)
    TextView m_cLeadTxtNo;

    @Nullable
    @Bind(R.id.LEADS_AMT_NO)
    TextView m_cLeadAmtNo;

    @Nullable
    @Bind(R.id.LOGINS_TXT_NO)
    TextView m_cLoginsTxtNo;

    @Nullable
    @Bind(R.id.LOGINS_AMT_NO)
    TextView m_cLoginsAmtNo;

    @Nullable
    @Bind(R.id.LOGINS_TXT_FINAL)
    TextView m_cFinalTxtNo;

    @Nullable
    @Bind(R.id.LOGINS_AMT_FINAL)
    TextView m_cFinalAmtNo;

    @Nullable
    @Bind(R.id.LOGINS_TXT_PART)
    TextView m_cPartTxtNo;

    @Nullable
    @Bind(R.id.LOGINS_AMT_PART)
    TextView m_cPartAmtNo;

    @Nullable
    @Bind(R.id.LOGINS_TXT_FULL)
    TextView m_cFullTxtNo;

    @Nullable
    @Bind(R.id.LOGINS_AMT_FULL)
    TextView m_cFullAmtNo;

    @Nullable
    @Bind(R.id.LEADS_TXT_PAYOUT)
    TextView m_cPayoutTxtNo;

    @Nullable
    @Bind(R.id.LEADS_AMT_PAYOUT)
    TextView m_cPayoutAmtNo;

    @Nullable
    @Bind(R.id.GO_ARROW_LEADS)
    ImageView m_cGoArrowLeads;

    @Nullable
    @Bind(R.id.GO_ARROW_LOGINS)
    ImageView m_cGoArrowLogins;

    @Nullable
    @Bind(R.id.GO_ARROW_FINAL)
    ImageView m_cGoArrowFinal;

    @Nullable
    @Bind(R.id.GO_ARROW_PART)
    ImageView m_cGoArrowPart;

    @Nullable
    @Bind(R.id.GO_ARROW_FULL)
    ImageView m_cGoArrowFull;

    ArrayList<String> m_cBuilderList;
    HashMap<String, String> m_cBuilderDic;
    ArrayAdapter<String> m_cSpinAdapter;

    ArrayList<String> m_cPeriodList;
    ArrayList<String> m_cPeriodSendList;

    private int m_cBuildersPos = -1;
    private int m_cPeriodPos = -1;

    public static final int FROM_DATE_PICKER_ID = 111;
    public static final int TO_DATE_PICKER_ID = 112;

    private int fromMonth = -1;
    private int fromYear = -1;
    private int fromDate = -1;
    private int toMonth = -1;
    private int toDate = -1;
    private int toYear = -1;

//    StringBuffer m_cStartEndDateBuff;
    private Calendar m_cCalendar;
    private DatePickerDialog m_cDatePickerDialog;

    String m_cJsonObject;
    int m_cPos;
    private ScrollView m_cScroll;

    public DashboardBuilderFragment() {
        super();
    }

    public static DashboardBuilderFragment newInstance(int pPosition, String pJsonObject) {
        DashboardBuilderFragment DashboardBuilderFragment = new DashboardBuilderFragment();

        Bundle args = new Bundle();
        args.putInt("Position", pPosition);
        args.putString("JsonObject", pJsonObject);
        DashboardBuilderFragment.setArguments(args);

        return DashboardBuilderFragment;
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
        m_cObjMainView = inflater.inflate(R.layout.dashboards_builder_layout, container, false);
        ButterKnife.bind(this, m_cObjMainView);

        m_cObjMainActivity.m_cObjFragmentBase = DashboardBuilderFragment.this;
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
        m_cSelectPeriodSpin.setOnItemSelectedListener(DashboardBuilderFragment.this);
        m_cBuildersSpin.setOnItemSelectedListener(DashboardBuilderFragment.this);
        m_cSelectPeriodSpin.setOnItemSelectedListener(DashboardBuilderFragment.this);

        m_cBuilderList = new ArrayList<>();
        m_cBuilderList.add("Select Builder");
        m_cSpinAdapter = new ArrayAdapter<String>(m_cObjMainActivity, R.layout.spinner_item_bold, m_cBuilderList);
        m_cBuildersSpin.setAdapter(m_cSpinAdapter);

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
        placeRequest(Constants.BUILDERS, BuilderAll.class, lllParams, false);

        //calling D teams api
        placeRequest(Constants.DASHBOARD_BUILDERS, Dashboards.class, null, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void handleUIMessage(Message pObjMessage) {

    }

    @OnClick({R.id.SELECT_MONTH_FROM, R.id.SELECT_MONTH_TO, R.id.GO_ARROW_LEADS, R.id.GO_ARROW_LOGINS,
            R.id.GO_ARROW_FINAL, R.id.GO_ARROW_PART, R.id.GO_ARROW_FULL})
    public void onClick(View v) {
        Intent lObjIntent;
        super.onClick(v);
        switch (v.getId()) {
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
            case R.id.GO_ARROW_FINAL:
                if (Integer.parseInt(m_cFinalTxtNo.getText().toString()) > 0) {
                    lObjIntent = new Intent(m_cObjMainActivity, DSACustomers.class);
                    lObjIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    lObjIntent.putExtra(Constants.STATUSGROUP, Constants.FIRST_DISB);
                    startActivity(lObjIntent);
                    reFreshDates();
                } else {
                    m_cObjMainActivity.displaySnack(m_cObjMainView, "No Final Disbursements");
                }
                break;
            case R.id.GO_ARROW_PART:
                if (Integer.parseInt(m_cPartTxtNo.getText().toString()) > 0) {
                    lObjIntent = new Intent(m_cObjMainActivity, DSACustomers.class);
                    lObjIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    lObjIntent.putExtra(Constants.STATUSGROUP, Constants.PART_DISB);
                    startActivity(lObjIntent);
                    reFreshDates();
                } else {
                    m_cObjMainActivity.displaySnack(m_cObjMainView, "No Part Disbursements");
                }
                break;
            case R.id.GO_ARROW_FULL:
                if (Integer.parseInt(m_cFullTxtNo.getText().toString()) > 0) {
                    lObjIntent = new Intent(m_cObjMainActivity, DSACustomers.class);
                    lObjIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    lObjIntent.putExtra(Constants.STATUSGROUP, Constants.FULL_DISB);
                    startActivity(lObjIntent);
                    reFreshDates();
                } else {
                    m_cObjMainActivity.displaySnack(m_cObjMainView, "No Builder Disbursements");
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
            case Constants.BUILDERS:
                BuilderAll builderAll = (BuilderAll) response;
                if (builderAll.getData().size() > 0) {
                    m_cBuilderList = new ArrayList<>();
                    m_cBuilderDic = new HashMap<>();
                    m_cBuilderList.add("Select Builder");
                    for (BuilderData lBuilderData : builderAll.getData()) {
                        m_cBuilderList.add(lBuilderData.getName());
                        m_cBuilderDic.put(lBuilderData.getName(), lBuilderData.getUuid());
                    }
                    m_cSpinAdapter = new ArrayAdapter<String>(m_cObjMainActivity, R.layout.spinner_item_bold, m_cBuilderList);
                    m_cBuildersSpin.setAdapter(m_cSpinAdapter);
                /*if (null != m_cObjectProj) {
                    int lPos = 0;
                    try {
                        lPos = m_cBuilderList.indexOf(m_cObjectProj.getData().getBuilderName().toString());
                        m_cSpinBuilderEdit.setSelection(lPos);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }*/
                }
                m_cObjMainActivity.hideDialog();
                break;
            case Constants.DASHBOARD_BUILDERS:
                Dashboards lDashboards = (Dashboards) response;
                if (null != lDashboards.getData()) {
                    try {
                        try {
                            m_cLeadAmtNo.setText(lDashboards.getData().getLeads().getAmount() != null ?
                                    "\u20B9 " + lDashboards.getData().getLeads().getAmount() : "\u20B9 0");
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
                            m_cFinalTxtNo.setText(lDashboards.getData().getFinalDisbursement().getCount() != null ?
                                    lDashboards.getData().getFinalDisbursement().getCount() : "0");

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            m_cFinalAmtNo.setText(lDashboards.getData().getFinalDisbursement().getAmount() != null ?
                                    "\u20B9 " + lDashboards.getData().getFinalDisbursement().getAmount() : "\u20B9 0");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            m_cPartTxtNo.setText(lDashboards.getData().getPartDisbursement().getCount() != null ?
                                    lDashboards.getData().getPartDisbursement().getCount() : "0");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            m_cPartAmtNo.setText(lDashboards.getData().getPartDisbursement().getAmount() != null ?
                                    "\u20B9 " + lDashboards.getData().getPartDisbursement().getAmount() : "\u20B9 0");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            m_cFullTxtNo.setText(lDashboards.getData().getFirstDisbursement().getCount() != null ?
                                    lDashboards.getData().getFirstDisbursement().getCount() : "0");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            m_cFullAmtNo.setText(lDashboards.getData().getFirstDisbursement().getAmount() != null ?
                                    "\u20B9 " + lDashboards.getData().getFirstDisbursement().getAmount() : "\u20B9 0");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            m_cPayoutTxtNo.setText(lDashboards.getData().getPayout().getPercentage() + "%" != null ?
                                    lDashboards.getData().getPayout().getPercentage() + "%" : "0");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            m_cPayoutAmtNo.setText(lDashboards.getData().getPayout().getAmount() != null ?
                                    "\u20B9 " + lDashboards.getData().getPayout().getAmount() : "\u20B9 0");
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.BUILDER_SPINNER:
                m_cBuildersPos = position;
                if (m_cBuildersPos > 0) {
                    m_cObjMainActivity.displayProgressBar(-1, "Loading");
                    HashMap<String, String> lParams = new HashMap<>();
                    if (m_cPeriodPos > 0) {
                        lParams.put(Constants.PERIOD, m_cPeriodSendList.get(m_cPeriodPos));
                    }
                    if (m_cBuildersPos > 0) {
                        lParams.put(Constants.MEMBER_UUID, m_cBuilderDic.get(m_cBuilderList.get(m_cBuildersPos)));
                    }
                    if (m_cFromDateTxt.getText().length() > 0) {
                        lParams.put(Constants.FROM, DSAMacros.getDateFormatYYYYMMDD(null, m_cFromDateTxt.getText().toString()));
                    }
                    if (m_cToDateTxt.getText().length() > 0) {
                        lParams.put(Constants.TO, DSAMacros.getDateFormatYYYYMMDD(null, m_cToDateTxt.getText().toString()));
                    }
                    placeRequest(Constants.DASHBOARD_BUILDERS, Dashboards.class, lParams, false);
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
                    if (m_cBuildersPos > 0) {
                        lParams.put(Constants.MEMBER_UUID, m_cBuilderDic.get(m_cBuilderList.get(m_cBuildersPos)));
                    }
                    if (m_cFromDateTxt.getText().length() > 0) {
                        lParams.put(Constants.FROM, DSAMacros.getDateFormatYYYYMMDD(null, m_cFromDateTxt.getText().toString()));
                    }
                    if (m_cToDateTxt.getText().length() > 0) {
                        lParams.put(Constants.TO, DSAMacros.getDateFormatYYYYMMDD(null, m_cToDateTxt.getText().toString()));
                    }
                    placeRequest(Constants.DASHBOARD_BUILDERS, Dashboards.class, lParams, false);
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
//            m_cStartEndDateBuff.append(" to " + lday + "-" + lmonth + "-" + year);
            m_cToDateTxt.setText(lday + "-" + lmonth + "-" + year);
            m_cDatePickerDialog.dismiss();

            m_cObjMainActivity.displayProgressBar(-1, "Loading");
            if (m_cToDateTxt.getText().length() > 0) {
                HashMap<String, String> lParams = new HashMap<>();
                if (m_cPeriodPos > 0) {
                    lParams.put(Constants.PERIOD, m_cPeriodSendList.get(m_cPeriodPos));
                }
                if (m_cBuildersPos > 0) {
                    lParams.put(Constants.MEMBER_UUID, m_cBuilderDic.get(m_cBuilderList.get(m_cBuildersPos)));
                }
                if (m_cFromDateTxt.getText().length() > 0) {
                    lParams.put(Constants.FROM, DSAMacros.getDateFormatYYYYMMDD(null, m_cFromDateTxt.getText().toString()));
                }
                lParams.put(Constants.TO, DSAMacros.getDateFormatYYYYMMDD(null, m_cToDateTxt.getText().toString()));
                placeRequest(Constants.DASHBOARD_BUILDERS, Dashboards.class, lParams, false);
            }
        }
    };
}

