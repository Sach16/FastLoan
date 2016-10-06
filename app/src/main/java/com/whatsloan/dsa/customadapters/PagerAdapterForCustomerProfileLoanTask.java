package com.whatsloan.dsa.customadapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.text.TextUtils;

import com.whatsloan.dsa.model.CustomersData;
import com.whatsloan.dsa.uifragments.CustomerLoanDetails;
import com.whatsloan.dsa.uifragments.CustomerProfile;
import com.whatsloan.dsa.uifragments.CustomerTaskView;
import com.whatsloan.dsa.uiframework.DSAFragmentBaseClass;

/**
 * Created by S.K. Pissay on 3/4/16.
 */
public class PagerAdapterForCustomerProfileLoanTask extends FragmentStatePagerAdapter {

    int m_cNumOfTabs;
    String m_cJsonObject;
    CustomersData m_cObjCustomer;
    public DSAFragmentBaseClass m_cObjFragmentBase;

    public PagerAdapterForCustomerProfileLoanTask(FragmentManager pFragment,  DSAFragmentBaseClass pObjFragmentBase,
                                         int pNumOfTabs, String pJsonObject, CustomersData pObjCustomer) {
        super(pFragment);
        this.m_cNumOfTabs = pNumOfTabs;
        this.m_cJsonObject = TextUtils.isEmpty(pJsonObject) ? "" :  pJsonObject;
        this.m_cObjFragmentBase = pObjFragmentBase;
        this.m_cObjCustomer = pObjCustomer;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                m_cObjFragmentBase = CustomerProfile.newInstance(position, m_cJsonObject, m_cObjCustomer);
                return m_cObjFragmentBase;
            case 1:
                m_cObjFragmentBase = CustomerLoanDetails.newInstance(position, m_cJsonObject, m_cObjCustomer);
                return m_cObjFragmentBase;
            case 2:
                m_cObjFragmentBase = CustomerTaskView.newInstance(position, m_cJsonObject, m_cObjCustomer);
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

