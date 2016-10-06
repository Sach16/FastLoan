package com.whatsloan.dsa.interfaces;

import android.view.View;

import com.whatsloan.dsa.model.LoansData;

/**
 * Created by S.K. Pissay on 13/5/16.
 */
public interface RecyclerLoansListener {
    public void onInfoClick(int pPostion, LoansData pLoansData, View pView);
    public void onEditClick(int pPostion, LoansData pLoansData, View pView);
    public void onJustClick(int pPostion, LoansData pLoansData, View pView);
}
