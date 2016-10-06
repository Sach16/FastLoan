package com.whatsloan.dsa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by S.K. Pissay on 8/5/16.
 */
public class LeadCustHead {

    @SerializedName("data")
    @Expose
    private LeadCustHeadData data;

    /**
     *
     * @return
     * The data
     */
    public LeadCustHeadData getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(LeadCustHeadData data) {
        this.data = data;
    }

}