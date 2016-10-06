package com.whatsloan.dsa.customadapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.whatsloan.dsa.model.Project;
import com.whatsloan.dsa.model.ProjectStruct;
import com.whatsloan.dsa.uifragments.ViewAddLSRQuery;
import com.whatsloan.dsa.uifragments.ViewAddProjectProperty;
import com.whatsloan.dsa.uiframework.DSAFragmentBaseClass;

/**
 * Created by S.K. Pissay on 25/3/16.
 */
public class PagerAdapterForProjectsAndLSR extends FragmentStatePagerAdapter {

    int m_cNumOfTabs;
    Project m_cObjProject;
    ProjectStruct m_cObjProjectStruct;
    String m_cId;
    public DSAFragmentBaseClass m_cObjFragmentBase;
    boolean m_cIsNewProj;
    boolean m_cIsApproved;

    public PagerAdapterForProjectsAndLSR(FragmentManager pFragment,  DSAFragmentBaseClass pObjFragmentBase,
                                int pNumOfTabs, Project pObjProject, ProjectStruct pObjProjectStruct,
                                         boolean pIsNewProj, boolean pApproved) {
        super(pFragment);
        this.m_cNumOfTabs = pNumOfTabs;
        this.m_cObjProject = pObjProject;
        this.m_cObjFragmentBase = pObjFragmentBase;
        this.m_cObjProjectStruct = pObjProjectStruct;
        this.m_cIsNewProj = pIsNewProj;
        this.m_cIsApproved = pApproved;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                m_cObjFragmentBase = ViewAddProjectProperty.newInstance(position, m_cObjProject, m_cObjProjectStruct, m_cIsNewProj, m_cIsApproved);
                return m_cObjFragmentBase;
            case 1:
                m_cObjFragmentBase = ViewAddLSRQuery.newInstance(position, m_cObjProjectStruct, m_cObjProject);
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
