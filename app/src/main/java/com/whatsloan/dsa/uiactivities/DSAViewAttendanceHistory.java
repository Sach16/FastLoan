package com.whatsloan.dsa.uiactivities;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.whatsloan.dsa.R;
import com.whatsloan.dsa.customadapters.CustomRecyclerAdapterForAttendanceHistory;
import com.whatsloan.dsa.interfaces.RecyclerAttendanceListener;
import com.whatsloan.dsa.model.AttendanceData;
import com.whatsloan.dsa.model.AttendanceMember;
import com.whatsloan.dsa.model.Attendances;
import com.whatsloan.dsa.model.MembersData;
import com.whatsloan.dsa.model.TeamMembers;
import com.whatsloan.dsa.network.Constants;
import com.whatsloan.dsa.network.RequestManager;
import com.whatsloan.dsa.uiframework.DSABaseActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by S.K. Pissay on 23/3/16.
 */
public class DSAViewAttendanceHistory extends DSABaseActivity implements RecyclerAttendanceListener, AdapterView.OnItemSelectedListener, View.OnTouchListener {

    ArrayList<String> m_cMembersList;
    HashMap<String, String> m_cMembersDic;

    ArrayAdapter<String> m_cSpinAdapter;
    private View m_cView;
    private int m_cMemPos = -1;
    boolean userSelect = false;

    ArrayList<AttendanceData> m_cSpareList;
    ArrayList<AttendanceData> m_cAttendanceList;

    @Nullable @Bind(R.id.SELECT_MONTH)
    RelativeLayout m_cSelectMonthLay;

    @Nullable @Bind(R.id.MONTH_TXT)
    TextView m_cSelectMonthTxt;

    @Nullable @Bind(R.id.RECYC_ATTEN_HISTORY)
    RecyclerView m_cRecycAttendance;

    @Nullable @Bind(R.id.MEMBERS_LIST_SPIN)
    Spinner m_cMemberslistspin;


    private Calendar m_cCalendar;
    private int m_cMonth;
    private int m_cYear;
    private String m_cName;
    private int m_cPresent;
    private int m_cAbsent;
    private CustomRecyclerAdapterForAttendanceHistory m_cRecycAdapter;

    final String[] MONTH = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep",
            "Oct", "Nov", "Dec"};


    @Override
    protected void onCreate(Bundle pSavedInstance) {
        super.onCreate(pSavedInstance);
        setContentView(R.layout.activity_slider_layout);

        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.view_attendance_history);
        m_cView = stub.inflate();
        ButterKnife.bind(this);
        setTitle("Attendance History", false, true, true, false);

        m_cObjSliderMenu = (DrawerLayout) findViewById(R.id.drawer_layout);
        m_cObjSliderMenu.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        m_cContainFragment = (FrameLayout) findViewById(R.id.main_content_container);

        init();
    }

            /*"2015-05-24 07:34:26"
            "2015-12-28 23:23:31"
            "2016-02-22 06:19:30"
            "2015-06-12 12:00:56"
            "2016-01-29 08:14:27"
            "2016-01-08 13:40:20"*/

    private void init() {
        m_cMemberslistspin.setOnTouchListener(this);
        m_cMemberslistspin.setOnItemSelectedListener(this);
        m_cAttendanceList = new ArrayList<>();
        m_cSpareList = new ArrayList<>();
        m_cCalendar = Calendar.getInstance();
        m_cMonth = m_cCalendar.get(Calendar.MONTH) + 1;
        m_cYear = m_cCalendar.get(Calendar.YEAR);
        m_cSelectMonthTxt.setText(MONTH[m_cCalendar.get(Calendar.MONTH)] + ", " + m_cCalendar.get(Calendar.YEAR));
        m_cRecycAttendance.setLayoutManager(new LinearLayoutManager(this));

        m_cMembersList = new ArrayList<>();
        m_cMembersList.add("All");
        m_cSpinAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, m_cMembersList);
        m_cMemberslistspin.setAdapter(m_cSpinAdapter);

        displayProgressBar(-1, "Loading");
        HashMap<String, String> lParams = new HashMap<>();
        lParams.put(Constants.PAGINATE, Constants.ALL);
        lParams.put(Constants.INSCLUDE, Constants.MEMBERS);
        RequestManager.getInstance(this).placeRequest(Constants.USERTEAM, TeamMembers.class, this, lParams, false);

        HashMap<String, String> llParams = new HashMap<>();
        llParams.put(Constants.MONTH, ""+pad(m_cMonth));
        llParams.put(Constants.YEAR, ""+m_cYear);
        RequestManager.getInstance(this).placeRequest(Constants.ATTENDANCES, Attendances.class, this, llParams, false);
    }

    @Override
    protected void handleUIMessage(Message pObjMessage) {

    }

    @OnClick(R.id.SELECT_MONTH)
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.SELECT_MONTH:
                monthPicker();
                break;
        }
    }

    @Override
    public void onAPIResponse(Object response, String apiMethod) {
        super.onAPIResponse(response, apiMethod);
        switch (apiMethod){
            case Constants.ATTENDANCES:
                Attendances lObjAtten = (Attendances) response;
                if (null != lObjAtten && lObjAtten.getData().size() > 0) {
                    if (m_cAttendanceList.size() > 0) {
                        m_cAttendanceList.clear();
                        m_cSpareList.clear();
                        m_cRecycAdapter.notifyDataSetChanged();
                    }
                    for(AttendanceData lAttenData : lObjAtten.getData())
                    {
                        m_cAttendanceList.add(lAttenData);
                        m_cSpareList.add(lAttenData);
                    }
                    if (null != m_cAttendanceList && m_cAttendanceList.size() > 0) {
                        m_cRecycAdapter = new CustomRecyclerAdapterForAttendanceHistory(this, m_cAttendanceList, this);
                        m_cRecycAttendance.setAdapter(m_cRecycAdapter);
                    }
                } else {
                    if (m_cAttendanceList.size() > 0) {
                        m_cAttendanceList.clear();
                        m_cSpareList.clear();
                        m_cRecycAdapter.notifyDataSetChanged();
                    }
                }
                hideDialog();
                break;
            case Constants.USERTEAM:
                TeamMembers lTeamMembers = (TeamMembers) response;
                if (lTeamMembers.getData().getMembers().getData().size() > 0) {
                    m_cMembersList = new ArrayList<>();
                    m_cMembersDic = new HashMap<>();
                    m_cMembersList.add("All");
                    for (MembersData lMembersData : lTeamMembers.getData().getMembers().getData()) {
                        m_cMembersList.add(lMembersData.getFirstName() + " " + lMembersData.getLastName());
                        m_cMembersDic.put(lMembersData.getFirstName() + " " + lMembersData.getLastName(), lMembersData.getUuid());
                    }
                    m_cSpinAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, m_cMembersList);
                    m_cMemberslistspin.setAdapter(m_cSpinAdapter);
                }
                hideDialog();
                break;
            default:
                if(apiMethod.contains(Constants.ATTENDANCE_CALENDAR)){
                    AttendanceMember lObjAttenMember = (AttendanceMember) response;
                    Intent lIntent = new Intent(this, DSAViewAttendanceHistoryCalendar.class);
                    lIntent.putExtra("ATTENDANCE_MEMBER", lObjAttenMember);
                    lIntent.putExtra("ATTEN_PRESENT", m_cPresent);
                    lIntent.putExtra("ATTEN_ABSENT", m_cAbsent);
                    lIntent.putExtra("ATTEN_NAME", m_cName);
                    lIntent.putExtra("ATTEN_MONTH", m_cMonth);
                    lIntent.putExtra("ATTEN_YEAR", m_cYear);
                    startActivity(lIntent);
                    hideDialog();
                }
                break;
        }
    }

    @Override
    public void onErrorResponse(VolleyError error, String apiMethod) {
        super.onErrorResponse(error, apiMethod);
        hideDialog();
        if(error.networkResponse.statusCode == 401){
            logout();
            displaySnack(m_cView, "Session Expired");
        }
        displaySnack(m_cView, "Error Response");
    }

    @Override
    public void onInfoClick(int pPostion, Attendances pAttendances, AttendanceData pAttendanceData, View pView) {
        m_cName = pAttendanceData.getName();
        m_cPresent = pAttendanceData.getPresent();
        m_cAbsent = pAttendanceData.getLeave();
        if(m_cPresent > 0 || 0 < m_cAbsent) {
            displayProgressBar(-1, "Loading...");
            HashMap<String, String> lParams = new HashMap<>();
            lParams.put(Constants.MONTH, "" + pad(m_cMonth));
            lParams.put(Constants.YEAR, "" + m_cYear);
            RequestManager.getInstance(DSAViewAttendanceHistory.this)
                    .placeRequest(Constants.apiMethodEx(Constants.ATTENDANCE_CALENDAR, pAttendanceData.getUuid()), AttendanceMember.class, DSAViewAttendanceHistory.this, lParams, false);
        }else {
            displaySnack(m_cView, "Attendance not available");
        }
    }

    private void monthPicker() {

        final View dialogView = View.inflate(this, R.layout.month_picker_dialog, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.date_picker);

        datePicker.init(m_cCalendar.get(Calendar.YEAR), m_cCalendar.get(Calendar.MONTH), m_cCalendar.get(Calendar.DAY_OF_MONTH), null);
        datePicker.setMaxDate(System.currentTimeMillis() - 1000);
//        datePicker.setMaxDate(m_cCalendar.getTimeInMillis());

        /*ViewGroup ll = (ViewGroup) datePicker.getChildAt(0);
        ViewGroup ll2 = (ViewGroup) ll.getChildAt(0);
        int lId1 = ll2.getId();
        int lId2 = ll2.getChildAt(0).getId();
        int lId3 = ll2.getChildAt(1).getId();
        ll2.getChildAt(0).setVisibility(View.GONE);*/
        ((ViewGroup) datePicker.getChildAt(0)).findViewById(Resources.getSystem().getIdentifier("day", "id", "android")).setVisibility(View.GONE);

        dialogView.findViewById(R.id.DATE_TIME_SET).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.date_picker);

                m_cCalendar = new GregorianCalendar(datePicker.getYear(),
                        datePicker.getMonth(),
                        datePicker.getDayOfMonth());
                callApi(datePicker.getMonth() + 1, datePicker.getYear());
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

    private void callApi(int pMonth, int pYear){
        //add year also in validation
        if(m_cMonth != pMonth || m_cYear != pYear) {
            m_cSelectMonthTxt.setText(MONTH[pMonth - 1] + ", " + pYear);
            m_cMonth = pMonth;
            m_cYear = pYear;
            displayProgressBar(-1, "Loading...");
            HashMap<String, String> lParams = new HashMap<>();
            lParams.put(Constants.MONTH, /*"10"*/"" + pad(m_cMonth));
            lParams.put(Constants.YEAR, /*"2015"*/"" + m_cYear);
            if(m_cMemPos > 0){
                lParams.put(Constants.USER_UUID,  m_cMembersDic.get(m_cMembersList.get(m_cMemPos)));
            }
            RequestManager.getInstance(DSAViewAttendanceHistory.this)
                    .placeRequest(Constants.ATTENDANCES, Attendances.class, DSAViewAttendanceHistory.this, lParams, false);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (userSelect) {
            switch (parent.getId()) {
                case R.id.MEMBERS_LIST_SPIN:
                    m_cMemPos = position;
                    if (m_cMemPos >= 0) {
                        displayProgressBar(-1, "Loading...");
                        HashMap<String, String> lParams = new HashMap<>();
                        lParams.put(Constants.MONTH, /*"10"*/"" + pad(m_cMonth));
                        lParams.put(Constants.YEAR, /*"2015"*/"" + m_cYear);
                        if(m_cMemPos >= 0){
                            lParams.put(Constants.USER_UUID,  m_cMembersDic.get(m_cMembersList.get(m_cMemPos)));
                        }
                        RequestManager.getInstance(DSAViewAttendanceHistory.this)
                                .placeRequest(Constants.ATTENDANCES, Attendances.class, DSAViewAttendanceHistory.this, lParams, false);
                    }
                    break;
            }
            userSelect = false;
        }
    }

    /*@Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (userSelect) {
            switch (parent.getId()) {
                case R.id.MEMBERS_LIST_SPIN:
                    m_cMemPos = position;
                    if (m_cMemPos > 0) {
                        m_cAttendanceList.clear();
                        m_cAttendanceList.add(m_cSpareList.get(m_cMemPos - 1));
                        m_cRecycAdapter.notifyDataSetChanged();
                    } else if (m_cMemPos == 0) {
                        if (null != m_cSpareList && m_cSpareList.size() > 0) {
                            m_cAttendanceList.clear();
                            for(AttendanceData lAttenData : m_cSpareList)
                                m_cAttendanceList.add(lAttenData);
                            m_cRecycAdapter.notifyDataSetChanged();
                        }
                    }
                    break;
            }
            userSelect = false;
        }
    }*/

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        userSelect = true;
        return false;
    }
}
