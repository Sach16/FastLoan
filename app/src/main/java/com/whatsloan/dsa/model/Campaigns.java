package com.whatsloan.dsa.model;

/**
 * Created by S.K. Pissay on 5/4/16.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Campaigns {

    @SerializedName("data")
    @Expose
    private List<CampaignsData> data = new ArrayList<CampaignsData>();
    @SerializedName("meta")
    @Expose
    private Meta meta;

    /**
     *
     * @return
     * The data
     */
    public List<CampaignsData> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<CampaignsData> data) {
        this.data = data;
    }

    /**
     *
     * @return
     * The meta
     */
    public Meta getMeta() {
        return meta;
    }

    /**
     *
     * @param meta
     * The meta
     */
    public void setMeta(Meta meta) {
        this.meta = meta;
    }

}
