package com.whatsloan.dsa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by S.K. Pissay on 8/5/16.
 */
public class LoanTypeCount {
    @SerializedName("data")
    @Expose
    private LoanTypeCountData data;

    /**
     *
     * @return
     * The data
     */
    public LoanTypeCountData getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(LoanTypeCountData data) {
        this.data = data;
    }

}