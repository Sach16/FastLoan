package com.whatsloan.dsa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by S.K. Pissay on 8/5/16.
 */
public class LeadsHLPL {

    @SerializedName("data")
    @Expose
    private LeadsHLPLData data;

    /**
     *
     * @return
     * The data
     */
    public LeadsHLPLData getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(LeadsHLPLData data) {
        this.data = data;
    }

}
