package com.whatsloan.dsa.interfaces;

import android.view.View;

import com.whatsloan.dsa.model.Campaigns;
import com.whatsloan.dsa.model.CampaignsData;

/**
 * Created by S.K. Pissay on 4/4/16.
 */
public interface RecyclerCampaignListener {
    public void onInfoClick(int pPostion, Campaigns pCampaigns, CampaignsData pCampaignData, View pView);
}
