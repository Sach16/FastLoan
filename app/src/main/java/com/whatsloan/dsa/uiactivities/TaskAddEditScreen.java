package com.whatsloan.dsa.uiactivities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.android.volley.VolleyError;
import com.whatsloan.dsa.R;
import com.whatsloan.dsa.customviews.RangeTimePickerDialog;
import com.whatsloan.dsa.macros.DSAMacros;
import com.whatsloan.dsa.model.CustomersData;
import com.whatsloan.dsa.model.Loans;
import com.whatsloan.dsa.model.LoansData;
import com.whatsloan.dsa.model.MembersData;
import com.whatsloan.dsa.model.Response;
import com.whatsloan.dsa.model.StatusData;
import com.whatsloan.dsa.model.Statuses;
import com.whatsloan.dsa.model.TasksData;
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
 * Created by S.K. Pissay on 24/3/16.
 */
public class TaskAddEditScreen extends DSABaseActivity implements AdapterView.OnItemSelectedListener {

    @Nullable
    @Bind(R.id.CUSTOMER_NAME)
    TextView m_cTaskNameEdit;

    @Nullable
    @Bind(R.id.PHONE_NO_EDIT)
    TextView m_cPhoneNoEdit;

    @Nullable
    @Bind(R.id.ASSINE_DETAILS)
    LinearLayout m_cAssineDetailsLinLay;

    @Nullable
    @Bind(R.id.TASK_LOANS_SPINNER)
    Spinner m_cSpinLoansEdit;

    @Nullable
    @Bind(R.id.TASK_ASSIGNS_SPINNER)
    Spinner m_cSpinTaskMembers;

    @Nullable
    @Bind(R.id.TASK_STATUS_SPINNER)
    Spinner m_cSpinTaskStatus;

    @Nullable
    @Bind(R.id.TASK_STAGE_SPINNER)
    Spinner m_cSpinTaskStages;

    @Nullable
    @Bind(R.id.TASK_PRIORITY_SPINNER)
    Spinner m_cSpinTaskPriorityStages;

    @Nullable
    @Bind(R.id.TASK_DESCRIPTION_EDIT)
    EditText m_cDescTaskEdit;

    @Nullable
    @Bind(R.id.TASK_DESCRIPTION_TXT_BRACKET)
    TextView m_cDescTasCountTxt;

    @Nullable
    @Bind(R.id.STARTD_TXT_LAY)
    RelativeLayout m_cStartDateRelLay;

    @Nullable
    @Bind(R.id.STARTD_MAIN_TXT)
    TextView m_cStartDateTxt;

    @Nullable
    @Bind(R.id.ENDD_TXT_LAY)
    RelativeLayout m_cEndDateRelLay;

    @Nullable
    @Bind(R.id.ENDD_MAIN_TXT)
    TextView m_cEndDateTxt;

    @Nullable
    @Bind(R.id.STARTT_TXT_LAY)
    RelativeLayout m_cStartTimeRelLay;

    @Nullable
    @Bind(R.id.STARTT_MAIN_TXT)
    TextView m_cStartTimeTxt;

    @Nullable
    @Bind(R.id.ENDT_TXT_LAY)
    RelativeLayout m_cEndTimeRelLay;

    @Nullable
    @Bind(R.id.ENDT_MAIN_TXT)
    TextView m_cEndTimeTxt;

    @Nullable
    @Bind(R.id.SAVE_OR_SUBMIT_TXT)
    TextView m_cSaveSubmitTxt;

    @Nullable
    @Bind(R.id.ADDED_BY_LAY)
    LinearLayout m_cAddedByLay;

    @Nullable
    @Bind(R.id.ADDED_BY_NAME)
    TextView m_cAddedByName;

    @Nullable
    @Bind(R.id.ADDED_BY_DATE)
    TextView m_cAddedByDate;

    @Nullable
    @Bind(R.id.ADDED_BY_TIME)
    TextView m_cAddedByTime;

    @Nullable
    @Bind(R.id.REL_LAY_GROUP)
    RelativeLayout m_cRelLayGrp;

    @Nullable
    @Bind(R.id.TASK_SCROLL_LAY)
    ScrollView m_cScrollGrp;

    private int m_cMembersPos = -1;
    private int m_cLoansPos = -1;
    private int m_cTasksStatusPos = -1;
    private int m_cTasksStagesPos = -1;
    private int m_cTasksPriorityPos = -1;

    ArrayList<String> m_cMembersList;
    HashMap<String, String> m_cMembersDic;
    ArrayList<String> m_cLoansList;
    HashMap<String, String> m_cLoansDic;
    ArrayList<String> m_cStatusList;
    HashMap<String, String> m_cStatusDic;
    ArrayList<String> m_cStagesList;
    HashMap<String, String> m_cStagesDic;
    ArrayList<String> m_cPriorityList;

    ArrayAdapter<String> m_cSpinAdapter;

    public static final int FROM_DATE_PICKER_ID = 101;
    public static final int TO_DATE_PICKER_ID = 102;

    public static final int FROM_TIME_DIALOG_ID = 111;
    public static final int TO_TIME_DIALOG_ID = 112;

    private Calendar m_cCalendar;
    private DatePickerDialog m_cDatePickerDialog;
    private RangeTimePickerDialog m_cTimePickerDialog;
    private TasksData m_cTasksData;
    private CustomersData m_cObjCustomer;
    private String m_cLoanUuidForNewTask;

    private int fromMonth = -1;
    private int fromYear = -1;
    private int fromDate = -1;
    private int toMonth = -1;
    private int toDate = -1;
    private int toYear = -1;

    private int mMinHour = -1;
    private int mMinMinute = -1;
    private int mMaxHour = 100;
    private int mMaxMinute = 100;
    private int mCurrentHour;
    private int mCurrentMinute;

//    StringBuffer m_cStartEndDateBuff;
//    StringBuffer m_cStartEndTimeBuff;

    @Override
    protected void onCreate(Bundle pSavedInstance) {
        super.onCreate(pSavedInstance);
        setContentView(R.layout.task_new_edit_assign);
        ButterKnife.bind(this);
        String ltitle = null;
        if (getIntent().getBooleanExtra("IS_NEW_TASK", false)) {
            ltitle = "New Task Assignment";
            m_cSaveSubmitTxt.setText("Submit");
            m_cObjCustomer = getIntent().getParcelableExtra("CUSTOMER_DETAILS");
            m_cLoanUuidForNewTask = getIntent().getStringExtra("LOAN_UUID");
        } else {
            ltitle = "Edit Task Assignment";
            m_cSaveSubmitTxt.setText("Save");
            m_cTasksData = getIntent().getParcelableExtra("TASKS_DATA");
            m_cObjCustomer = getIntent().getParcelableExtra("CUSTOMER_DETAILS");
        }
        setTitle(ltitle, false, true, true, false);

        init();
    }

    private void init() {
        m_cSpinTaskMembers.setOnItemSelectedListener(this);
        m_cSpinLoansEdit.setOnItemSelectedListener(this);
        m_cSpinTaskStatus.setOnItemSelectedListener(this);
        m_cSpinTaskStages.setOnItemSelectedListener(this);
        m_cSpinTaskPriorityStages.setOnItemSelectedListener(this);

        //uppercase the First char of priorities
        m_cPriorityList = new ArrayList<>();
        m_cPriorityList.add("Select Task Priority");
        m_cPriorityList.add("Low");
        m_cPriorityList.add("High");
        m_cPriorityList.add("Medium");
        m_cSpinAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item_bold, m_cPriorityList);
        m_cSpinTaskPriorityStages.setAdapter(m_cSpinAdapter);

        m_cDescTaskEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                m_cDescTasCountTxt.setText("("+m_cDescTaskEdit.getText().toString().length()+"/162)");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                m_cDescTasCountTxt.setText("("+m_cDescTaskEdit.getText().toString().length()+"/162)");
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        if(null != m_cObjCustomer){
            m_cAssineDetailsLinLay.setVisibility(View.VISIBLE);
            m_cTaskNameEdit.setText(m_cObjCustomer.getFirstName()+" "+m_cObjCustomer.getLastName());
            m_cPhoneNoEdit.setText(m_cObjCustomer.getPhone());
        }else {
            m_cAssineDetailsLinLay.setVisibility(View.GONE);
        }

        if (null != m_cTasksData) {
            m_cAddedByLay.setVisibility(View.VISIBLE);
            try {
                m_cAddedByName.setText(m_cTasksData.getUser().getData().getFirstName() + " " + m_cTasksData.getUser().getData().getLastName());
                m_cAddedByDate.setText(DSAMacros.getDateFormat(null, m_cTasksData.getCreatedAt().getDate(), DSAMacros.DATE_FORMAT_UNDERSC_YYYYMMDD_HHMMSS,
                        DSAMacros.DEFAULT_DATEFORMAT_DDMMYYYY));
                m_cAddedByTime.setText(DSAMacros.getDateFormat(null, m_cTasksData.getCreatedAt().getDate(), DSAMacros.DATE_FORMAT_UNDERSC_YYYYMMDD_HHMMSS,
                        DSAMacros.TIME_FORMAT_HHMM_AM_PM));
            }catch (Exception e){
                e.printStackTrace();
            }

            int lPos = 0;
            try {
                lPos = m_cPriorityList.indexOf(m_cTasksData.getPriority());
                m_cSpinTaskPriorityStages.setSelection(lPos);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try{
                m_cDescTaskEdit.setText(m_cTasksData.getDescription());
                m_cStartDateTxt.setText(DSAMacros.getDateFormat(null, m_cTasksData.getFrom().getDate(), DSAMacros.DATE_FORMAT_UNDERSC_YYYYMMDD_HHMMSS,
                        DSAMacros.DEFAULT_DATEFORMAT_DDMMYYYY));
                m_cEndDateTxt.setText(DSAMacros.getDateFormat(null, m_cTasksData.getTo().getDate(), DSAMacros.DATE_FORMAT_UNDERSC_YYYYMMDD_HHMMSS,
                        DSAMacros.DEFAULT_DATEFORMAT_DDMMYYYY));
            }catch (Exception e){
                e.printStackTrace();
            }

            try{
                m_cStartTimeTxt.setText(DSAMacros.getDateFormat(null, m_cTasksData.getFrom().getDate(), DSAMacros.DATE_FORMAT_UNDERSC_YYYYMMDD_HHMMSS,
                        DSAMacros.DEFAULT_TIMEFORMAT_HHMMSS));
                m_cEndTimeTxt.setText(DSAMacros.getDateFormat(null, m_cTasksData.getTo().getDate(), DSAMacros.DATE_FORMAT_UNDERSC_YYYYMMDD_HHMMSS,
                        DSAMacros.DEFAULT_TIMEFORMAT_HHMMSS));
            }catch (Exception e){
                e.printStackTrace();
            }

        }

        //Calling Cities api
        displayProgressBar(-1, "Loading");
        HashMap<String, String> lParams = new HashMap<>();
        lParams.put(Constants.PAGINATE, Constants.ALL);
        lParams.put(Constants.INSCLUDE, Constants.MEMBERS);
        RequestManager.getInstance(this).placeRequest(Constants.TASKUSERTEAM, TeamMembers.class, this, lParams, false);

        HashMap<String, String> llParams = new HashMap<>();
        llParams.put(Constants.INSCLUDE, Constants.AGENT_BANKS);
        if(null != m_cLoanUuidForNewTask){
            llParams.put(Constants.LOAN_UUID, m_cLoanUuidForNewTask);
        }
        if (null != m_cTasksData){
            llParams.put(Constants.TASK_UUID, m_cTasksData.getUuid());
        }
        RequestManager.getInstance(this).placeRequest(Constants.LOANS, Loans.class, this, llParams, false);

        RequestManager.getInstance(this).placeRequest(Constants.TASK_STATUSES, Statuses.class, this, null, false);

        RequestManager.getInstance(this).placeRequest(Constants.TASK_STAGES, Statuses.class, this, null, false);
    }

    @Override
    protected void handleUIMessage(Message pObjMessage) {

    }

    @OnClick({R.id.SAVE_OR_SUBMIT_TXT, R.id.STARTD_TXT_LAY, R.id.ENDD_TXT_LAY,  R.id.STARTT_TXT_LAY, R.id.ENDT_TXT_LAY, R.id.CANCEL_OR_SUBMIT_TXT})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.SAVE_OR_SUBMIT_TXT:
                if (validate()) {
                    displayProgressBar(-1, "Loading...");
                    HashMap<String, String> lParams = new HashMap<>();
                    try {
                        lParams.put(Constants.LOAN_UUID, m_cLoansDic.get(m_cLoansList.get(m_cLoansPos)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        lParams.put(Constants.MEMBER_UUID_ARRAY, m_cMembersDic.get(m_cMembersList.get(m_cMembersPos)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        lParams.put(Constants.TASKABLE_UUID, m_cLoansDic.get(m_cLoansList.get(m_cLoansPos)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        lParams.put(Constants.TASKABLE_TYPE, "LOAN");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        lParams.put(Constants.TASK_STATUS_UUID, m_cStatusDic.get(m_cStatusList.get(m_cTasksStatusPos)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        lParams.put(Constants.TASK_STAGE_UUID, m_cStagesDic.get(m_cStagesList.get(m_cTasksStagesPos)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        lParams.put(Constants.PRIORITY, m_cPriorityList.get(m_cTasksPriorityPos));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
//                    String[] lDates = m_cDatesTxt.getText().toString().split("to");
//                    String[] lTimes = m_cTimesTxt.getText().toString().split("to");
//                    lParams.put(Constants.FROM, DSAMacros.getDateFormatYYYYMMDD(null, lDates[0].trim()));
//                    lParams.put(Constants.TO, DSAMacros.getDateFormatYYYYMMDD(null, lDates[1].trim()));

                        lParams.put(Constants.FROM, DSAMacros.getDateFormat(null, m_cStartDateTxt.getText().toString(), DSAMacros.DEFAULT_DATEFORMAT_DDMMYYYY,
                                DSAMacros.DEFAULT_DATEFORMAT_YYYYMMDD) + " " + m_cStartTimeTxt.getText().toString());
                        lParams.put(Constants.TO, DSAMacros.getDateFormat(null, m_cEndDateTxt.getText().toString(), DSAMacros.DEFAULT_DATEFORMAT_DDMMYYYY,
                                DSAMacros.DEFAULT_DATEFORMAT_YYYYMMDD) + " " + m_cEndTimeTxt.getText().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        lParams.put(Constants.DESCRIPTION, m_cDescTaskEdit.getText().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (m_cTasksData == null) {
                        RequestManager.getInstance(this).placeRequest(Constants.TASKS, Response.class, this, lParams, true);
                    } else {
                        RequestManager.getInstance(this).placePutRequest(Constants.apiMethodEx(Constants.TASKS, m_cTasksData.getUuid()),
                                Response.class, this, lParams, true);
                    }
                }
                break;
            case R.id.STARTD_TXT_LAY:
                showDatePickerDialog(FROM_DATE_PICKER_ID);
                break;
            case R.id.ENDD_TXT_LAY:
                showDatePickerDialog(TO_DATE_PICKER_ID);
                break;
            case R.id.CANCEL_OR_SUBMIT_TXT:
                onBackPressed();
                break;
            case R.id.STARTT_TXT_LAY:
                showTimePickerDialog(FROM_TIME_DIALOG_ID);
                break;
            case R.id.ENDT_TXT_LAY:
                showTimePickerDialog(TO_TIME_DIALOG_ID);
                break;
        }
    }

    private boolean validate() {
        String ldesc = m_cDescTaskEdit.getText().toString().trim();
        String lstartdate = m_cStartDateTxt.getText().toString().trim();
        String lstarttime = m_cStartTimeTxt.getText().toString().trim();
        String lenddate = m_cEndDateTxt.getText().toString().trim();
        String lendtime = m_cEndTimeTxt.getText().toString().trim();
        boolean lRetVal = false;

        if(m_cMembersPos == 0){
            displaySnack(m_cRelLayGrp, "Select Assigned to");
            m_cScrollGrp.fullScroll(ScrollView.FOCUS_UP);
            return false;
        }else if(m_cLoansPos == 0){
            displaySnack(m_cRelLayGrp, "Select Loan");
            m_cScrollGrp.fullScroll(ScrollView.FOCUS_UP);
            return false;
        }else if (m_cTasksStatusPos == 0){
            displaySnack(m_cRelLayGrp, "Select Task Status");
            m_cScrollGrp.fullScroll(ScrollView.FOCUS_UP);
            return false;
        }else if (m_cTasksStagesPos == 0){
            displaySnack(m_cRelLayGrp, "Select Task Stages");
            m_cScrollGrp.fullScroll(ScrollView.FOCUS_UP);
            return false;
        }else if (m_cTasksPriorityPos == 0){
            displaySnack(m_cRelLayGrp, "Select Task Priority");
            m_cScrollGrp.fullScroll(ScrollView.FOCUS_UP);
            return false;
        }else if (ldesc.isEmpty()){
            displaySnack(m_cRelLayGrp, "Description required");
            m_cScrollGrp.fullScroll(ScrollView.FOCUS_DOWN);
            return false;
        }else if (lstartdate.isEmpty()){
            displaySnack(m_cRelLayGrp, "Select Start Date");
            m_cScrollGrp.fullScroll(ScrollView.FOCUS_DOWN);
            return false;
        }else if (lstarttime.isEmpty()){
            displaySnack(m_cRelLayGrp, "Select Start Time");
            m_cScrollGrp.fullScroll(ScrollView.FOCUS_DOWN);
            return false;
        }else if (lenddate.isEmpty()){
            displaySnack(m_cRelLayGrp, "Select End Date");
            m_cScrollGrp.fullScroll(ScrollView.FOCUS_DOWN);
            return false;
        }else if (lendtime.isEmpty()){
            displaySnack(m_cRelLayGrp, "Select End Time");
            m_cScrollGrp.fullScroll(ScrollView.FOCUS_DOWN);
            return false;
        }else {
            lRetVal = true;
        }
        return lRetVal;
    }

    @Override
    public void onAPIResponse(Object response, String apiMethod) {
        super.onAPIResponse(response, apiMethod);
        switch (apiMethod) {
            case Constants.TASKUSERTEAM:
                TeamMembers lTeamMembers = (TeamMembers) response;
                m_cMembersList = new ArrayList<>();
                m_cMembersDic = new HashMap<>();
                if (lTeamMembers.getData().getMembers().getData().size() > 0) {
                    m_cMembersList.add("Assigned To");
                }
                for (MembersData lMembersData : lTeamMembers.getData().getMembers().getData()) {
                    m_cMembersList.add(lMembersData.getFirstName() + " " + lMembersData.getLastName());
                    m_cMembersDic.put(lMembersData.getFirstName() + " " + lMembersData.getLastName(), lMembersData.getUuid());
                }
                m_cSpinAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item_bold, m_cMembersList);
                m_cSpinTaskMembers.setAdapter(m_cSpinAdapter);
                if (null != m_cTasksData) {
                    int lPos = 0;
                    try {
                        lPos = m_cMembersList.indexOf(m_cTasksData.getMembers().getData().get(0).getFirstName() +
                                " " + m_cTasksData.getMembers().getData().get(0).getLastName());
                        m_cSpinTaskMembers.setSelection(lPos);
                        // Note : removed because single loan is got for edit
                        /*if(lPos > 0){
                            displayProgressBar(-1, "Loading");
                            HashMap<String, String> lParams = new HashMap<>();
                            lParams.put(Constants.INSCLUDE, Constants.AGENT_BANKS);
                            if (m_cMembersPos > 0) {
                                lParams.put(Constants.AGENT_UUID, m_cMembersDic.get(m_cMembersList.get(m_cMembersPos)));
                            }
                            RequestManager.getInstance(this).placeRequest(Constants.LOANS, Loans.class, this, lParams, false);
                        }*/
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                hideDialog();
                break;
            case Constants.LOANS:
                Loans lLoans = (Loans) response;
                m_cLoansList = new ArrayList<>();
                m_cLoansDic = new HashMap<>();
                if (lLoans.getData().size() > 0) {
                    m_cLoansList.add("Select Loan");
                }
                for (LoansData lLoansData : lLoans.getData()) {
                    m_cLoansList.add(lLoansData.getAmount() + "/" + lLoansData.getType().getData().getKey() + "/" + DSAMacros.s2l(lLoansData.getStatus().getData().getKey()) + "/" + lLoansData.getUser().getData().getFirstName());
                    m_cLoansDic.put(lLoansData.getAmount() + "/" + lLoansData.getType().getData().getKey() + "/" + DSAMacros.s2l(lLoansData.getStatus().getData().getKey()) + "/" + lLoansData.getUser().getData().getFirstName(), lLoansData.getUuid());
                }
                m_cSpinAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item_bold, m_cLoansList);
                m_cSpinLoansEdit.setAdapter(m_cSpinAdapter);
                hideDialog();
                break;
            case Constants.TASK_STATUSES:
                Statuses lStatuses = (Statuses) response;
                m_cStatusList = new ArrayList<>();
                m_cStatusDic = new HashMap<>();
                if (lStatuses.getData().size() > 0) {
                    m_cStatusList.add("Task Status");
                }
                for (StatusData lStatusData : lStatuses.getData()) {
                    m_cStatusList.add(lStatusData.getLabel());
                    m_cStatusDic.put(lStatusData.getLabel(), lStatusData.getUuid());
                }
                m_cSpinAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item_bold, m_cStatusList);
                m_cSpinTaskStatus.setAdapter(m_cSpinAdapter);
                if (null != m_cTasksData) {
                    int lPos = 0;
                    String lLabel = m_cTasksData.getStatus().getData().getLabel();
                    try {
                        lPos = m_cStatusList.indexOf(lLabel);
                        m_cSpinTaskStatus.setSelection(lPos);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if(lLabel.equalsIgnoreCase(DSAMacros.COMPLETED_STATUS)||lLabel.equalsIgnoreCase(DSAMacros.CANCELLED_STATUS)
                            ||lLabel.equalsIgnoreCase(DSAMacros.OVERDUE_STATUS))
                        m_cSpinTaskStatus.setEnabled(false);
                }
                hideDialog();
                break;
            case Constants.TASK_STAGES:
                Statuses lStages = (Statuses) response;
                m_cStagesList = new ArrayList<>();
                m_cStagesDic = new HashMap<>();
                if (lStages.getData().size() > 0) {
                    m_cStagesList.add("Task Stages");
                }
                for (StatusData lStatusData : lStages.getData()) {
                    m_cStagesList.add(lStatusData.getLabel());
                    m_cStagesDic.put(lStatusData.getLabel(), lStatusData.getUuid());
                }
                m_cSpinAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item_bold, m_cStagesList);
                m_cSpinTaskStages.setAdapter(m_cSpinAdapter);
                if (null != m_cTasksData) {
                    int lPos = 0;
                    try {
                        lPos = m_cStagesList.indexOf(m_cTasksData.getStage().getData().getLabel());
                        m_cSpinTaskStages.setSelection(lPos);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                hideDialog();
                break;
            case Constants.TASKS:
                Response lResponse = (Response) response;
                onBackPressed();
                break;
            default:
                if (apiMethod.contains("/")) {
                    Response llResponse = (Response) response;
                    onBackPressed();
                }
                break;
        }
    }

    @Override
    public void onErrorResponse(VolleyError error, String apiMethod) {
        hideDialog();
        switch (apiMethod) {
            case Constants.TASKS:
                displaySnack(m_cRelLayGrp, "Enter All Fields");
                break;
            default:
                if (apiMethod.contains("/")) {
                    displaySnack(m_cRelLayGrp, "Enter All Fields");
                }else {
                    super.onErrorResponse(error, apiMethod);
                }
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.TASK_ASSIGNS_SPINNER:
                m_cMembersPos = position;
                /*if (position > 0) {
                    displayProgressBar(-1, "Loading");
                    HashMap<String, String> lParams = new HashMap<>();
                    lParams.put(Constants.INSCLUDE, Constants.AGENT_BANKS);
                    if (m_cMembersPos > 0) {
                        lParams.put(Constants.AGENT_UUID, m_cMembersDic.get(m_cMembersList.get(m_cMembersPos)));
                    }
                    RequestManager.getInstance(this).placeRequest(Constants.LOANS, Loans.class, this, lParams, false);
                }*/
                break;
            case R.id.TASK_LOANS_SPINNER:
                m_cLoansPos = position;
                break;
            case R.id.TASK_STATUS_SPINNER:
                m_cTasksStatusPos = position;
                break;
            case R.id.TASK_STAGE_SPINNER:
                m_cTasksStagesPos = position;
                break;
            case R.id.TASK_PRIORITY_SPINNER:
                m_cTasksPriorityPos = position;
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
                if (null != m_cTasksData){
                    try {
                        m_cDatePickerDialog.getDatePicker().setMinDate(DSAMacros.convertStringToDate(m_cTasksData.getFrom().getDate(), DSAMacros.DATE_FORMAT_UNDERSC_YYYYMMDD_HHMMSS).getTime());
                    }catch (Exception e){
                        e.printStackTrace();
                        m_cDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                    }
                }else {
                    m_cDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                }
                m_cDatePickerDialog.setTitle("Select From Date");
                break;
            case TO_DATE_PICKER_ID:
                if (toDate > 0)
                    m_cDatePickerDialog = new DatePickerDialog(this, myToDateListener, toYear,
                            toMonth, toDate);
                else
                    m_cDatePickerDialog = new DatePickerDialog(this, myToDateListener, m_cCalendar.get(Calendar.YEAR),
                            m_cCalendar.get(Calendar.MONTH), m_cCalendar.get(Calendar.DAY_OF_MONTH));
                try {
                    m_cDatePickerDialog.getDatePicker().setMinDate(DSAMacros.convertStringToDate(m_cStartDateTxt.getText().toString(), DSAMacros.DEFAULT_DATEFORMAT_DDMMYYYY).getTime());
                }catch (Exception e){
                    e.printStackTrace();
                    m_cDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                }
                m_cDatePickerDialog.setTitle("Select To Date");
                break;
        }
        m_cDatePickerDialog.show();
    }

    private void showTimePickerDialog(int pId) {
        m_cCalendar = Calendar.getInstance();
        switch (pId) {
            case FROM_TIME_DIALOG_ID:
                // set time picker as current time
//                m_cTimePickerDialog = new RangeTimePickerDialog(this,
//                        fromTimePickerListener, m_cCalendar.get(Calendar.HOUR_OF_DAY), m_cCalendar.get(Calendar.MINUTE), false);
                try {
                    setMin(m_cCalendar.get(Calendar.HOUR_OF_DAY), m_cCalendar.get(Calendar.MINUTE));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    setMax(23, 59);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                timePicker(true);
                break;
            case TO_TIME_DIALOG_ID:
                try {
                    if (DSAMacros.convertStringToDate(m_cEndDateTxt.getText().toString(), DSAMacros.DEFAULT_DATEFORMAT_DDMMYYYY)
                            .after(DSAMacros.convertStringToDate(m_cStartDateTxt.getText().toString(), DSAMacros.DEFAULT_DATEFORMAT_DDMMYYYY))) {
                        reFreshTime();
                    }else {
                        try {
                            setMin(Integer.parseInt(m_cStartTimeTxt.getText().toString().split(":")[0]),
                                    Integer.parseInt(m_cStartTimeTxt.getText().toString().split(":")[1]));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            setMax(23, 59);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                timePicker(false);
//                m_cTimePickerDialog = new RangeTimePickerDialog(this,
//                        toTimePickerListener, m_cCalendar.get(Calendar.HOUR_OF_DAY), m_cCalendar.get(Calendar.MINUTE), false);
                break;

        }
//        m_cTimePickerDialog.show();
    }

    private void reFreshTime() {
        mMinHour = -1;
        mMinMinute = -1;
        mMaxHour = 100;
        mMaxMinute = 100;
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
            m_cStartDateTxt.setText(lday + "-" + lmonth + "-" + year);
            m_cDatePickerDialog.dismiss();
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
            m_cEndDateTxt.setText(lday + "-" + lmonth + "-" + year);
            m_cDatePickerDialog.dismiss();
        }
    };

    private RangeTimePickerDialog.OnTimeSetListener fromTimePickerListener = new RangeTimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
            StringBuffer m_cStartTimeBuff = new StringBuffer();
            m_cStartTimeBuff.append(pad(selectedHour))
                    .append(":").append(pad(selectedMinute)).append(":").append("00");
            m_cStartTimeTxt.setText(m_cStartTimeBuff.toString());
            m_cTimePickerDialog.dismiss();
        }
    };

    private RangeTimePickerDialog.OnTimeSetListener toTimePickerListener = new RangeTimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
            StringBuffer m_cEndTimeBuff = new StringBuffer();
            m_cEndTimeBuff.append(pad(selectedHour))
                    .append(":").append(pad(selectedMinute)).append(":").append("00");
            m_cEndTimeTxt.setText(m_cEndTimeBuff.toString());
            m_cTimePickerDialog.dismiss();
        }
    };

    private void timePicker(final boolean pIsFrom) {

        final View dialogView = View.inflate(this, R.layout.time_picker_dialog, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        mCurrentHour = m_cCalendar.getTime().getHours();
        mCurrentMinute = m_cCalendar.getTime().getMinutes();

        final int[] lhourOfDay = new int[1];
        final int[] lminute = new int[1];
        final boolean[] validTime = new boolean[1];
        TimePicker datePicker = (TimePicker) dialogView.findViewById(R.id.time_picker);
        datePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                lhourOfDay[0] = hourOfDay;
                lminute[0] = minute;
            }
        });

        final TextView lTxtView;
        if(pIsFrom){
            lTxtView = m_cStartTimeTxt;
        }else {
            lTxtView = m_cEndTimeTxt;
        }

        dialogView.findViewById(R.id.DATE_TIME_SET).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((lhourOfDay[0]  < mMinHour ) || (lhourOfDay[0]  == mMinHour && lminute[0] < mMinMinute))
                        || ((lhourOfDay[0]  > mMaxHour) || (lhourOfDay[0]  == mMaxHour && lminute[0] > mMaxMinute))) {
                    validTime[0] = false;
                } else {
                    validTime[0] = true;
                }
                if (validTime[0]) {
                    mCurrentHour = lhourOfDay[0] ;
                    mCurrentMinute = lminute[0];

                    StringBuffer m_cStartTimeBuff = new StringBuffer();
                    m_cStartTimeBuff.append(pad(mCurrentHour))
                            .append(":").append(pad(mCurrentMinute)).append(":").append("00");
                    lTxtView.setText(m_cStartTimeBuff.toString());
                }
                else {
                    if(pIsFrom){
                        StringBuffer m_cStartTimeBuff = new StringBuffer();
                    m_cStartTimeBuff.append(pad(m_cCalendar.get(Calendar.HOUR_OF_DAY)))
                            .append(":").append(pad(m_cCalendar.get(Calendar.MINUTE))).append(":").append("00");
                    lTxtView.setText(m_cStartTimeBuff.toString());
                    }else {
                        displaySnack(m_cRelLayGrp, "End Time should be than Start Time");
                        m_cScrollGrp.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                }
                alertDialog.dismiss();
            }
        });

        dialogView.findViewById(R.id.DIALOG_CANCEL).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        dialogView.findViewById(R.id.DIALOG_CLOSE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

//        datePicker.setMaxDate(m_cCalendar.getTimeInMillis());

        /*ViewGroup ll = (ViewGroup) datePicker.getChildAt(0);
        ViewGroup ll2 = (ViewGroup) ll.getChildAt(0);
        int lId1 = ll2.getId();
        int lId2 = ll2.getChildAt(0).getId();
        int lId3 = ll2.getChildAt(1).getId();
        ll2.getChildAt(0).setVisibility(View.GONE);*/
//        ((ViewGroup) datePicker.getChildAt(0)).findViewById(Resources.getSystem().getIdentifier("day", "id", "android")).setVisibility(View.GONE);

        alertDialog.setView(dialogView);
        alertDialog.show();
    }

    public void setMin(int hour, int minute) {
        mMinHour = hour;
        mMinMinute = minute;
    }

    public void setMax(int hour, int minute) {
        mMaxHour = hour;
        mMaxMinute = minute;
    }
}
