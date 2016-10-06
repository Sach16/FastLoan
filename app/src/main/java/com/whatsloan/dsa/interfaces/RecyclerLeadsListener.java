package com.whatsloan.dsa.interfaces;

import android.view.View;

import com.whatsloan.dsa.model.LeadsDatum;
import com.whatsloan.dsa.model.Leads;

/**
 * Created by S.K. Pissay on 29/3/16.
 */
public interface RecyclerLeadsListener {
    public void onInfoClick(int pPostion, Leads pLeads, LeadsDatum pLeadsDatum, View pView);
}
