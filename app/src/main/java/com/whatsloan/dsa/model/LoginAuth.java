package com.whatsloan.dsa.model;

/**
 * Created by S.K. Pissay on 28/3/16.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

public class LoginAuth extends SugarRecord{

    @SerializedName("data")
    @Expose
    private DataAuth data;

    public LoginAuth(){}

    public LoginAuth(DataAuth data){
        this.data = data;
    }

    /**
     *
     * @return
     * The data
     */
    public DataAuth getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(DataAuth data) {
        this.data = data;
    }

}