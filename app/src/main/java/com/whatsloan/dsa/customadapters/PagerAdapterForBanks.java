package com.whatsloan.dsa.customadapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.whatsloan.dsa.uifragments.BankApprovedProjectsFragment;
import com.whatsloan.dsa.uifragments.BankApprovingFragment;
import com.whatsloan.dsa.uifragments.BankProjectsForApproval;
import com.whatsloan.dsa.uiframework.DSAFragmentBaseClass;

/**
 * Created by S.K. Pissay on 24/3/16.
 */
public class PagerAdapterForBanks extends FragmentStatePagerAdapter{

    int m_cNumOfTabs;
    String m_cId;
    public DSAFragmentBaseClass m_cObjFragmentBase;

    public PagerAdapterForBanks(FragmentManager pFragment,  DSAFragmentBaseClass pObjFragmentBase,
                                int pNumOfTabs) {
        super(pFragment);
        this.m_cNumOfTabs = pNumOfTabs;
//        this.m_cProjectsBanks = pProjectsBanks;
        this.m_cObjFragmentBase = pObjFragmentBase;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                m_cObjFragmentBase = BankApprovedProjectsFragment.newInstance(position);
                return m_cObjFragmentBase;
            case 1:
                m_cObjFragmentBase = BankApprovingFragment.newInstance(position);
                return m_cObjFragmentBase;
            case 2:
                m_cObjFragmentBase = BankProjectsForApproval.newInstance(position);
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
