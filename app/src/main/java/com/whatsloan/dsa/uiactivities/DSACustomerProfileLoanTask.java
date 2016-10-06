package com.whatsloan.dsa.uiactivities;

import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;

import com.whatsloan.dsa.R;
import com.whatsloan.dsa.customadapters.PagerAdapterForCustomerProfileLoanTask;
import com.whatsloan.dsa.model.CustomersData;
import com.whatsloan.dsa.model.MembersData;
import com.whatsloan.dsa.uiframework.DSABaseActivity;

import butterknife.ButterKnife;

/**
 * Created by S.K. Pissay on 3/4/16.
 */
public class DSACustomerProfileLoanTask extends DSABaseActivity{
    private TabLayout m_cTabLayout;
    private ViewPager m_cPager;
    private View m_cView;
    public static String m_cLoanUuid;
    //    private SwipeRefreshLayout swipeView;
    private PagerAdapterForCustomerProfileLoanTask adapter;
    MembersData m_cObjMemberData;
    CustomersData m_cObjCustomersData;
    private int m_cBackPressed;

    @Override
    protected void onCreate(Bundle pSavedInstance) {
        super.onCreate(pSavedInstance);
        setContentView(R.layout.activity_slider_layout);

        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.customer_profile_loan_task);
        m_cView = stub.inflate();
        ButterKnife.bind(this);

        String lTitle = null;
        m_cObjMemberData = getIntent().getParcelableExtra("MEMBER_ID");
        m_cObjCustomersData = getIntent().getParcelableExtra("CUSTOMER_ID");
        if(null != m_cObjMemberData) {
            lTitle = m_cObjMemberData.getFirstName() + " " + m_cObjMemberData.getLastName();
        }
        if(null != m_cObjCustomersData){
            lTitle = m_cObjCustomersData.getFirstName()+" "+m_cObjCustomersData.getLastName();
        }

        setTitle(lTitle, false, true, true, false);
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
//        displayProgressBar(-1, "Loading Specilties... ");
//        m_cObjTransportMgr.getSpeciality(EURemediesMacros.getSessionId(this), this);

        m_cPager = (ViewPager) m_cView.findViewById(R.id.PAGER);
        m_cTabLayout = (TabLayout) m_cView.findViewById(R.id.TAB_LAYOUT);

//        swipeView = (SwipeRefreshLayout) m_cView.findViewById(R.id.SWIPE_REFRESH);

        m_cTabLayout.addTab(m_cTabLayout.newTab().setText("CUSTOMER PROFILE"));
        m_cTabLayout.addTab(m_cTabLayout.newTab().setText("LOAN DETAILS"));
        m_cTabLayout.addTab(m_cTabLayout.newTab().setText("TASK VIEW"));
        m_cTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        m_cPager = (ViewPager) m_cView.findViewById(R.id.PAGER);
        m_cPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(m_cTabLayout));
//        m_cPager.setOffscreenPageLimit(2);
        adapter = new PagerAdapterForCustomerProfileLoanTask(getSupportFragmentManager(),
                m_cObjFragmentBase,
                m_cTabLayout.getTabCount(),
                m_cJsonSpecialityObject,
                m_cObjCustomersData
                );
        m_cPager.setAdapter(adapter);

        m_cTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                m_cPager.setCurrentItem(tab.getPosition());
//                swipeView.setEnabled(false);
                switch (tab.getPosition()) {
                    case 0:
                        // NOTHING TO DO HERE
                        break;
                    case 1:
//                        swipeView.setEnabled(true);
//                        displayProgressBar(-1, "Loading Packages,..");
//                        m_cObjTransportMgr.getPackages("", EURemediesSpecialityScreen.this);
                        break;
                    case 2:
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
                    case 2:
//                        ISSWIPE = true;
//                        swipeView.setRefreshing(true);
//                        m_cObjTransportMgr.getPackages("", EURemediesSpecialityScreen.this);
                        break;
                }
            }
        });*/
    }

    @Override
    protected void handleUIMessage(Message pObjMessage) {

    }
}
