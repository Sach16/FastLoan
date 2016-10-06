package com.whatsloan.dsa.interfaces;

import android.view.View;

import com.whatsloan.dsa.model.Bank;
import com.whatsloan.dsa.model.BankAll;
import com.whatsloan.dsa.model.Project;
import com.whatsloan.dsa.model.ProjectStruct;
import com.whatsloan.dsa.model.QueriesData;

/**
 * Created by S.K. Pissay on 13/4/16.
 */
public interface RecyclerBanksListener {
    public void onProjectClicked(int pPostion, ProjectStruct pProjectStruct, Project pProject, View pView);
    public void onBankClicked(int pPostion, BankAll pBankAll, Bank pBank, View pView);
    public void onLSRClicked(int pPostion, ProjectStruct pProjectStruct, Project pProject, QueriesData pQueriesData, View pView);
}

