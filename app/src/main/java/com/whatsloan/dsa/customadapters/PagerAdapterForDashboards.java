package com.whatsloan.dsa.customadapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.text.TextUtils;

import com.whatsloan.dsa.uifragments.DashboardBuilderFragment;
import com.whatsloan.dsa.uifragments.DashboardReferralFragment;
import com.whatsloan.dsa.uifragments.DashboardTeamFragment;
import com.whatsloan.dsa.uiframework.DSAFragmentBaseClass;

/**
 * Created by S.K. Pissay on 12/4/16.
 */
public class PagerAdapterForDashboards extends FragmentStatePagerAdapter {

    int m_cNumOfTabs;
    String m_cJsonObject;
    String m_cId;
    public DSAFragmentBaseClass m_cObjFragmentBase;

    public PagerAdapterForDashboards(FragmentManager pFragment,  DSAFragmentBaseClass pObjFragmentBase,
                                int pNumOfTabs, String pJsonObject, String pId) {
        super(pFragment);
        this.m_cNumOfTabs = pNumOfTabs;
        this.m_cJsonObject = TextUtils.isEmpty(pJsonObject) ? "" :  pJsonObject;
        this.m_cObjFragmentBase = pObjFragmentBase;
        this.m_cId = pId;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                m_cObjFragmentBase = DashboardTeamFragment.newInstance(position, m_cJsonObject);
                return m_cObjFragmentBase;
            case 1:
                m_cObjFragmentBase = DashboardBuilderFragment.newInstance(position, m_cJsonObject);
                return m_cObjFragmentBase;
            case 2:
                m_cObjFragmentBase = DashboardReferralFragment.newInstance(position, m_cJsonObject);
                return m_cObjFragmentBase;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return m_cNumOfTabs;
    }
}

