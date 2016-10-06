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

import com.android.volley.VolleyError;
import com.orm.query.Select;
import com.whatsloan.dsa.R;
import com.whatsloan.dsa.customadapters.PagerAdapterForDashboards;
import com.whatsloan.dsa.model.DataAuth;
import com.whatsloan.dsa.uiframework.DSABaseActivity;

import butterknife.ButterKnife;

/**
 * Created by S.K. Pissay on 12/4/16.
 */
public class DSADashboard extends DSABaseActivity {

    private TabLayout m_cTabLayout;
    private ViewPager m_cPager;
    private View m_cView;
    //    private SwipeRefreshLayout swipeView;
    private PagerAdapterForDashboards adapter;
    private int m_cBackPressed;

    @Override
    protected void onCreate(Bundle pSavedInstance) {
        super.onCreate(pSavedInstance);
        setContentView(R.layout.activity_slider_layout);

        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.dashboards_layout);
        m_cView = stub.inflate();
        ButterKnife.bind(this);

        setTitle("Dashboard", false, true, true, false);
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
        Select ldataAuth = Select.from(DataAuth.class);
        DataAuth lDAuth = (DataAuth) ldataAuth.first();

        m_cTabLayout.addTab(m_cTabLayout.newTab().setText("TEAMS"));
        if(!lDAuth.getRole().equalsIgnoreCase("DSA_MEMBER")) {
            m_cTabLayout.addTab(m_cTabLayout.newTab().setText("BUILDER"));
            m_cTabLayout.addTab(m_cTabLayout.newTab().setText("REFERRAL"));
        }
//        m_cTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        m_cPager = (ViewPager) m_cView.findViewById(R.id.PAGER);
        m_cPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(m_cTabLayout));
        m_cPager.setOffscreenPageLimit(2);
        adapter = new PagerAdapterForDashboards(getSupportFragmentManager(),
                m_cObjFragmentBase,
                m_cTabLayout.getTabCount(),
                m_cJsonSpecialityObject,
                "");
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
                        // NOTHING TO DO HERE
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
                swipeView.setEnabled(false);
                switch (m_cPager.getCurrentItem()) {
                    case 0:
//                        swipeView.setRefreshing(true);
//                        m_cObjTransportMgr.getSpeciality(EURemediesMacros.getSessionId(EURemediesSpecialityScreen.this), EURemediesSpecialityScreen.this);
                        break;
                    case 1:
                        ISSWIPE = true;
//                        swipeView.setRefreshing(true);
//                        m_cObjTransportMgr.getPackages("", EURemediesSpecialityScreen.this);
                        break;
                    case 2:
//                        swipeView.setRefreshing(false);
//                        swipeView.setEnabled(false);
                        break;
                }
            }
        });*/
    }

    @Override
    protected void handleUIMessage(Message pObjMessage) {

    }

    @Override
    public void onAPIResponse(Object response, String apiMethod) {
        switch (apiMethod) {
            default:
                super.onAPIResponse(response, apiMethod);
                break;
        }
    }

    @Override
    public void onErrorResponse(VolleyError error, String apiMethod) {
        hideDialog();
        switch (apiMethod) {
            default:
                super.onErrorResponse(error, apiMethod);
                break;
        }
    }
}
