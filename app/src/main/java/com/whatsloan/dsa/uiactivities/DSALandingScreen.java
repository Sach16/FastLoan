package com.whatsloan.dsa.uiactivities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.orm.query.Select;
import com.whatsloan.dsa.R;
import com.whatsloan.dsa.customadapters.CustomRecyclerAdapterForListOfLanding;
import com.whatsloan.dsa.interfaces.RecyclerLandingListener;
import com.whatsloan.dsa.macros.DSAMacros;
import com.whatsloan.dsa.model.DataAuth;
import com.whatsloan.dsa.model.Landing;
import com.whatsloan.dsa.model.Members;
import com.whatsloan.dsa.model.MembersData;
import com.whatsloan.dsa.network.Constants;
import com.whatsloan.dsa.network.RequestManager;
import com.whatsloan.dsa.uiframework.DSABaseActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by S.K. Pissay on 20/3/16.
 */
public class DSALandingScreen extends DSABaseActivity implements RecyclerLandingListener{

    private View m_cView;

    @Nullable
    @Bind(R.id.HISTORY_BTN)
    Button m_cHistoryBtn;

    @Nullable
    @Bind(R.id.CUSTOMER_LAY)
    RelativeLayout m_cCustomerLay;

    @Nullable
    @Bind(R.id.LEAD_LAY)
    RelativeLayout m_cLeadLay;

    @Nullable
    @Bind(R.id.CUSTOMERS_COUNT)
    TextView m_cCustomerNo;

    @Nullable
    @Bind(R.id.LEAD_COUNT)
    TextView m_cLeadNo;

    @Nullable
    @Bind(R.id.RECYC_LANDING)
    RecyclerView m_cRecyclerLand;

    @Nullable
    @Bind(R.id.LANDING_DATE)
    TextView m_cLandingDate;

    @Nullable
    @Bind(R.id.LANDING_TIME)
    TextView m_cLandingTime;

    @Nullable
    @Bind(R.id.LANDING_FULNAME)
    TextView m_cLandingName;

    @Nullable
    @Bind(R.id.LANDING_ATTEN_DAY)
    TextView m_cLandingAttenDay;

    private CustomRecyclerAdapterForListOfLanding m_cRecyclerAdapter;

    @Override
    protected void onCreate(Bundle pSavedInstance) {
        super.onCreate(pSavedInstance);
        setContentView(R.layout.activity_slider_layout);

        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.landing_screen);
        m_cView = stub.inflate();
        ButterKnife.bind(this);
        setTitle("", false, false, true, true);

        m_cObjSliderMenu = (DrawerLayout) findViewById(R.id.drawer_layout);
        m_cObjSliderMenu.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        m_cContainFragment = (FrameLayout) findViewById(R.id.main_content_container);
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    private void init() {
        Select ldataAuth = Select.from(DataAuth.class);
        DataAuth lDAuth = (DataAuth) ldataAuth.first();
        m_cLandingName.setText(lDAuth.getFirstName() + " " + lDAuth.getLastName());
        m_cLandingDate.setText(DSAMacros.getStartDay(this).get(0));
        m_cLandingTime.setText(DSAMacros.getStartDay(this).get(1));

        Calendar lCalendar = Calendar.getInstance();
        String date = DSAMacros.getDateFormat(new Date(), null, null, DSAMacros.DISPLAY_DATE_TIME_FORMAT).substring(0, 6);
        String[] dateArray = date.split(" ");
        if(dateArray.length>0){
            int num = Integer.parseInt(dateArray[0]);
            if(num<10){
                date = date.replace("0", "");
            }
        }
        date = date.replace(" ", DSAMacros.getDayNumberSuffix(lCalendar.get(Calendar.DAY_OF_MONTH)) + " ");
        m_cLandingAttenDay.setText("Attendance for " + date + " ");
        m_cRecyclerLand.setLayoutManager(new LinearLayoutManager(this));
        displayProgressBar(-1, "Loading ...");
        HashMap<String, String> lParams = new HashMap<>();
        //TODO uncomment when api side is proper
//        lParams.put(Constants.INSCLUDE, "members,members.taskStatusCount,members.tasks");
//        lParams.put(Constants.INSCLUDE, "members,members.tasks");
        lParams.put(Constants.INSCLUDE, "members.taskStatusCount,members.tasks.status,members.attendances");
        RequestManager.getInstance(this).placeRequest(Constants.LANDING, Landing.class, this, lParams, false);
    }

    @Override
    public void onBackPressed() {
        displayYesNoAlert(-1, "Are you sure you want to exit?");
    }

    @Override
    protected void handleUIMessage(Message pObjMessage) {
        switch (pObjMessage.what) {
            case DSAMacros.NOTIFICATION_NO_NETWORK_CONNECTION_RETRY:
                displaySnack(m_cView, "Please check internet connection");
                break;
            case DSAMacros.YES_MESSAGE:
                finish();
                break;
            case DSAMacros.NO_MESSAGE:
                break;
        }
    }

    @OnClick({R.id.HISTORY_BTN, R.id.CUSTOMER_LAY, R.id.LEAD_LAY, R.id.DASHBOARD_LAY, R.id.PRODUCTS_LAY})
    public void onClick(View v) {
        super.onClick(v);
        Intent lObjIntent;
        switch (v.getId()) {
            case R.id.HISTORY_BTN:
                lObjIntent = new Intent(this, DSAViewAttendanceHistory.class);
                lObjIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(lObjIntent);
                break;
            case R.id.CUSTOMER_LAY:
                lObjIntent = new Intent(this, DSACustomers.class);
                lObjIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(lObjIntent);
                break;
            case R.id.LEAD_LAY:
                lObjIntent = new Intent(this, DSALeads.class);
                lObjIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(lObjIntent);
                break;
            case R.id.DASHBOARD_LAY:
                lObjIntent = new Intent(this, DSADashboard.class);
                lObjIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(lObjIntent);
                break;
            case R.id.PRODUCTS_LAY:
                lObjIntent = new Intent(this, DSAProducts.class);
                lObjIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(lObjIntent);
                break;
        }
    }

    @Override
    public void onAPIResponse(Object response, String apiMethod) {
        super.onAPIResponse(response, apiMethod);
        switch (apiMethod) {
            case Constants.LANDING:
                hideDialog();
                Landing lObjLanding = (Landing) response;
                if (null != lObjLanding.getData() && lObjLanding.getData().getTeam().getData().get(0).getMembers().getData().size() > 0) {
                    m_cCustomerNo.setText(lObjLanding.getData().getCustomer().getCount().toString());
                    m_cLeadNo.setText(lObjLanding.getData().getLead().getCount().toString());

                    Members lObjMembers = lObjLanding.getData().getTeam().getData().get(0).getMembers();
                    m_cRecyclerAdapter = new CustomRecyclerAdapterForListOfLanding(this, lObjMembers, this);
                    m_cRecyclerLand.setAdapter(m_cRecyclerAdapter);
                }
                break;
        }
    }

    @Override
    public void onErrorResponse(VolleyError error, String apiMethod) {
        hideDialog();
        switch (apiMethod) {
            case Constants.LANDING:
                if(error != null && error.networkResponse != null) {
                    if (error.networkResponse.statusCode == 401) {
                        logout();
                        displaySnack(m_cView, "Session Expired");
                    }
                }else {
                    super.onErrorResponse(error, apiMethod);
                }
                break;
            default:
                super.onErrorResponse(error, apiMethod);
                break;
        }
    }

    @Override
    public void onInfoClick(int pPostion, Members pMemberss, MembersData pMembersData, View pView) {
        Intent lIntent = new Intent(this, ViewProfile.class);
        lIntent.putExtra(Constants.MEMBER_UUID, pMembersData.getUuid());
        lIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(lIntent);
    }
}
