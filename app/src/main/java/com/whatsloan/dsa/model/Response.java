package com.whatsloan.dsa.model;

/**
 * Created by S.K. Pissay on 11/4/16.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("data")
    @Expose
    private ResponseData data;

    /**
     *
     * @return
     * The data
     */
    public ResponseData getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(ResponseData data) {
        this.data = data;
    }

}