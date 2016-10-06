package com.whatsloan.dsa.interfaces;

import android.view.View;

import com.whatsloan.dsa.model.Members;
import com.whatsloan.dsa.model.MembersData;

/**
 * Created by S.K. Pissay on 30/3/16.
 */
public interface RecyclerLandingListener {
    public void onInfoClick(int pPostion, Members pMemberss, MembersData pMembersData, View pView);
}
