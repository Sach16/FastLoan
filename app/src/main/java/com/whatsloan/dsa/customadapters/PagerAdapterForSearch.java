package com.whatsloan.dsa.customadapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.whatsloan.dsa.model.Leads;
import com.whatsloan.dsa.model.Loans;
import com.whatsloan.dsa.uifragments.SearchCustomersFragment;
import com.whatsloan.dsa.uifragments.SearchLeadsFragment;
import com.whatsloan.dsa.uiframework.DSAFragmentBaseClass;

/**
 * Created by S.K. Pissay on 30/3/16.
 */
public class PagerAdapterForSearch extends FragmentStatePagerAdapter {

    int m_cNumOfTabs;
    String m_cId;
    private Leads m_cObjLeads;
    private Loans m_cLoans;

    //inner fragment items
    String m_cKey;
    String m_cValue;
    String m_cBuilder_uuid;
    String m_cProject_uuid;
    boolean m_cboth;

    public DSAFragmentBaseClass m_cObjFragmentBase;

    public PagerAdapterForSearch(FragmentManager pFragment,  DSAFragmentBaseClass pObjFragmentBase,
                                         int pNumOfTabs, String pId,
                                 String pKey, String pValue, String pBuilder_uuid, String pProject_uuid, boolean pboth) {
        super(pFragment);
        this.m_cNumOfTabs = pNumOfTabs;
        this.m_cObjFragmentBase = pObjFragmentBase;
        this.m_cId = pId;

        this.m_cKey = pKey;
        this.m_cValue = pValue;
        this.m_cBuilder_uuid = pBuilder_uuid;
        this.m_cProject_uuid = pProject_uuid;
        this.m_cboth = pboth;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                m_cObjFragmentBase = SearchLeadsFragment.newInstance(position, m_cKey, m_cValue, m_cBuilder_uuid,
                        m_cProject_uuid, m_cboth);
                return m_cObjFragmentBase;
            case 1:
                m_cObjFragmentBase = SearchCustomersFragment.newInstance(position, m_cKey, m_cValue, m_cBuilder_uuid,
                        m_cProject_uuid, m_cboth);
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