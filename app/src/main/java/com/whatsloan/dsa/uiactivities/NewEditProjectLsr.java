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
import com.whatsloan.dsa.customadapters.PagerAdapterForProjectsAndLSR;
import com.whatsloan.dsa.model.Project;
import com.whatsloan.dsa.model.ProjectStruct;
import com.whatsloan.dsa.uiframework.DSABaseActivity;

import butterknife.ButterKnife;

/**
 * Created by S.K. Pissay on 25/3/16.
 */
public class NewEditProjectLsr extends DSABaseActivity{
    private TabLayout m_cTabLayout;
    private ViewPager m_cPager;
    private View m_cView;
    Project m_cObjProject;
    ProjectStruct m_cObjProjectStruct;
    public static boolean IS_NEW_PROJECT = true;
    public static boolean APPROVED = true;
    //    private SwipeRefreshLayout swipeView;
    private PagerAdapterForProjectsAndLSR adapter;
    private int m_cBackPressed;

    @Override
    protected void onCreate(Bundle pSavedInstance) {
        super.onCreate(pSavedInstance);
        setContentView(R.layout.activity_slider_layout);

        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.new_or_edit_project_and_lsr);
        m_cView = stub.inflate();
        ButterKnife.bind(this);

        String lTitle = null;
        if(getIntent().getBooleanExtra("IS_NEW_PROJECT", false)){
            IS_NEW_PROJECT = getIntent().getBooleanExtra("IS_NEW_PROJECT", false);
            lTitle = "New Task Assignment";
        }else{
            //TODO show the lead name
            lTitle = "Lead Name";
            IS_NEW_PROJECT = getIntent().getBooleanExtra("IS_NEW_PROJECT", false);
            APPROVED = getIntent().getBooleanExtra("APPROVED", false);
            m_cObjProject = getIntent().getParcelableExtra("PROJECT");
            m_cObjProjectStruct = getIntent().getParcelableExtra("PROJECTSTRUCT");
            if(null != m_cObjProject){
                lTitle = m_cObjProject.getData().getName();
            }
        }

        setTitle(lTitle, false, true, true, false);
        ISSWIPE = false;

        // Slider Initilization
        m_cObjSliderMenu = (DrawerLayout) findViewById(R.id.drawer_layout);
        m_cObjSliderMenu.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        m_cContainFragment = (FrameLayout) findViewById(R.id.main_content_container);

        m_cBackPressed = 0;

        initilization();
    }

    private void initilization() {
//        displayProgressBar(-1, "Loading Specilties... ");
//        m_cObjTransportMgr.getSpeciality(EURemediesMacros.getSessionId(this), this);

        m_cPager = (ViewPager) m_cView.findViewById(R.id.PAGER);
        m_cTabLayout = (TabLayout) m_cView.findViewById(R.id.TAB_LAYOUT);

//        swipeView = (SwipeRefreshLayout) m_cView.findViewById(R.id.SWIPE_REFRESH);

        m_cTabLayout.addTab(m_cTabLayout.newTab().setText("VIEW PROJECT/PROPERTY"));
        m_cTabLayout.addTab(m_cTabLayout.newTab().setText("LSR QUERIES"));
        m_cTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        m_cPager = (ViewPager) m_cView.findViewById(R.id.PAGER);
        m_cPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(m_cTabLayout));
        adapter = new PagerAdapterForProjectsAndLSR(getSupportFragmentManager(),
                m_cObjFragmentBase,
                m_cTabLayout.getTabCount(),
                m_cObjProject,
                m_cObjProjectStruct,
                IS_NEW_PROJECT,
                APPROVED);
        m_cPager.setAdapter(adapter);

        m_cTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                m_cPager.setCurrentItem(tab.getPosition());
//                swipeView.setEnabled(false);
                switch (tab.getPosition()) {
                    case 0:
                        setTitle(m_cObjProject != null? m_cObjProject.getData().getName() : "LSR QUERIES", false, true, true, false);
                        // NOTHING TO DO HERE
                        break;
                    case 1:
                        setTitle(m_cObjProject != null? m_cObjProject.getData().getName() : "LSR QUERIES", false, true, true, false);
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
                }
            }
        });*/
    }

    @Override
    protected void handleUIMessage(Message pObjMessage) {

    }
}
