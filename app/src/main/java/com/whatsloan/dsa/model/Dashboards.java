package com.whatsloan.dsa.model;

/**
 * Created by S.K. Pissay on 7/5/16.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dashboards {

    @SerializedName("data")
    @Expose
    private DashboardsTeam data;

    /**
     *
     * @return
     * The data
     */
    public DashboardsTeam getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(DashboardsTeam data) {
        this.data = data;
    }

}