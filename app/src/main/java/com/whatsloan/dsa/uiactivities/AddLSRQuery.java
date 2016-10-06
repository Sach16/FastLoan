package com.whatsloan.dsa.uiactivities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.graphics.Color;
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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.android.volley.VolleyError;
import com.whatsloan.dsa.R;
import com.whatsloan.dsa.customviews.RangeTimePickerDialog;
import com.whatsloan.dsa.macros.DSAMacros;
import com.whatsloan.dsa.model.MembersData;
import com.whatsloan.dsa.model.Project;
import com.whatsloan.dsa.model.ProjectStruct;
import com.whatsloan.dsa.model.ProjectsAll;
import com.whatsloan.dsa.model.QueriesData;
import com.whatsloan.dsa.model.Response;
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
 * Created by S.K. Pissay on 25/3/16.
 */
public class AddLSRQuery extends DSABaseActivity implements AdapterView.OnItemSelectedListener {

    // status Id's
    @Nullable
    @Bind(R.id.STATUS_IMG_PROPERTY)
    ImageView m_cPropertyStatImg;

    @Nullable
    @Bind(R.id.STATUS_LINE_PROPERTY)
    ImageView m_cPropertyStatLine;

    @Nullable
    @Bind(R.id.STATUS_PENDING_IMG)
    ImageView m_cPendingStatImg;

    @Nullable
    @Bind(R.id.STATUS_PENDING_LINE)
    ImageView m_cPendingStatLine;

    @Nullable
    @Bind(R.id.STATUS_ADDLSR_IMG)
    ImageView m_cAddlsrStatImg;

    @Nullable
    @Bind(R.id.STATUS_ADDLSR_LINE)
    ImageView m_cAddlsrStatLine;

    @Nullable
    @Bind(R.id.STATUS_QUERY_IMG)
    ImageView m_cQueryStatImg;

    @Nullable
    @Bind(R.id.STATUS_QUERY_LINE)
    ImageView m_cQueryStatLine;

    @Nullable
    @Bind(R.id.STATUS_ASSIGNED_IMG)
    ImageView m_cAssignStatImg;

    @Nullable
    @Bind(R.id.STATUS_ASSIGNED_LINE)
    ImageView m_cAssignStatLine;

    @Nullable
    @Bind(R.id.STATUS_STARTD_IMG)
    ImageView m_cDateStatImg;

    @Nullable
    @Bind(R.id.STATUS_STARTD_LINE)
    ImageView m_cDateStatLine;

    @Nullable
    @Bind(R.id.STATUS_ENDD_IMG)
    ImageView m_cDateEndImg;

    @Nullable
    @Bind(R.id.STATUS_ENDD_LINE)
    ImageView m_cDateEndLine;

    @Nullable
    @Bind(R.id.STATUS_STARTT_IMG)
    ImageView m_cTimeStatImg;

    @Nullable
    @Bind(R.id.STATUS_STARTT_LINE)
    ImageView m_cTimeStatLine;

    @Nullable
    @Bind(R.id.STATUS_ENDT_IMG)
    ImageView m_cTimeEndImg;

    @Nullable
    @Bind(R.id.STATUS_ENDT_LINE)
    ImageView m_cTimeEndLine;

    @Nullable
    @Bind(R.id.STATUS_STATUS_IMG)
    ImageView m_cStatusStatImg;

    /*@Nullable
    @Bind(R.id.STATUS_STATUS_LINE)
    ImageView m_cStatusStatLine;*/

    //edit Id's

    @Nullable
    @Bind(R.id.PROPERTY_SPINNER)
    Spinner m_cSpinPropertyEdit;

    @Nullable
    @Bind(R.id.PENDING_SPINNER)
    Spinner m_cSpinPendingEdit;

    @Nullable
    @Bind(R.id.ADDLSR_MAIN_TXT)
    EditText m_cAddLsredit;

    @Nullable
    @Bind(R.id.QUERY_TXT_COUNT)
    TextView m_cQueryTxtCount;

    @Nullable
    @Bind(R.id.QUERY_TXT_LAY)
    RelativeLayout m_cQuerydateLayPickEdit;

    @Nullable
    @Bind(R.id.QUERY_MAIN_TXT)
    TextView m_cQueryDateTxtedit;

    @Nullable
    @Bind(R.id.ASSIGNED_SPINNER)
    Spinner m_cSpinMemAssignEdit;

    @Nullable
    @Bind(R.id.STARTD_TXT_LAY)
    RelativeLayout m_cDateStartLayPickEdit;

    @Nullable
    @Bind(R.id.ENDD_TXT_LAY)
    RelativeLayout m_cDateEndLayPickEdit;

    @Nullable
    @Bind(R.id.STARTD_MAIN_TXT)
    TextView m_cStartDateTxtedit;

    @Nullable
    @Bind(R.id.ENDD_MAIN_TXT)
    TextView m_cEndDateTxtedit;

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
    @Bind(R.id.STATUS_SPINNER)
    Spinner m_cSpinStatusEdit;

    @Nullable
    @Bind(R.id.REL_LAY_GROUP)
    RelativeLayout m_cRelGrp;

    final String[] MONTH = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep",
            "Oct", "Nov", "Dec"};
    public static final int QUERY_DATE_PICKER_ID = 100;
    public static final int FROM_DATE_PICKER_ID = 101;
    public static final int TO_DATE_PICKER_ID = 102;
    public static final int FROM_TIME_DIALOG_ID = 111;
    public static final int TO_TIME_DIALOG_ID = 112;
    private Calendar m_cCalendar;
    private DatePickerDialog m_cDatePickerDialog;
    private RangeTimePickerDialog m_cTimePickerDialog;
    Project m_cObjectProj;
    QueriesData m_cQueryData;

    private int m_cAssigneePos = -1;
    private int m_cPendingPos = -1;
    private int m_cProjectPos = -1;
    private int m_cStatusPos = -1;

    private int mMinHour = -1;
    private int mMinMinute = -1;
    private int mMaxHour = 100;
    private int mMaxMinute = 100;
    private int mCurrentHour;
    private int mCurrentMinute;

    ArrayList<String> m_cAssigneeList;
    HashMap<String, String> m_cAssigneeDic;
    ArrayList<String> m_cPendingList;
    HashMap<String, String> m_cPendingDic;
    ArrayList<String> m_cProjectList;
    HashMap<String, String> m_cProjectDic;
    ArrayList<String> m_cStatusList;
    HashMap<String, String> m_cStatusDic;

//    StringBuffer m_cStartEndDateBuff;
//    StringBuffer m_cStartEndTimeBuff;

    ArrayAdapter<String> m_cSpinAdapter;

    private int fromMonth = -1;
    private int fromYear = -1;
    private int fromDate = -1;
    private int toMonth = -1;
    private int toDate = -1;
    private int toYear = -1;

    @Override
    protected void onCreate(Bundle pSavedInstance) {
        super.onCreate(pSavedInstance);
        setContentView(R.layout.add_lsr_query);
        ButterKnife.bind(this);

        m_cObjectProj = getIntent().getParcelableExtra("ProjectObject");
        m_cQueryData = getIntent().getParcelableExtra("LSRObject");
        if (m_cQueryData != null) {
            setTitle(m_cQueryData.getQuery(), false, true, true, false);
        } else {
            setTitle("Add LSR Query", false, true, true, false);
        }
        init();

    }

    private void init() {

        if (m_cQueryData != null) {
            try {
                m_cAddLsredit.setText(m_cQueryData.getQuery().toString());
                colorView(m_cAddlsrStatImg, m_cAddlsrStatLine, true);
            }catch (Exception e){
                e.printStackTrace();
            }

            m_cSpinPropertyEdit.setEnabled(false);
//            m_cSpinPendingEdit.setEnabled(false);
            try{
                m_cStartDateTxtedit.setText(DSAMacros.getDateFormat(null, m_cQueryData.getStartDate().getDate(), DSAMacros.DATE_FORMAT_UNDERSC_YYYYMMDD_HHMMSS,
                        DSAMacros.DEFAULT_DATEFORMAT_DDMMYYYY));
                colorView(m_cDateStatImg, m_cDateStatLine, true);
                m_cEndDateTxtedit.setText(DSAMacros.getDateFormat(null, m_cQueryData.getEndDate().getDate(), DSAMacros.DATE_FORMAT_UNDERSC_YYYYMMDD_HHMMSS,
                        DSAMacros.DEFAULT_DATEFORMAT_DDMMYYYY));
                colorView(m_cDateEndImg, m_cDateEndLine, true);
            }catch (Exception e){
                e.printStackTrace();
            }

            try{
                m_cStartTimeTxt.setText(DSAMacros.getDateFormat(null, m_cQueryData.getStartDate().getDate(), DSAMacros.DATE_FORMAT_UNDERSC_YYYYMMDD_HHMMSS,
                        DSAMacros.DEFAULT_TIMEFORMAT_HHMMSS));
                colorView(m_cTimeStatImg, m_cTimeStatLine, true);
                m_cEndTimeTxt.setText(DSAMacros.getDateFormat(null, m_cQueryData.getEndDate().getDate(), DSAMacros.DATE_FORMAT_UNDERSC_YYYYMMDD_HHMMSS,
                        DSAMacros.DEFAULT_TIMEFORMAT_HHMMSS));
                colorView(m_cTimeEndImg, m_cTimeEndLine, true);
            }catch (Exception e){
                e.printStackTrace();
            }

            try{
                m_cQueryDateTxtedit.setText(DSAMacros.getDateFormat(null, m_cQueryData.getRaisedDate().getDate(), DSAMacros.DATE_FORMAT_UNDERSC_YYYYMMDD_HHMMSS,
                        DSAMacros.DEFAULT_DATEFORMAT_DDMMYYYY));
                colorView(m_cQueryStatImg, m_cQueryStatLine, true);
            }catch (Exception e){
                e.printStackTrace();
            }

        }

        m_cStatusList = new ArrayList<>();
        m_cStatusList.add("Select Status");
        m_cStatusList.add("PENDING");
        m_cStatusList.add("APPROVED");
        m_cSpinAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item_bold, m_cStatusList);
        m_cSpinStatusEdit.setAdapter(m_cSpinAdapter);

        m_cAddLsredit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                m_cQueryTxtCount.setText("("+(count+after+start)+"/162)");
                m_cQueryTxtCount.setText("("+m_cAddLsredit.getText().toString().length()+"/162)");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                m_cQueryTxtCount.setText("(" + (1 + before + start) + "/162)");
                m_cQueryTxtCount.setText("("+m_cAddLsredit.getText().toString().length()+"/162)");
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() > 0) {
                    colorView(m_cAddlsrStatImg, m_cAddlsrStatLine, true);
                } else {
                    colorView(m_cAddlsrStatImg, m_cAddlsrStatLine, false);
                }

            }
        });
        m_cSpinPropertyEdit.setOnItemSelectedListener(this);
        m_cSpinPendingEdit.setOnItemSelectedListener(this);
        m_cSpinMemAssignEdit.setOnItemSelectedListener(this);
        m_cSpinStatusEdit.setOnItemSelectedListener(this);

        m_cPendingList = new ArrayList<>();
        m_cPendingList.add("Select Pending With");
        m_cPendingList.add("DSA");
        m_cPendingList.add("Customer");
        m_cPendingList.add("Builder");
        m_cSpinAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item_bold, m_cPendingList);
        m_cSpinPendingEdit.setAdapter(m_cSpinAdapter);

        if (null != m_cObjectProj) {
            int lPos = 0;
            try {
                lPos = m_cPendingList.indexOf(m_cQueryData.getPendingWith());
                m_cSpinPendingEdit.setSelection(lPos);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (null != m_cObjectProj) {
            int llPos = 0;
            try {
                llPos = m_cStatusList.indexOf(m_cQueryData.getStatus());
                m_cSpinStatusEdit.setSelection(llPos);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //Calling Team api
        HashMap<String, String> lParams = new HashMap<>();
        lParams.put(Constants.INSCLUDE, Constants.MEMBERS);
        RequestManager.getInstance(this).placeRequest(Constants.USERTEAM, TeamMembers.class, this, lParams, false);

        //Calling Projects api
        displayProgressBar(-1, "Loading...");
        HashMap<String, String> llParams = new HashMap<>();
        llParams.put(Constants.PAGINATE, Constants.ALL);
        RequestManager.getInstance(this).placeRequest(Constants.PROJECTSAPPROVAL, ProjectsAll.class, this, llParams, false);
    }

    @Override
    protected void handleUIMessage(Message pObjMessage) {

    }

    @OnClick({R.id.CANCEL_BTN_TXT, R.id.SUBMIT_BTN_TXT, R.id.QUERY_TXT_LAY, R.id.STARTD_TXT_LAY, R.id.ENDD_TXT_LAY,
            R.id.STARTT_TXT_LAY, R.id.ENDT_TXT_LAY})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.CANCEL_BTN_TXT:
                onBackPressed();
                break;
            case R.id.SUBMIT_BTN_TXT:
                if(null != m_cQueryData) {
                    if (validateEdit()) {
                        displayProgressBar(-1, "Loading...");
                        HashMap<String, String> lParams = new HashMap<>();
                        lParams.put(Constants.PROJECTID, m_cObjectProj.getData().getUuid());
                        lParams.put(Constants.QUERY, m_cAddLsredit.getText().toString());
                        lParams.put(Constants.ASSIGNEDTO, m_cAssigneeDic.get(m_cAssigneeList.get(m_cAssigneePos)));
                        lParams.put(Constants.RAISEDDATE, DSAMacros.getDateFormatYYYYMMDD(null, m_cQueryDateTxtedit.getText().toString().trim()));
                        String[] lDates = m_cStartDateTxtedit.getText().toString().split("to");
                        lParams.put(Constants.STARTDATE,
                                DSAMacros.getDateFormat(null, m_cStartDateTxtedit.getText().toString(), DSAMacros.DEFAULT_DATEFORMAT_DDMMYYYY,
                                        DSAMacros.DEFAULT_DATEFORMAT_YYYYMMDD)+" "+m_cStartTimeTxt.getText().toString());
                        lParams.put(Constants.ENDDATE,
                                DSAMacros.getDateFormat(null, m_cEndDateTxtedit.getText().toString(), DSAMacros.DEFAULT_DATEFORMAT_DDMMYYYY,
                                        DSAMacros.DEFAULT_DATEFORMAT_YYYYMMDD)+" "+m_cEndTimeTxt.getText().toString());
                        //TODO uncomment below
                        lParams.put(Constants.PENDINGWITH, m_cPendingList.get(m_cPendingPos));
                        lParams.put(Constants.DUEDATE, DSAMacros.getDateFormatYYYYMMDD(null, m_cQueryDateTxtedit.getText().toString().trim()));
                        lParams.put(Constants.STATUS, m_cStatusList.get(m_cStatusPos));
                        RequestManager.getInstance(this).placePutRequest(Constants.apiMethodEx(Constants.QUERIES, m_cQueryData.getUuid()), Response.class, this, lParams, true);
                    }
                }else {
                    if (validateCred()) {
                        displayProgressBar(-1, "Loading...");
                        HashMap<String, String> lParams = new HashMap<>();
                        lParams.put(Constants.PROJECTID, m_cProjectDic.get(m_cProjectList.get(m_cProjectPos)));
                        lParams.put(Constants.QUERY, m_cAddLsredit.getText().toString());
                        lParams.put(Constants.ASSIGNEDTO, m_cAssigneeDic.get(m_cAssigneeList.get(m_cAssigneePos)));
                        lParams.put(Constants.RAISEDDATE, DSAMacros.getDateFormatYYYYMMDD(null, m_cQueryDateTxtedit.getText().toString().trim()));
                        String[] lDates = m_cStartDateTxtedit.getText().toString().split("to");
                        lParams.put(Constants.STARTDATE,
                                DSAMacros.getDateFormat(null, m_cStartDateTxtedit.getText().toString(), DSAMacros.DEFAULT_DATEFORMAT_DDMMYYYY,
                                        DSAMacros.DEFAULT_DATEFORMAT_YYYYMMDD)+" "+m_cStartTimeTxt.getText().toString());
                        lParams.put(Constants.ENDDATE,
                                DSAMacros.getDateFormat(null, m_cEndDateTxtedit.getText().toString(), DSAMacros.DEFAULT_DATEFORMAT_DDMMYYYY,
                                        DSAMacros.DEFAULT_DATEFORMAT_YYYYMMDD)+" "+m_cEndTimeTxt.getText().toString());
                        //TODO uncomment below
                        lParams.put(Constants.PENDINGWITH, m_cPendingList.get(m_cPendingPos));
                        lParams.put(Constants.DUEDATE, DSAMacros.getDateFormatYYYYMMDD(null, m_cQueryDateTxtedit.getText().toString().trim()));
                        lParams.put(Constants.STATUS, m_cStatusList.get(m_cStatusPos));
                        RequestManager.getInstance(this).placeRequest(Constants.QUERIES, Response.class, this, lParams, true);
                    }
                }
                break;
            case R.id.QUERY_TXT_LAY:
                showDatePickerDialog(QUERY_DATE_PICKER_ID);
                break;
            case R.id.STARTD_TXT_LAY:
                showDatePickerDialog(FROM_DATE_PICKER_ID);
                break;
            case R.id.ENDD_TXT_LAY:
                showDatePickerDialog(TO_DATE_PICKER_ID);
                break;
            case R.id.STARTT_TXT_LAY:
                showTimePickerDialog(FROM_TIME_DIALOG_ID);
                break;
            case R.id.ENDT_TXT_LAY:
                showTimePickerDialog(TO_TIME_DIALOG_ID);
                break;
        }
    }

    private boolean validateCred() {
        boolean lRetVal = false;
        String lquery = m_cAddLsredit.getText().toString().trim();
        String lqueryDate = m_cQueryDateTxtedit.getText().toString().trim();
        String lStartDate = m_cStartDateTxtedit.getText().toString().trim();
        String lEndDate = m_cEndDateTxtedit.getText().toString().trim();
        String lStarttime = m_cStartTimeTxt.getText().toString().trim();
        String lEndtime = m_cEndTimeTxt.getText().toString().trim();
//        String lTimeRange = m_cStartTimeTxtedit.getText().toString().trim();
        if (m_cSpinPropertyEdit.getId() > 0) {
            colorView(m_cPropertyStatImg, m_cPropertyStatLine, true);
            if (m_cPendingPos > 0) {
                colorView(m_cPendingStatImg, m_cPendingStatLine, true);
                if (lquery.length() > 0 && isAlphaNumeric(lquery)) {
                    colorView(m_cAddlsrStatImg, m_cAddlsrStatLine, true);
                    if (lqueryDate.length() > 0) {
                        colorView(m_cQueryStatImg, m_cQueryStatLine, true);
                        if (m_cAssigneePos > 0) {
                            colorView(m_cAssignStatImg, m_cAssignStatLine, true);
                            if (lStartDate.length() > 0) {
                                colorView(m_cDateStatImg, m_cDateStatLine, true);
                                if (lStarttime.length() > 0) {
                                    colorView(m_cTimeStatImg, m_cTimeStatLine, true);
                                    if (lEndDate.length() > 0) {
                                        colorView(m_cDateEndImg, m_cDateEndLine, true);
                                        if (lEndtime.length() > 0) {
                                            colorView(m_cTimeEndImg, m_cTimeEndLine, true);
                                            if (m_cStatusPos > 0) {
                                                colorView(m_cStatusStatImg, null, true);
                                                lRetVal = true;
                                            } else {
                                                colorView(m_cStatusStatImg, null, false);
                                            }
                                        } else {
                                            colorView(m_cTimeEndImg, m_cTimeEndLine, false);
                                        }
                                    } else {
                                        colorView(m_cDateEndImg, m_cDateEndLine, false);
                                    }
                                } else {
                                    colorView(m_cTimeStatImg, m_cTimeStatLine, false);
                                }
                            } else {
                                colorView(m_cDateStatImg, m_cDateStatLine, false);
                            }
                        } else {
                            colorView(m_cAssignStatImg, m_cAssignStatLine, false);
                        }
                    } else {
                        colorView(m_cQueryStatImg, m_cQueryStatLine, false);
                    }
                } else {
                    colorView(m_cAddlsrStatImg, m_cAddlsrStatLine, false);
                }
            } else {
                colorView(m_cPendingStatImg, m_cPendingStatLine, false);
            }
        } else {
            colorView(m_cPropertyStatImg, m_cPropertyStatLine, false);
        }
        return lRetVal;
    }

    private boolean validateEdit() {
        boolean lRetVal = false;
        String lquery = m_cAddLsredit.getText().toString().trim();
        String lqueryDate = m_cQueryDateTxtedit.getText().toString().trim();
        String lStartDate = m_cStartDateTxtedit.getText().toString().trim();
        String lEndDate = m_cEndDateTxtedit.getText().toString().trim();
        String lStarttime = m_cStartTimeTxt.getText().toString().trim();
        String lEndtime = m_cEndTimeTxt.getText().toString().trim();
//        String lTimeRange = m_cStartTimeTxtedit.getText().toString().trim();
        if (m_cPendingPos > 0) {
            colorView(m_cPendingStatImg, m_cPendingStatLine, true);
            if (lquery.length() > 0 && isAlphaNumeric(lquery)) {
                colorView(m_cAddlsrStatImg, m_cAddlsrStatLine, true);
                if (lqueryDate.length() > 0) {
                    colorView(m_cQueryStatImg, m_cQueryStatLine, true);
                    if (m_cAssigneePos > 0) {
                        colorView(m_cAssignStatImg, m_cAssignStatLine, true);
                        if (lStartDate.length() > 0) {
                            colorView(m_cDateStatImg, m_cDateStatLine, true);
                            if (lStarttime.length() > 0) {
                                colorView(m_cTimeStatImg, m_cTimeStatLine, true);
                                if (lEndDate.length() > 0) {
                                    colorView(m_cDateEndImg, m_cDateEndLine, true);
                                    if (lEndtime.length() > 0) {
                                        colorView(m_cTimeEndImg, m_cTimeEndLine, true);
                                            if (m_cStatusPos > 0) {
                                                colorView(m_cStatusStatImg, null, true);
                                                lRetVal = true;
                                            } else {
                                                colorView(m_cStatusStatImg, null, false);
                                            }
                                    } else {
                                        colorView(m_cTimeEndImg, m_cTimeEndLine, false);
                                    }
                                } else {
                                    colorView(m_cDateEndImg, m_cDateEndLine, false);
                                }
                            } else {
                                colorView(m_cTimeStatImg, m_cTimeStatLine, false);
                            }
                        } else {
                            colorView(m_cDateStatImg, m_cDateStatLine, false);
                        }
                    } else {
                        colorView(m_cAssignStatImg, m_cAssignStatLine, false);
                    }
                } else {
                    colorView(m_cQueryStatImg, m_cQueryStatLine, false);
                }
            } else {
                colorView(m_cAddlsrStatImg, m_cAddlsrStatLine, false);
            }
        } else {
            colorView(m_cPendingStatImg, m_cPendingStatLine, false);
        }
        return lRetVal;
    }

    @Override
    public void onAPIResponse(Object response, String apiMethod) {
        super.onAPIResponse(response, apiMethod);
        switch (apiMethod) {
            case Constants.PROJECTSAPPROVAL:
                ProjectsAll lProjectsAll = (ProjectsAll) response;
                m_cProjectList = new ArrayList<>();
                m_cProjectDic = new HashMap<>();
                if (lProjectsAll.getProjectStruct().size() > 0) {
                    m_cProjectList.add("Select Project");
                }
                for (ProjectStruct lProject : lProjectsAll.getProjectStruct()) {
                    m_cProjectList.add(lProject.getProject().getData().getName());
                    m_cProjectDic.put(lProject.getProject().getData().getName(), lProject.getProject().getData().getUuid());
                }
                m_cSpinAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item_bold, m_cProjectList);
                m_cSpinPropertyEdit.setAdapter(m_cSpinAdapter);
                if (null != m_cQueryData) {
                    int lPos = 0;
                    try {
                        lPos = m_cProjectList.indexOf(m_cObjectProj.getData().getName());
                        m_cSpinPropertyEdit.setSelection(lPos);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                hideDialog();
                break;
            case Constants.USERTEAM:
                TeamMembers lTeamMembers = (TeamMembers) response;
                m_cAssigneeList = new ArrayList<>();
                m_cAssigneeDic = new HashMap<>();
                if (lTeamMembers.getData().getMembers().getData().size() > 0) {
                    m_cAssigneeList.add("Select Member");
                }
                for (MembersData lMembersData : lTeamMembers.getData().getMembers().getData()) {
                    m_cAssigneeList.add(lMembersData.getFirstName() + " " + lMembersData.getLastName());
                    m_cAssigneeDic.put(lMembersData.getFirstName() + " " + lMembersData.getLastName(), lMembersData.getUuid());
                }
                m_cSpinAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item_bold, m_cAssigneeList);
                m_cSpinMemAssignEdit.setAdapter(m_cSpinAdapter);
                if (null != m_cQueryData) {
                    int lPos = 0;
                    try {
                        String lName = m_cQueryData.getAssignee().getData().getFirstName() + " " + m_cQueryData.getAssignee().getData().getLastName();
                        lPos = m_cAssigneeList.indexOf(lName);
                        m_cSpinMemAssignEdit.setSelection(lPos);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                hideDialog();
                break;
            case Constants.QUERIES:
//                Response lResponse = (Response) response;
                hideDialog();
                onBackPressed();
                break;
            default:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onErrorResponse(VolleyError error, String apiMethod) {
        super.onErrorResponse(error, apiMethod);
        switch (apiMethod) {
            case Constants.PROJECTSAPPROVAL:
                hideDialog();
                break;
            case Constants.USERTEAM:
                hideDialog();
                break;
            case Constants.QUERIES:
//                Response lResponse = (Response) response;
                hideDialog();
                onBackPressed();
                break;
            default:
                hideDialog();
                break;
        }
    }

    private void colorView(ImageView pImg, ImageView pLine, boolean pState) {
        if (pState) {
            try {
                pImg.setImageResource(R.drawable.cricle_tick);
                pLine.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                pImg.setImageResource(R.drawable.circlee);
                pLine.setBackgroundColor(Color.RED);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.PROPERTY_SPINNER:
                m_cProjectPos = position;
                if (position > 0) {
                    colorView(m_cPropertyStatImg, m_cPropertyStatLine, true);
                } else if (position == 0) {
//                    colorView(m_cBuilderStatImg, m_cBuilderStatLine, false);
                }
                break;
            case R.id.PENDING_SPINNER:
                m_cPendingPos = position;
                if (position > 0) {
                    colorView(m_cPendingStatImg, m_cPendingStatLine, true);
                } else if (position == 0) {
//                    colorView(m_cTeamStatImg, m_cBankStatLine, false);
                }
                break;
            case R.id.ASSIGNED_SPINNER:
                m_cAssigneePos = position;
                if (position > 0) {
                    colorView(m_cAssignStatImg, m_cAssignStatLine, true);
                } else if (position == 0) {
//                    colorView(m_cStatusStatImg, m_cStatusStatLine, false);
                }
                break;
            case R.id.STATUS_SPINNER:
                m_cStatusPos = position;
                if (position > 0) {
                    colorView(m_cStatusStatImg, null, true);
                } else if (position == 0) {
//                    colorView(m_cStatusStatImg, m_cStatusStatLine, false);
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void showDatePickerDialog(int pId) {
        m_cCalendar = Calendar.getInstance();
        switch (pId) {
            case QUERY_DATE_PICKER_ID:
                m_cDatePickerDialog = new DatePickerDialog(this, myQueryDateListener, m_cCalendar.get(Calendar.YEAR),
                        m_cCalendar.get(Calendar.MONTH), m_cCalendar.get(Calendar.DAY_OF_MONTH));
                m_cDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                break;
            case FROM_DATE_PICKER_ID:
                if (fromDate > 0)
                    m_cDatePickerDialog = new DatePickerDialog(this, myFromDateListener, fromYear,
                            fromMonth, fromDate);
                else
                    m_cDatePickerDialog = new DatePickerDialog(this, myFromDateListener, m_cCalendar.get(Calendar.YEAR),
                            m_cCalendar.get(Calendar.MONTH), m_cCalendar.get(Calendar.DAY_OF_MONTH));
                m_cDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
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
                    m_cDatePickerDialog.getDatePicker().setMinDate(DSAMacros.convertStringToDate(m_cStartDateTxtedit.getText().toString(), DSAMacros.DEFAULT_DATEFORMAT_DDMMYYYY).getTime());
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
                    if (DSAMacros.convertStringToDate(m_cEndDateTxtedit.getText().toString(), DSAMacros.DEFAULT_DATEFORMAT_DDMMYYYY)
                            .after(DSAMacros.convertStringToDate(m_cStartDateTxtedit.getText().toString(), DSAMacros.DEFAULT_DATEFORMAT_DDMMYYYY))) {
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
            m_cStartDateTxtedit.setText(lday + "-" + lmonth + "-" + year);
            m_cDatePickerDialog.dismiss();
            colorView(m_cDateStatImg, m_cDateStatLine, true);
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
            m_cEndDateTxtedit.setText(lday + "-" + lmonth + "-" + year);
            m_cDatePickerDialog.dismiss();
            colorView(m_cDateEndImg, m_cDateEndLine, true);
        }
    };

    private DatePickerDialog.OnDateSetListener myQueryDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int year, int month, int day) {
            String lmonth = String.format("%02d", month + 1);
            String lday = String.format("%02d", day);
            m_cQueryDateTxtedit.setText(lday + "-" + lmonth + "-" + year);
            m_cDatePickerDialog.dismiss();
            colorView(m_cQueryStatImg, m_cQueryStatLine, true);
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
            colorView(m_cTimeStatImg, m_cTimeStatLine, true);
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
            colorView(m_cTimeEndImg, m_cTimeEndLine, true);
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
                    if(pIsFrom){
                        colorView(m_cTimeStatImg, m_cTimeStatLine, true);
                    }else {
                        colorView(m_cTimeEndImg, m_cTimeEndLine, true);
                    }
                }
                else {
                    if(pIsFrom){
                        StringBuffer m_cStartTimeBuff = new StringBuffer();
                        m_cStartTimeBuff.append(pad(m_cCalendar.get(Calendar.HOUR_OF_DAY)))
                                .append(":").append(pad(m_cCalendar.get(Calendar.MINUTE))).append(":").append("00");
                        lTxtView.setText(m_cStartTimeBuff.toString());
                        colorView(m_cTimeStatImg, m_cTimeStatLine, true);
                    }else {
                        displaySnack(m_cRelGrp, "End Time should be than Start Time");
                        colorView(m_cTimeEndImg, m_cTimeEndLine, true);
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