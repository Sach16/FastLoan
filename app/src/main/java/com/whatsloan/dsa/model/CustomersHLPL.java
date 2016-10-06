package com.whatsloan.dsa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by S.K. Pissay on 8/5/16.
 */
public class CustomersHLPL {
    @SerializedName("data")
    @Expose
    private CustomersHLPLData data;

    /**
     *
     * @return
     * The data
     */
    public CustomersHLPLData getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(CustomersHLPLData data) {
        this.data = data;
    }

}
