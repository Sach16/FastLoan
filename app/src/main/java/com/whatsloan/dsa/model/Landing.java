package com.whatsloan.dsa.model;

/**
 * Created by S.K. Pissay on 1/4/16.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Landing {

    @SerializedName("data")
    @Expose
    private LandingData data;

    /**
     *
     * @return
     * The data
     */
    public LandingData getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(LandingData data) {
        this.data = data;
    }

}