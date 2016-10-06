package com.whatsloan.dsa.model;

/**
 * Created by S.K. Pissay on 30/3/16.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Sources {

    @SerializedName("data")
    @Expose
    private List<LeadsSrcDatum> data = new ArrayList<LeadsSrcDatum>();

    /**
     *
     * @return
     * The data
     */
    public List<LeadsSrcDatum> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<LeadsSrcDatum> data) {
        this.data = data;
    }

}
