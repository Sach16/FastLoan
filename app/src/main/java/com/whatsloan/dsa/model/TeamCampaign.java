package com.whatsloan.dsa.model;

/**
 * Created by S.K. Pissay on 7/4/16.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeamCampaign {

    @SerializedName("data")
    @Expose
    private TeamsData data;

    /**
     *
     * @return
     * The data
     */
    public TeamsData getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(TeamsData data) {
        this.data = data;
    }

}