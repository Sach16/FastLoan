package com.whatsloan.dsa.interfaces;

import android.view.View;

import com.whatsloan.dsa.model.CustomersData;

/**
 * Created by S.K. Pissay on 23/4/16.
 */
public interface RecyclerCustomersListener {
    public void onInfoClick(int pPostion, CustomersData pCustomersData, View pView);
}
