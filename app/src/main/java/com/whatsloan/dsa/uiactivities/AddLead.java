package com.whatsloan.dsa.uiactivities;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.whatsloan.dsa.R;
import com.whatsloan.dsa.model.AgentData;
import com.whatsloan.dsa.model.BuilderAll;
import com.whatsloan.dsa.model.BuilderData;
import com.whatsloan.dsa.model.CityAll;
import com.whatsloan.dsa.model.CityData;
import com.whatsloan.dsa.model.LeadsSrcDatum;
import com.whatsloan.dsa.model.ProjectData;
import com.whatsloan.dsa.model.Projects;
import com.whatsloan.dsa.model.Response;
import com.whatsloan.dsa.model.Sources;
import com.whatsloan.dsa.model.Types;
import com.whatsloan.dsa.network.Constants;
import com.whatsloan.dsa.network.RequestManager;
import com.whatsloan.dsa.uiframework.DSABaseActivity;
import com.whatsloan.dsa.utils.DecimalDigitsInputFilter;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by S.K. Pissay on 2/5/16.
 */
public class AddLead extends DSABaseActivity implements AdapterView.OnItemSelectedListener {

    // status Id's

    @Nullable
    @Bind(R.id.STATUS_LEAD_IMG)
    ImageView m_cLeadStatImg;

    @Nullable
    @Bind(R.id.STATUS_LEAD_LINE)
    ImageView m_cLeadStatLine;

    @Nullable
    @Bind(R.id.STATUS_PHONE_IMG)
    ImageView m_cPhoneStatImg;

    @Nullable
    @Bind(R.id.STATUS_PHONE_LINE)
    ImageView m_cPhoneStatLine;

    @Nullable
    @Bind(R.id.STATUS_EMAIL_IMG)
    ImageView m_cEmailStatImg;

    @Nullable
    @Bind(R.id.STATUS_EMAIL_LINE)
    ImageView m_cEmailStatLine;

    @Nullable
    @Bind(R.id.STATUS_CITY_IMG)
    ImageView m_cCityStatImg;

    @Nullable
    @Bind(R.id.STATUS_CITY_LINE)
    ImageView m_cCityStatLine;

    @Nullable
    @Bind(R.id.STATUS_LOANTYPE_IMG)
    ImageView m_cLoantypeStatImg;

    @Nullable
    @Bind(R.id.STATUS_LOANTYPE_LINE)
    ImageView m_cLoantypeStatLine;

    @Nullable
    @Bind(R.id.STATUS_PROPVERF_IMG)
    ImageView m_cisPropStatImg;

    @Nullable
    @Bind(R.id.STATUS_PROPVERF_LINE)
    ImageView m_cisPropStatLine;

    @Nullable
    @Bind(R.id.STATUS_IMG_BUILDER)
    ImageView m_cBuilderStatImg;

    @Nullable
    @Bind(R.id.STATUS_LINE_BUILDER)
    ImageView m_cBuilderStatLine;

    @Nullable
    @Bind(R.id.STATUS_PROJECT_IMG)
    ImageView m_cProjectStatImg;

    @Nullable
    @Bind(R.id.STATUS_PROJECT_LINE)
    ImageView m_cProjectStatLine;

    @Nullable
    @Bind(R.id.STATUS_LOANAMT_IMG)
    ImageView m_cLoanamtStatImg;

    @Nullable
    @Bind(R.id.STATUS_LOANAMT_LINE)
    ImageView m_cLoanamtStatLine;

    @Nullable
    @Bind(R.id.STATUS_NETSALARY_IMG)
    ImageView m_cNetsalStatImg;

    @Nullable
    @Bind(R.id.STATUS_NETSALARY_LINE)
    ImageView m_cNetsalStatLine;

    @Nullable
    @Bind(R.id.STATUS_LOANEMI_IMG)
    ImageView m_cLoanemiStatImg;

    @Nullable
    @Bind(R.id.STATUS_LOANEMI_LINE)
    ImageView m_cLoanemiStatLine;

    @Nullable
    @Bind(R.id.STATUS_COMPANYNAME_IMG)
    ImageView m_cComapnynameStatImg;

    @Nullable
    @Bind(R.id.STATUS_COMPANYNAME_LINE)
    ImageView m_cComapnynameStatLine;

    @Nullable
    @Bind(R.id.STATUS_LEADSOURCE_IMG)
    ImageView m_cLeadsrcStatImg;

    @Nullable
    @Bind(R.id.STATUS_LEADSOURCE_LINE)
    ImageView m_cLeadsrcStatLine;

    @Nullable
    @Bind(R.id.STATUS_REFERRAL_IMG)
    ImageView m_cReffStatImg;

    @Nullable
    @Bind(R.id.STATUS_REFERRAL_LINE)
    ImageView m_cReffStatLine;

    //edit Id's

    @Nullable
    @Bind(R.id.LEAD_DESCRIPTION_EDIT)
    EditText m_cLeadDescEdit;

    @Nullable
    @Bind(R.id.PHONE_DESCRIPTION_EDIT)
    EditText m_cPhoneDescEdit;

    @Nullable
    @Bind(R.id.EMAIL_DESCRIPTION_EDIT)
    EditText m_cEmailDescEdit;

    @Nullable
    @Bind(R.id.CITY_SPINNER)
    Spinner m_cSpinCityEdit;

    @Nullable
    @Bind(R.id.LOANTYPE_SPINNER)
    Spinner m_cSpinLoantypeEdit;

    @Nullable
    @Bind(R.id.LOANAMT_DESCRIPTION_EDIT)
    EditText m_cLoanamtDescEdit;

    @Nullable
    @Bind(R.id.NETSALARY_DESCRIPTION_EDIT)
    EditText m_cNetsalDescEdit;

    @Nullable
    @Bind(R.id.LOANEMI_DESCRIPTION_EDIT)
    EditText m_cLoanemiDescEdit;

    @Nullable
    @Bind(R.id.COMPANYNAME_DESCRIPTION_EDIT)
    EditText m_cCompnameDescEdit;

    @Nullable
    @Bind(R.id.LEADSOURCE_SPINNER)
    Spinner m_cSpinLeadsrcEdit;

    @Nullable
    @Bind(R.id.BUILDER_SPINNER)
    Spinner m_cSpinBuilderEdit;

    @Nullable
    @Bind(R.id.PROJECT_SPINNER)
    Spinner m_cSpinProjectEdit;

    @Nullable
    @Bind(R.id.SUBMIT_BTN_TXT)
    TextView m_cSubmitBtnTxt;

    @Nullable
    @Bind(R.id.CANCEL_BTN_TXT)
    TextView m_cCancelBtnTxt;

    @Nullable
    @Bind(R.id.REFERRAL_SPINNER)
    Spinner m_cSpinReffEdit;

    @Nullable
    @Bind(R.id.REFERRAL_LAY)
    RelativeLayout m_cReffRelLay;

    @Nullable
    @Bind(R.id.PROPVERF_LAY)
    RelativeLayout m_cPropverfRelLay;

    @Nullable
    @Bind(R.id.PROPVERF_SPINNER)
    RadioGroup m_cRadioGroupPropverf;

    @Nullable
    @Bind(R.id.BUILDER_LAY)
    RelativeLayout m_cBuilderRelLay;

    @Nullable
    @Bind(R.id.PROJECT_LAY)
    RelativeLayout m_cProjectRelLay;

    ArrayList<String> m_cTypesList;
    HashMap<String, String> m_cTypesDic;
    ArrayList<String> m_cSourcesList;
    HashMap<String, String> m_cSourcesDic;
    ArrayList<String> m_cCitiesList;
    HashMap<String, String> m_cCitiesDic;
    ArrayList<String> m_cReffList;
    HashMap<String, String> m_cReffDic;
    ArrayList<String> m_cProjectList;
    HashMap<String, String> m_cProjectDic;
    ArrayList<String> m_cBuilderList;
    HashMap<String, String> m_cBuilderDic;
    ArrayAdapter<String> m_cSpinAdapter;

    private int m_cCitiesPos = -1;
    private int m_cSourcesPos = -1;
    private int m_cTypesPos = -1;
    private int m_cReffPos = -1;
    private int m_cBuilderPos = -1;
    private int m_cProjectPos = -1;

    private int m_cVerPos = -1;

    @Override
    protected void onCreate(Bundle pSavedInstance) {
        super.onCreate(pSavedInstance);
        setContentView(R.layout.add_lead);
        ButterKnife.bind(this);

        /*if (m_cQueryData != null) {
            setTitle(m_cQueryData.getQuery(), false, true, true, false);
        } else {*/
        setTitle("Add Lead", false, true, true, false);
        /*}*/
        init();

    }

    private void init() {
        m_cLoanamtDescEdit.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
        m_cNetsalDescEdit.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});
        m_cLoanemiDescEdit.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});

        m_cSpinCityEdit.setOnItemSelectedListener(this);
        m_cSpinLoantypeEdit.setOnItemSelectedListener(this);
        m_cSpinLeadsrcEdit.setOnItemSelectedListener(this);
        m_cSpinReffEdit.setOnItemSelectedListener(this);
        m_cSpinBuilderEdit.setOnItemSelectedListener(this);
        m_cSpinProjectEdit.setOnItemSelectedListener(this);

        m_cRadioGroupPropverf.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                colorView(m_cisPropStatImg, m_cisPropStatLine, true);
                View radioButton = m_cRadioGroupPropverf.findViewById(checkedId);

                m_cVerPos = m_cRadioGroupPropverf.indexOfChild(radioButton) + 1;

                if (m_cVerPos == 1) {
                    m_cBuilderRelLay.setVisibility(View.VISIBLE);
                    m_cProjectRelLay.setVisibility(View.VISIBLE);

                    //call builder api
                    displayProgressBar(-1, "Loading");
                    RequestManager.getInstance(AddLead.this).placeRequest(Constants.BUILDERS_GETBUILDERS, BuilderAll.class, AddLead.this, null, false);

                    HashMap<String, String> lParams = new HashMap<>();
                    lParams.put(Constants.INSCLUDE, Constants.BUILDER);
                    RequestManager.getInstance(AddLead.this).placeRequest(Constants.PROJECTS_GETPROJECT, Projects.class, AddLead.this, lParams, false);
                } else {
                    m_cBuilderRelLay.setVisibility(View.GONE);
                    m_cProjectRelLay.setVisibility(View.GONE);
                }
            }
        });

        checkTextWatch();

        displayProgressBar(-1, "Loading");
        HashMap<String, String> lParams = new HashMap<>();
        lParams.put(Constants.PAGINATE, Constants.ALL);
        RequestManager.getInstance(this).placeRequest(Constants.CITIES, CityAll.class, this, lParams, false);

        RequestManager.getInstance(this).placeRequest(Constants.LOANS_TYPE, Types.class, this, lParams, false);

        RequestManager.getInstance(this).placeRequest(Constants.LEADS_SOURCE, Sources.class, this, lParams, false);
    }

    private void checkTextWatch() {
        ArrayList<EditText> editTextList = new ArrayList<>();
        editTextList.add(m_cLeadDescEdit);
        editTextList.add(m_cPhoneDescEdit);
        editTextList.add(m_cEmailDescEdit);
        editTextList.add(m_cLoanamtDescEdit);
        editTextList.add(m_cNetsalDescEdit);
        editTextList.add(m_cLoanemiDescEdit);
        editTextList.add(m_cCompnameDescEdit);
        
        for (final EditText editText : editTextList){
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    checkNowWithIds(editText);
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    checkNowWithIds(editText);
                }

                @Override
                public void afterTextChanged(Editable s) {
                    checkNowWithIds(editText);
                }
            });
        }

    }

    private void checkNowWithIds(EditText editText) {
        final String lleaddesc = m_cLeadDescEdit.getText().toString().trim();
        final String lphone = m_cPhoneDescEdit.getText().toString().trim();
        final String lemail = m_cEmailDescEdit.getText().toString().trim();
        final String lloanamt = m_cLoanamtDescEdit.getText().toString().trim();
        final String lnetsal = m_cNetsalDescEdit.getText().toString().trim();
        final String lloanemi = m_cLoanemiDescEdit.getText().toString().trim();
        final String lcompname = m_cCompnameDescEdit.getText().toString().trim();

        switch (editText.getId()){
            case R.id.LEAD_DESCRIPTION_EDIT:
                if (lleaddesc.length() > 0 && isAlphabetic(lleaddesc)) {
                    colorView(m_cLeadStatImg, m_cLeadStatLine, true);
                }else {
                    colorView(m_cLeadStatImg, m_cLeadStatLine, false);
                }
                break;
            case R.id.PHONE_DESCRIPTION_EDIT:
                if (lphone.length() > 0 && isPhoneNoValid(lphone)) {
                    colorView(m_cPhoneStatImg, m_cPhoneStatLine, true);
                } else {
                    colorView(m_cPhoneStatImg, m_cPhoneStatLine, false);
                }
                break;
            case R.id.EMAIL_DESCRIPTION_EDIT:
                if (lemail.length() > 0 && isEmailValid(lemail)) {
                    colorView(m_cEmailStatImg, m_cEmailStatLine, true);
                } else {
                    colorView(m_cEmailStatImg, m_cEmailStatLine, false);
                }
                break;
            case R.id.LOANAMT_DESCRIPTION_EDIT:
                if (lloanamt.length() > 0 && Float.parseFloat(lloanamt) > 0) {
                    colorView(m_cLoanamtStatImg, m_cLoanamtStatLine, true);
                } else {
                    colorView(m_cLoanamtStatImg, m_cLoanamtStatLine, false);
                }
                break;
            case R.id.NETSALARY_DESCRIPTION_EDIT:
                if (lnetsal.length() > 0 && Float.parseFloat(lnetsal) > 0) {
                    colorView(m_cNetsalStatImg, m_cNetsalStatLine, true);
                } else {
                    colorView(m_cNetsalStatImg, m_cNetsalStatLine, false);
                }
                break;
            case R.id.LOANEMI_DESCRIPTION_EDIT:
                if (lloanemi.length() > 0 && Float.parseFloat(lloanemi) > 0) {
                    colorView(m_cLoanemiStatImg, m_cLoanemiStatLine, true);
                } else {
                    colorView(m_cLoanemiStatImg, m_cLoanemiStatLine, false);
                }
                break;
            case R.id.COMPANYNAME_DESCRIPTION_EDIT:
                if (lcompname.length() > 0 && isAlphaNumeric(lcompname)) {
                    colorView(m_cComapnynameStatImg, m_cComapnynameStatLine, true);
                } else {
                    colorView(m_cComapnynameStatImg, m_cComapnynameStatLine, false);
                }
                break;

        }
    }

    public boolean validateAdd() {
        boolean lRetVal = false;
        try {
            String lleaddesc = m_cLeadDescEdit.getText().toString().trim();
            String lphone = m_cPhoneDescEdit.getText().toString().trim();
            String lemail = m_cEmailDescEdit.getText().toString().trim();
            String lloanamt = m_cLoanamtDescEdit.getText().toString().trim();
            String lnetsal = m_cNetsalDescEdit.getText().toString().trim();
            String lloanemi = m_cLoanemiDescEdit.getText().toString().trim();
            String lcompname = m_cCompnameDescEdit.getText().toString().trim();
            if (lleaddesc.length() > 0 && isAlphabetic(lleaddesc)) {
                colorView(m_cLeadStatImg, m_cLeadStatLine, true);
                if (lphone.length() > 0 && isPhoneNoValid(lphone)) {
                    colorView(m_cPhoneStatImg, m_cPhoneStatLine, true);
                    if (lemail.length() > 0 && isEmailValid(lemail)) {
                        colorView(m_cEmailStatImg, m_cEmailStatLine, true);
                        if (m_cCitiesPos > 0) {
                            colorView(m_cCityStatImg, m_cCityStatLine, true);
                            if (m_cTypesPos > 0) {
                                colorView(m_cLoantypeStatImg, m_cLoantypeStatLine, true);
                                if (m_cTypesPos > 0 && m_cTypesList.get(m_cTypesPos).equalsIgnoreCase(Constants.HOME_LOAN)) {
                                    if (!validateProp()) {
                                        return false;
                                    }
                                }
                                if (lloanamt.length() > 0 && Float.parseFloat(lloanamt) > 0) {
                                    colorView(m_cLoanamtStatImg, m_cLoanamtStatLine, true);
                                    if (lnetsal.length() > 0 && Float.parseFloat(lnetsal) > 0) {
                                        colorView(m_cNetsalStatImg, m_cNetsalStatLine, true);
                                        if (lloanemi.length() > 0 && Float.parseFloat(lloanemi) > 0) {
                                            colorView(m_cLoanemiStatImg, m_cLoanemiStatLine, true);
                                            if (lcompname.length() > 0 && isAlphaNumeric(lcompname)) {
                                                colorView(m_cComapnynameStatImg, m_cComapnynameStatLine, true);
                                                if (m_cSourcesPos > 0) {
                                                    colorView(m_cLeadsrcStatImg, m_cLeadsrcStatLine, true);
                                                    lRetVal = true;
                                                    if (m_cSourcesPos > 0 && m_cSourcesList.get(m_cSourcesPos).equalsIgnoreCase(Constants.REFERRAL)) {
                                                        lRetVal = validateReff();
                                                    }
                                                } else {
                                                    colorView(m_cLeadsrcStatImg, m_cLeadsrcStatLine, false);
                                                }
                                            } else {
                                                colorView(m_cComapnynameStatImg, m_cComapnynameStatLine, false);
                                            }
                                        } else {
                                            colorView(m_cLoanemiStatImg, m_cLoanemiStatLine, false);
                                        }
                                    } else {
                                        colorView(m_cNetsalStatImg, m_cNetsalStatLine, false);
                                    }
                                } else {
                                    colorView(m_cLoanamtStatImg, m_cLoanamtStatLine, false);
                                }
                            } else {
                                colorView(m_cLoantypeStatImg, m_cLoantypeStatLine, false);
                            }
                        } else {
                            colorView(m_cCityStatImg, m_cCityStatLine, false);
                        }
                    } else {
                        colorView(m_cEmailStatImg, m_cEmailStatLine, false);
                    }
                } else {
                    colorView(m_cPhoneStatImg, m_cPhoneStatLine, false);
                }
            } else {
                colorView(m_cLeadStatImg, m_cLeadStatLine, false);
            }
        }catch (Exception e){
            e.printStackTrace();
            lRetVal = false;
        }
        return lRetVal;
    }

    public boolean validateReff() {
        boolean lRetVal = false;
        if (m_cReffPos > 0) {
            colorView(m_cReffStatImg, m_cReffStatLine, true);
            lRetVal = true;
        } else {
            colorView(m_cReffStatImg, m_cReffStatLine, false);
        }
        return lRetVal;
    }

    public boolean validateProp() {
        boolean lRetVal = false;
        if (m_cTypesPos > 0) {
            colorView(m_cisPropStatImg, m_cisPropStatLine, true);
            if(m_cVerPos == 1){
                if(!validateVer()){
                    return false;
                }
            }
            lRetVal = true;
        } else {
            colorView(m_cisPropStatImg, m_cisPropStatLine, false);
        }
        return lRetVal;
    }

    private boolean validateVer() {
        boolean lRetVal = false;
        if (m_cBuilderPos > 0) {
            colorView(m_cBuilderStatImg, m_cBuilderStatLine, true);
            if (m_cProjectPos > 0) {
                colorView(m_cProjectStatImg, m_cProjectStatLine, true);
                lRetVal = true;
            } else {
                colorView(m_cProjectStatImg, m_cProjectStatLine, false);
            }
        } else {
            colorView(m_cBuilderStatImg, m_cBuilderStatLine, false);
        }
        return lRetVal;
    }


    /*assigned_to_uuid
    source_uuid
    name
    phone
    email
    city_uuid
    type_uuid
    loan_amount
    net_salary
    existing_loan_emi
    company_name*/

    @OnClick({R.id.SUBMIT_BTN_TXT, R.id.CANCEL_BTN_TXT})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.SUBMIT_BTN_TXT:
                if (validateAdd()) {
                    displayProgressBar(-1, "Loading...");
                    HashMap<String, String> lParams = new HashMap<>();
//                    lParams.put(Constants.ASSIGNED_TO_UUID, );
                    lParams.put(Constants.SOURCE_UUID, m_cSourcesDic.get(m_cSourcesList.get(m_cSourcesPos)));
                    lParams.put(Constants.NAME, m_cLeadDescEdit.getText().toString());
                    lParams.put(Constants.PHONE, m_cPhoneDescEdit.getText().toString());
                    lParams.put(Constants.EMAIL, m_cEmailDescEdit.getText().toString());
                    lParams.put(Constants.CITYID, m_cCitiesDic.get(m_cCitiesList.get(m_cCitiesPos)));
                    lParams.put(Constants.TYPE_UUID, m_cTypesDic.get(m_cTypesList.get(m_cTypesPos)));
                    if (m_cTypesPos > 0 && m_cTypesList.get(m_cTypesPos).equalsIgnoreCase(Constants.HOME_LOAN)) {
                        if(m_cVerPos == 1) {
                            lParams.put(Constants.PROJECTID, m_cProjectDic.get(m_cProjectList.get(m_cProjectPos)));
                        }
                    }
                    lParams.put(Constants.LOAN_AMOUNT, m_cLoanamtDescEdit.getText().toString());
                    lParams.put(Constants.NET_SALARY, m_cNetsalDescEdit.getText().toString());
                    lParams.put(Constants.EXISTING_LOAN_EMI, m_cLoanemiDescEdit.getText().toString());
                    lParams.put(Constants.COMPANY_NAME, m_cCompnameDescEdit.getText().toString());
                    if (m_cSourcesPos > 0 && m_cSourcesList.get(m_cSourcesPos).equalsIgnoreCase(Constants.REFERRAL)) {
                        lParams.put(Constants.REFERRAL_UUID, m_cReffDic.get(m_cReffList.get(m_cReffPos)));
                    }
                    RequestManager.getInstance(this).placeRequest(Constants.LEAD, Response.class, this, lParams, true);
                }
                break;
            case R.id.CANCEL_BTN_TXT:
                onBackPressed();
                break;
        }
    }

    @Override
    protected void handleUIMessage(Message pObjMessage) {

    }

    @Override
        public void onAPIResponse(Object response, String apiMethod) {
        super.onAPIResponse(response, apiMethod);
        switch (apiMethod){
            case Constants.CITIES:
                CityAll lCityAll = (CityAll) response;
                m_cCitiesList = new ArrayList<>();
                m_cCitiesDic = new HashMap<>();
                if (lCityAll.getData().size() > 0) {
                    m_cCitiesList.add("Select City");
                }
                for (CityData lCityData : lCityAll.getData()) {
                    m_cCitiesList.add(lCityData.getName());
                    m_cCitiesDic.put(lCityData.getName(), lCityData.getUuid());
                }
                m_cSpinAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item_bold, m_cCitiesList);
                m_cSpinCityEdit.setAdapter(m_cSpinAdapter);
                /*if (null != m_cObjectProj) {
                    int lPos = 0;
                    try {
                        lPos = m_cCitiesList.indexOf(m_cObjectProj.getData().getAddress().getData().getCity().getData().getName().toString());
                        m_cSpinCityEdit.setSelection(lPos);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }*/
                hideDialog();
                break;
            case Constants.LEADS_SOURCE:
                Sources lSources = (Sources) response;
                m_cSourcesList = new ArrayList<>();
                m_cSourcesDic = new HashMap<>();
                if (lSources.getData().size() > 0) {
                    m_cSourcesList.add("Select Lead Source");
                }
                for (LeadsSrcDatum lLeadsSrcDatum : lSources.getData()) {
                    m_cSourcesList.add(lLeadsSrcDatum.getName());
                    m_cSourcesDic.put(lLeadsSrcDatum.getName(), lLeadsSrcDatum.getUuid());
                }
                m_cSpinAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item_bold, m_cSourcesList);
                m_cSpinLeadsrcEdit.setAdapter(m_cSpinAdapter);
                /*if (null != m_cObjectProj) {
                    int lPos = 0;
                    try {
                        lPos = m_cCitiesList.indexOf(m_cObjectProj.getData().getAddress().getData().getCity().getData().getName().toString());
                        m_cSpinCityEdit.setSelection(lPos);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }*/
                hideDialog();
                break;
            case Constants.LOANS_TYPE:
                Types lTypes = (Types) response;
                m_cTypesList = new ArrayList<>();
                m_cTypesDic = new HashMap<>();
                if (lTypes.getData().size() > 0) {
                    m_cTypesList.add("Select Loan Type");
                }
                for (LeadsSrcDatum lLeadsSrcDatum : lTypes.getData()) {
                    m_cTypesList.add(lLeadsSrcDatum.getName());
                    m_cTypesDic.put(lLeadsSrcDatum.getName(), lLeadsSrcDatum.getUuid());
                }
                m_cSpinAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item_bold, m_cTypesList);
                m_cSpinLoantypeEdit.setAdapter(m_cSpinAdapter);
                /*if (null != m_cObjectProj) {
                    int lPos = 0;
                    try {
                        lPos = m_cCitiesList.indexOf(m_cObjectProj.getData().getAddress().getData().getCity().getData().getName().toString());
                        m_cSpinCityEdit.setSelection(lPos);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }*/
                hideDialog();
                break;
            case Constants.TEAMS_REFERRALS:
                Types llTypes = (Types) response;
                m_cReffList = new ArrayList<>();
                m_cReffDic = new HashMap<>();
                if (llTypes.getData().get(0).getReferrals().getData().size() > 0) {
                    m_cReffList.add("Select Referral");
                }
                for (AgentData lAgentData : llTypes.getData().get(0).getReferrals().getData()) {
                    m_cReffList.add(lAgentData.getFirstName()+" "+lAgentData.getLastName());
                    m_cReffDic.put(lAgentData.getFirstName() + " " + lAgentData.getLastName(), lAgentData.getUuid());
                }
                m_cSpinAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item_bold, m_cReffList);
                m_cSpinReffEdit.setAdapter(m_cSpinAdapter);
                /*if (null != m_cObjectProj) {
                    int lPos = 0;
                    try {
                        lPos = m_cCitiesList.indexOf(m_cObjectProj.getData().getAddress().getData().getCity().getData().getName().toString());
                        m_cSpinCityEdit.setSelection(lPos);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }*/
                hideDialog();
                break;
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
                m_cSpinBuilderEdit.setAdapter(m_cSpinAdapter);
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
                m_cSpinProjectEdit.setAdapter(m_cSpinAdapter);
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
            case Constants.LEAD:
                Response lResponse = (Response) response;
                onBackPressed();
                break;

        }
    }

    @Override
    public void onErrorResponse(VolleyError error, String apiMethod) {
        super.onErrorResponse(error, apiMethod);
        switch (apiMethod){
            case Constants.LEAD:
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
            case R.id.CITY_SPINNER:
                m_cCitiesPos = position;
                if (position > 0) {
                    colorView(m_cCityStatImg, m_cCityStatLine, true);
                }
                break;
            case R.id.LOANTYPE_SPINNER:
                m_cTypesPos = position;
                if (position > 0) {
                    colorView(m_cLoantypeStatImg, m_cLoantypeStatLine, true);
                    if (m_cTypesPos > 0 && m_cTypesList.get(m_cTypesPos).equalsIgnoreCase(Constants.HOME_LOAN)) {
                        m_cPropverfRelLay.setVisibility(View.VISIBLE);
                    }else {
                        m_cPropverfRelLay.setVisibility(View.GONE);
                        m_cBuilderRelLay.setVisibility(View.GONE);
                        m_cProjectRelLay.setVisibility(View.GONE);
                    }
                }
                else {
                    m_cPropverfRelLay.setVisibility(View.GONE);
                    m_cBuilderRelLay.setVisibility(View.GONE);
                    m_cProjectRelLay.setVisibility(View.GONE);
                }
                break;
            case R.id.LEADSOURCE_SPINNER:
                m_cSourcesPos = position;
                if (position > 0) {
                    m_cReffRelLay.setVisibility(View.VISIBLE);
                    colorView(m_cLeadsrcStatImg, m_cLeadsrcStatLine, true);
                    HashMap<String, String> lParams = new HashMap<>();
                    if (m_cSourcesPos > 0 && m_cSourcesList.get(m_cSourcesPos).equalsIgnoreCase(Constants.REFERRAL)) {
                        lParams.put(Constants.INSCLUDE, Constants.REFERRALS);
                        RequestManager.getInstance(this).placeRequest(Constants.TEAMS_REFERRALS, Types.class, this, lParams, false);
                    }else {
                        m_cReffRelLay.setVisibility(View.GONE);
                    }
                }else {
                    m_cReffRelLay.setVisibility(View.GONE);
                }
                break;
            case R.id.REFERRAL_SPINNER:
                m_cReffPos = position;
                if (position > 0) {
                    colorView(m_cReffStatImg, m_cReffStatLine, true);
                }
                break;
            case R.id.BUILDER_SPINNER:
                m_cBuilderPos = position;
                if (position > 0) {
                    colorView(m_cBuilderStatImg, m_cBuilderStatLine, true);
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
                if (position > 0) {
                    colorView(m_cProjectStatImg, m_cProjectStatLine, true);
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
