package com.whatsloan.dsa.uifragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.android.volley.VolleyError;
import com.squareup.picasso.Picasso;
import com.whatsloan.dsa.R;
import com.whatsloan.dsa.customviews.UserCircularImageView;
import com.whatsloan.dsa.macros.DSAMacros;
import com.whatsloan.dsa.model.AttachmentsData;
import com.whatsloan.dsa.model.CityAll;
import com.whatsloan.dsa.model.CityData;
import com.whatsloan.dsa.model.CustomersData;
import com.whatsloan.dsa.model.ProjectStruct;
import com.whatsloan.dsa.model.Response;
import com.whatsloan.dsa.network.Constants;
import com.whatsloan.dsa.uiframework.DSAFragmentBaseClass;
import com.whatsloan.dsa.utils.DecimalDigitsInputFilter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by S.K. Pissay on 3/4/16.
 */
public class CustomerProfile extends DSAFragmentBaseClass implements AdapterView.OnItemSelectedListener {

    @Nullable
    @Bind({ R.id.EDIT_DOB_LAY, R.id.RIGHT_CONTACT_TIME_LAY})
    List<RelativeLayout> nameViews;

    @Nullable
    @Bind(R.id.PRO_PIC)
    UserCircularImageView m_cCustImg;

    @Nullable
    @Bind(R.id.CUST_NAME)
    TextView m_cCustName;

    @Nullable
    @Bind(R.id.LOCATION)
    TextView m_cLocation;

    @Nullable
    @Bind(R.id.EDIT_PHONE)
    EditText m_cPhone;

    @Nullable
    @Bind(R.id.EDIT_EMAIL)
    EditText m_cEmail;

    @Nullable
    @Bind(R.id.EDIT_DOB_LAY)
    RelativeLayout m_cDOBLay;

    @Nullable
    @Bind(R.id.EDIT_DOB)
    TextView m_cDOB;

    @Nullable
    @Bind(R.id.EDIT_AGE)
    EditText m_cAge;

    @Nullable
    @Bind(R.id.EDIT_EDU)
    EditText m_cEducation;

    @Nullable
    @Bind(R.id.EDIT_COMPANY)
    EditText m_cCompany;

    @Nullable
    @Bind(R.id.EDIT_SALARY)
    EditText m_cSalary;

    @Nullable
    @Bind(R.id.EDIT_PAN)
    EditText m_cPAN;

    @Nullable
    @Bind(R.id.EDIT_ACC)
    EditText m_cACCNo;

    @Nullable
    @Bind(R.id.EDIT_SKYPE)
    EditText m_cSkypeId;

    @Nullable
    @Bind(R.id.EDIT_FACET)
    EditText m_cFaceTime;

    @Nullable
    @Bind(R.id.RIGHT_CONTACT_TIME_LAY)
    RelativeLayout m_cConstactTimePicker;

    @Nullable
    @Bind(R.id.RIGHT_CONTACT_TIME_TXT)
    TextView m_cConstactTimeTxt;

    @Nullable
    @Bind(R.id.EDIT_CIBIL)
    EditText m_cCIBIL;

    @Nullable
    @Bind(R.id.EDIT_SELECT_CITY_SPIN)
    Spinner m_cCitySpin;

    @Nullable
    @Bind(R.id.EDIT_SELECT_RECIDENCY_SPIN)
    Spinner m_cResidencySpin;

    @Nullable
    @Bind(R.id.EDIT_SELECT_PROFESSION_SPIN)
    Spinner m_cProfessionSpin;

    @Nullable
    @Bind(R.id.EDIT_MARITAL_SPIN)
    Spinner m_cMaritalSpin;

    @Nullable
    @Bind(R.id.EDIT_CUSTOMER_IMG)
    ImageView m_cEditCustomerImg;

    @Nullable
    @Bind(R.id.SUBMIT_BTN_TXT)
    TextView submitbtntxt;

    @Nullable
    @Bind(R.id.CANCEL_BTN_TXT)
    TextView cancelbtntxt;

    @Nullable
    @Bind(R.id.PROFILE_EDIT_REL_LAY)
    RelativeLayout profileeditrellay;

    @Nullable
    @Bind(R.id.ADD_OR_EDIT_LAY)
    RelativeLayout addOrEditLay;

    @Nullable
    @Bind(R.id.EDIT_SELECT_CIBIL_SPIN)
    Spinner m_cCIBILSpin;

    @Nullable
    @Bind(R.id.SCROLL)
    ScrollView m_cScrollView;

    String m_cJsonObject;
    int m_cPos;
    private ScrollView m_cScroll;
    CustomersData m_cObjCustomer;

    private int m_cCitiesPos = -1;
    private int m_cRecyPos = -1;
    private int m_cProffPos = -1;
    private int m_cMaritalPos = -1;
    private int m_cCIBILPos = -1;

    ArrayList<ProjectStruct> m_cProjectList;
    ArrayList<String> m_cRecyList;
    HashMap<String, String> m_cRecyDic;
    ArrayList<String> m_cCitiesList;
    HashMap<String, String> m_cCitiesDic;
    ArrayList<String> m_cProffList;
    HashMap<String, String> m_cProffDic;
    ArrayList<String> m_cMaritalList;
    HashMap<String, String> m_cMaritalDic;
    ArrayList<String> m_cCIBILList;
    HashMap<String, String> m_cCIBILDic;

    ArrayAdapter<String> m_cSpinAdapter;

    private Calendar m_cCalendar;
    private DatePickerDialog m_cDatePickerDialog;
    private TimePickerDialog m_cTimePickerDialog;
    public static final int DATE_PICKER_ID = 101;
    public static final int TIME_DIALOG_ID = 111;

    public CustomerProfile() {
        super();
    }

    public static CustomerProfile newInstance(int pPosition, String pJsonObject, CustomersData pObjCustomer) {
        CustomerProfile lCustomerProfile = new CustomerProfile();

        Bundle args = new Bundle();
        args.putInt("Position", pPosition);
        args.putString("JsonObject", pJsonObject);
        args.putParcelable("CustomerObject", pObjCustomer);
        lCustomerProfile.setArguments(args);

        return lCustomerProfile;
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
        m_cObjMainView = inflater.inflate(R.layout.customer_profile_edit, container, false);
        ButterKnife.bind(this, m_cObjMainView);

        init();


        return m_cObjMainView;
    }

    private void init() {

        setAccessibility(false);

        m_cObjMainActivity.m_cObjFragmentBase = CustomerProfile.this;
        m_cPos = getArguments().getInt("Position", 0);
        m_cJsonObject = getArguments().getString("JsonObject");
        m_cObjCustomer = getArguments().getParcelable("CustomerObject");

        m_cSalary.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});

        m_cCitySpin.setOnItemSelectedListener(CustomerProfile.this);
        m_cResidencySpin.setOnItemSelectedListener(CustomerProfile.this);
        m_cProfessionSpin.setOnItemSelectedListener(CustomerProfile.this);
        m_cMaritalSpin.setOnItemSelectedListener(CustomerProfile.this);
//        m_cCIBILSpin.setOnItemSelectedListener(CustomerProfile.this);

        m_cRecyList = new ArrayList<>();
        m_cRecyList.add("Select Residential Status");
        m_cRecyList.add("Indian");
        m_cRecyList.add("NRI");
        m_cRecyList.add("PIO/OCI");
        m_cSpinAdapter = new ArrayAdapter<String>(m_cObjMainActivity, R.layout.spinner_item_plain_bold, m_cRecyList);
        m_cResidencySpin.setAdapter(m_cSpinAdapter);

        m_cProffList = new ArrayList<>();
        m_cProffList.add("Select Profession");
        m_cProffList.add("Salaried");
        m_cProffList.add("Doctor");
        m_cProffList.add("Self Employed-Professionals");
        m_cProffList.add("Self Employed-Others");
        m_cSpinAdapter = new ArrayAdapter<String>(m_cObjMainActivity, R.layout.spinner_item_plain_bold, m_cProffList);
        m_cProfessionSpin.setAdapter(m_cSpinAdapter);

        m_cMaritalList = new ArrayList<>();
        m_cMaritalList.add("Select Marital Status");
        m_cMaritalList.add("Married");
        m_cMaritalList.add("Unmarried");
        m_cSpinAdapter = new ArrayAdapter<String>(m_cObjMainActivity, R.layout.spinner_item_plain_bold, m_cMaritalList);
        m_cMaritalSpin.setAdapter(m_cSpinAdapter);

        m_cCIBILList = new ArrayList<>();
        m_cCIBILList.add("Select CIBIL Status");
        m_cCIBILList.add("Settled A/C");
        m_cCIBILList.add("Written off A/C");
        m_cCIBILList.add("Overdue A/C");
        m_cCIBILList.add("Good A/C");
        m_cSpinAdapter = new ArrayAdapter<String>(m_cObjMainActivity, R.layout.spinner_item_plain_bold, m_cCIBILList);
        m_cCIBILSpin.setAdapter(m_cSpinAdapter);

        //Calling Cities api
        m_cObjMainActivity.displayProgressBar(-1, "Loading");
        HashMap<String, String> lParams = new HashMap<>();
        lParams.put(Constants.PAGINATE, Constants.ALL);
        placeRequest(Constants.CITIES, CityAll.class, lParams, false);

        if (null != m_cObjCustomer) {
            try {
                for (AttachmentsData lAtachData : m_cObjCustomer.getAttachments().getData()) {
                    if (lAtachData.getType().equalsIgnoreCase(Constants.PROFILE_PICTURE)) {
                        Picasso.with(m_cObjMainActivity)
                                .load(lAtachData.getUri())
                                .error(R.drawable.profile_placeholder)
                                .placeholder(R.drawable.profile_placeholder)
                                .fit()
                                .into(m_cCustImg);
                    }
                }
            } catch (Exception e) {
                m_cCustImg.setImageResource(R.drawable.profile_placeholder);
                e.printStackTrace();
            }
            m_cCustName.setText(m_cObjCustomer.getFirstName() + " " + m_cObjCustomer.getLastName());
            if (null != m_cObjCustomer.getAddresses())
                m_cLocation.setText(m_cObjCustomer.getAddresses().getData().getCity().getData().getName());
            m_cPhone.setText(m_cObjCustomer.getPhone());
            m_cEmail.setText(m_cObjCustomer.getEmail());
            if (null != m_cObjCustomer.getSettings()) {
                //show dob in dd-MM-YYYY
                m_cDOB.setText(DSAMacros.getDateFormat(null, m_cObjCustomer.getSettings().getData().getDob(), DSAMacros.DEFAULT_DATEFORMAT_YYYYMMDD,
                        DSAMacros.DEFAULT_DATEFORMAT_DDMMYYYY));
                m_cAge.setText(m_cObjCustomer.getSettings().getData().getAge());
                m_cEducation.setText(m_cObjCustomer.getSettings().getData().getEducation());
                m_cCompany.setText(m_cObjCustomer.getSettings().getData().getCompany());
                m_cSalary.setText(m_cObjCustomer.getSettings().getData().getNetIncome().replaceAll("₹", "").trim());
                m_cPAN.setText(m_cObjCustomer.getSettings().getData().getPan());
                m_cACCNo.setText(m_cObjCustomer.getSettings().getData().getSalaryBank());
                m_cSkypeId.setText(m_cObjCustomer.getSettings().getData().getSkype());
                m_cFaceTime.setText(m_cObjCustomer.getSettings().getData().getFacetime());
                m_cCIBIL.setText(m_cObjCustomer.getSettings().getData().getCibilScore());
                m_cConstactTimeTxt.setText(m_cObjCustomer.getSettings().getData().getContactTime());
                int lPos = 0;
                try {
                    lPos = m_cRecyList.indexOf(m_cObjCustomer.getSettings().getData().getResidentStatus());
                    m_cResidencySpin.setSelection(lPos);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                int llPos = 0;
                try {
                    llPos = m_cProffList.indexOf(m_cObjCustomer.getSettings().getData().getProfession());
                    m_cProfessionSpin.setSelection(llPos);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                int lllPos = 0;
                try {
                    lllPos = m_cMaritalList.indexOf(m_cObjCustomer.getSettings().getData().getMaritalStatus());
                    m_cMaritalSpin.setSelection(lllPos);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                int llllPos = 0;
                try {
                    llllPos = m_cCIBILList.indexOf(m_cObjCustomer.getSettings().getData().getCibilStatus());
                    m_cCIBILSpin.setSelection(llllPos);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

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
    }

    public boolean validateAdd() {
        boolean lRetVal = false;
        String lPhone = m_cPhone.getText().toString().trim();
        String lEmail = m_cEmail.getText().toString().trim();
        String lDOB = m_cDOB.getText().toString().trim();
        String lAge = m_cAge.getText().toString().trim();
        String lEducation = m_cEducation.getText().toString().trim();
        String lCompany = m_cCompany.getText().toString().trim();
        String lSalary = m_cSalary.getText().toString().replaceAll("₹", "").trim();
        String lPAN = m_cPAN.getText().toString().trim();
        String lACCNo = m_cACCNo.getText().toString().trim();
        String lSkypeId = m_cSkypeId.getText().toString().trim();
        String lFaceTime = m_cFaceTime.getText().toString().trim();
//        String lCIBIL = m_cCIBIL.getText().toString().trim();

        if (lPhone.length() > 0 && m_cObjMainActivity.isPhoneNoValid(lPhone)) {
            if (DSAMacros.isEmailValid(lEmail)) {
                if (m_cCitiesPos > 0) {
                    if (m_cRecyPos > 0) {
                        if (m_cProffPos > 0) {
                            if (lDOB.length() > 0) {
                                if (lAge.length() > 0) {
                                    if (lEducation.length() > 0 && m_cObjMainActivity.isAlphaNumeric(lEducation)) {
                                        if (m_cMaritalPos > 0) {
                                            if (lCompany.length() > 0 && m_cObjMainActivity.isAlphaNumeric(lCompany)) {
                                                if (lSalary.length() > 0 && Float.parseFloat(lSalary) > 0) {
                                                    if (lACCNo.length() > 0 && m_cObjMainActivity.isAlphabetic(lACCNo)) {
                                                        lRetVal = true;
                                                    } else {
                                                        m_cObjMainActivity.displaySnack(m_cObjMainView, "Enter Salary Bank Account Name");
                                                        m_cScrollView.fullScroll(ScrollView.FOCUS_DOWN);
                                                    }
                                                } else {
                                                    m_cObjMainActivity.displaySnack(m_cObjMainView, "Enter Salary");
                                                    m_cScrollView.fullScroll(ScrollView.FOCUS_DOWN);
                                                }
                                            } else {
                                                m_cObjMainActivity.displaySnack(m_cObjMainView, "Enter Company Name");
                                                m_cScrollView.fullScroll(ScrollView.FOCUS_DOWN);
                                            }
                                        } else {
                                            m_cObjMainActivity.displaySnack(m_cObjMainView, "Select Marital Status");
                                            m_cScrollView.fullScroll(ScrollView.FOCUS_DOWN);
                                        }
                                    } else {
                                        m_cObjMainActivity.displaySnack(m_cObjMainView, "Enter Education");
                                        m_cScrollView.fullScroll(ScrollView.FOCUS_UP);
                                    }
                                } else {
                                    m_cObjMainActivity.displaySnack(m_cObjMainView, "Enter Age");
                                    m_cScrollView.fullScroll(ScrollView.FOCUS_UP);
                                }
                            } else {
                                m_cObjMainActivity.displaySnack(m_cObjMainView, "Enter Date of Birth");
                                m_cScrollView.fullScroll(ScrollView.FOCUS_UP);
                            }
                        } else {
                            m_cObjMainActivity.displaySnack(m_cObjMainView, "Select Profession");
                            m_cScrollView.fullScroll(ScrollView.FOCUS_UP);
                        }
                    } else {
                        m_cObjMainActivity.displaySnack(m_cObjMainView, "Select Residency");
                        m_cScrollView.fullScroll(ScrollView.FOCUS_UP);
                    }
                } else {
                    m_cObjMainActivity.displaySnack(m_cObjMainView, "Select City");
                    m_cScrollView.fullScroll(ScrollView.FOCUS_UP);
                }
            } else {
                m_cObjMainActivity.displaySnack(m_cObjMainView, "Enter Valid Email");
                m_cScrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        } else {
            m_cObjMainActivity.displaySnack(m_cObjMainView, "Enter Valid Phone");
            m_cScrollView.fullScroll(ScrollView.FOCUS_UP);
        }
        return lRetVal;
    }

    private void setAccessibility(boolean pState) {
        m_cPhone.setEnabled(pState);
        m_cEmail.setEnabled(pState);
//        m_cDOB.setEnabled(pState);
        m_cAge.setEnabled(pState);
        m_cEducation.setEnabled(pState);
        m_cMaritalSpin.setEnabled(pState);
        m_cCompany.setEnabled(pState);
        m_cSalary.setEnabled(pState);
        m_cPAN.setEnabled(pState);
        m_cACCNo.setEnabled(pState);
        m_cSkypeId.setEnabled(pState);
        m_cFaceTime.setEnabled(pState);
        m_cCitySpin.setEnabled(pState);
        m_cResidencySpin.setEnabled(pState);
        m_cProfessionSpin.setEnabled(pState);
        m_cCIBIL.setEnabled(false);
        m_cCIBILSpin.setEnabled(false);
        ButterKnife.apply(nameViews, ENABLED, pState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void handleUIMessage(Message pObjMessage) {

    }

    @OnClick({R.id.EDIT_CUSTOMER_IMG, R.id.SUBMIT_BTN_TXT, R.id.CANCEL_BTN_TXT, R.id.RIGHT_CONTACT_TIME_LAY, R.id.EDIT_DOB_LAY})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.EDIT_CUSTOMER_IMG:
                setAccessibility(true);
                addOrEditLay.setVisibility(View.VISIBLE);
                break;
            case R.id.SUBMIT_BTN_TXT:
                if (validateAdd()) {
                    m_cObjMainActivity.displayProgressBar(-1, "Loading...");
                    HashMap<String, String> lParams = new HashMap<>();
                    try {
                        if (null != m_cPhone.getText().toString())
                            lParams.put(Constants.PHONE, m_cPhone.getText().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (null != m_cEmail.getText().toString())
                            lParams.put(Constants.EMAIL, m_cEmail.getText().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (m_cCitiesPos > 0)
                            lParams.put(Constants.CITY_UUID, m_cCitiesDic.get(m_cCitiesList.get(m_cCitiesPos)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (m_cRecyPos > 0)
                            lParams.put(Constants.RESIDENT_STATUS, m_cRecyList.get(m_cRecyPos));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (m_cProffPos > 0)
                            lParams.put(Constants.PROFESSION, m_cProffList.get(m_cProffPos));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (null != m_cDOB.getText().toString())
                            lParams.put(Constants.DOB, DSAMacros.getDateFormat(null, m_cDOB.getText().toString(), DSAMacros.DEFAULT_DATEFORMAT_DDMMYYYY,
                                    DSAMacros.DEFAULT_DATEFORMAT_YYYYMMDD));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (null != m_cAge.getText().toString())
                            lParams.put(Constants.AGE, m_cAge.getText().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (null != m_cEducation.getText().toString())
                            lParams.put(Constants.EDUCATION, m_cEducation.getText().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (m_cMaritalPos > 0)
                            lParams.put(Constants.MARITAL_STATUS, m_cMaritalList.get(m_cMaritalPos));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (null != m_cCompany.getText().toString())
                            lParams.put(Constants.COMPANY, m_cCompany.getText().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (null != m_cSalary.getText().toString())
                            lParams.put(Constants.NET_INCOME, m_cSalary.getText().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (null != m_cPAN.getText().toString())
                            lParams.put(Constants.PAN, m_cPAN.getText().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (null != m_cACCNo.getText().toString())
                            lParams.put(Constants.SALARY_BANK, m_cACCNo.getText().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (null != m_cSkypeId.getText().toString())
                            lParams.put(Constants.SKYPE, m_cSkypeId.getText().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (null != m_cFaceTime.getText().toString())
                            lParams.put(Constants.FACETIME, m_cFaceTime.getText().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (null != m_cConstactTimeTxt.getText().toString())
                            lParams.put(Constants.CONTACT_TIME, m_cConstactTimeTxt.getText().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    placePutRequest(Constants.apiMethodEx(Constants.CUSTOMERS, m_cObjCustomer.getUuid()), Response.class, lParams, true);
                }
                break;
            case R.id.CANCEL_BTN_TXT:
                setAccessibility(false);
                addOrEditLay.setVisibility(View.GONE);
                break;
            case R.id.EDIT_DOB_LAY:
                showDatePickerDialog(DATE_PICKER_ID);
                break;
            case R.id.RIGHT_CONTACT_TIME_LAY:
                showTimePickerDialog(TIME_DIALOG_ID);
                break;
        }
    }

    private void showDatePickerDialog(int pId) {
        m_cCalendar = Calendar.getInstance();
        switch (pId) {
            case DATE_PICKER_ID:
                m_cDatePickerDialog = new DatePickerDialog(m_cObjMainActivity, myFromDateListener, m_cCalendar.get(Calendar.YEAR),
                        m_cCalendar.get(Calendar.MONTH), m_cCalendar.get(Calendar.DAY_OF_MONTH));
                m_cDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                break;
        }
        m_cDatePickerDialog.show();
    }

    private void showTimePickerDialog(int pId) {
        m_cCalendar = Calendar.getInstance();
        switch (pId) {
            case TIME_DIALOG_ID:
                // set time picker as current time
                m_cTimePickerDialog = new TimePickerDialog(m_cObjMainActivity,
                        timePickerListener, m_cCalendar.get(Calendar.HOUR_OF_DAY), m_cCalendar.get(Calendar.MINUTE), false);
        }
        m_cTimePickerDialog.show();
    }

    private DatePickerDialog.OnDateSetListener myFromDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int year, int month, int day) {
            String lmonth = String.format("%02d", month + 1);
            String lday = String.format("%02d", day);
            m_cDOB.setText(lday + "-" + lmonth + "-" + year);
            m_cDatePickerDialog.dismiss();
        }
    };

    private TimePickerDialog.OnTimeSetListener timePickerListener =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int selectedHour,
                                      int selectedMinute) {
//                    hour = selectedHour;
//                    minute = selectedMinute;

                    // set current time into textview
                    m_cConstactTimeTxt.setText(new StringBuilder().append(m_cObjMainActivity.pad(selectedHour))
                            .append(":").append(m_cObjMainActivity.pad(selectedMinute)).append(":").append("00").toString());

                    // set current time into timepicker
//                    timePicker1.setCurrentHour(selectedHour);
//                    timePicker1.setCurrentMinute(selectedMinute);

                }
            };

    @Override
    public void onAPIResponse(Object response, String apiMethod) {
        super.onAPIResponse(response, apiMethod);
        switch (apiMethod) {
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
                m_cSpinAdapter = new ArrayAdapter<String>(m_cObjMainActivity, R.layout.spinner_item_plain_bold, m_cCitiesList);
                m_cCitySpin.setAdapter(m_cSpinAdapter);
                if (null != m_cObjCustomer) {
                    int lllPos = 0;
                    try {
                        lllPos = m_cCitiesList.indexOf(m_cObjCustomer.getAddresses().getData().getCity().getData().getName());
                        m_cCitySpin.setSelection(lllPos);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                m_cObjMainActivity.hideDialog();
                break;
            default:
                if (apiMethod.contains("/")) {
                    Response lResponse = (Response) response;
                    m_cObjMainActivity.displaySnack(profileeditrellay, "Successfully Edited");
                    m_cObjMainActivity.hideDialog();
                    setAccessibility(false);
                    addOrEditLay.setVisibility(View.GONE);
                }
                break;
        }
        m_cObjMainActivity.hideDialog();
    }

    @Override
    public void onErrorResponse(VolleyError error, String apiMethod) {
        super.onErrorResponse(error, apiMethod);
        m_cObjMainActivity.hideDialog();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.EDIT_SELECT_CITY_SPIN:
                m_cCitiesPos = position;
                /*if(position >= 0){
                    m_cObjMainActivity.displayProgressBar(-1, "Loading");
                    HashMap<String, String> lParams = new HashMap<>();
                    if(m_cCitiesPos > 0){
                        lParams.put(Constants.CITYID, m_cCitiesDic.get(m_cCitiesList.get(m_cCitiesPos)));
                    }else{
                        lParams.put(Constants.PAGINATE, Constants.ALL);
                    }
//                    placeRequest(Constants.BANKS, BanksAll.class, lParams, false); //todo uncomment for all banks
                    placeRequest(Constants.TEAMMEMBERS_BANKS, BanksAll.class, lParams, false);
//                    m_cSelectBankSpin.performClick();
                }*/
                break;
            case R.id.EDIT_SELECT_RECIDENCY_SPIN:
                m_cRecyPos = position;
                break;
            case R.id.EDIT_SELECT_PROFESSION_SPIN:
                m_cProffPos = position;
                break;
            case R.id.EDIT_MARITAL_SPIN:
                m_cMaritalPos = position;
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
}
