package com.whatsloan.dsa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by S.K. Pissay on 8/5/16.
 */
public class CustomersHLPLData {
    @SerializedName("HL")
    @Expose
    private Integer HL;
    @SerializedName("PL")
    @Expose
    private Integer PL;

    /**
     *
     * @return
     * The HL
     */
    public Integer getHL() {
        return HL;
    }

    /**
     *
     * @param HL
     * The HL
     */
    public void setHL(Integer HL) {
        this.HL = HL;
    }

    /**
     *
     * @return
     * The PL
     */
    public Integer getPL() {
        return PL;
    }

    /**
     *
     * @param PL
     * The PL
     */
    public void setPL(Integer PL) {
        this.PL = PL;
    }

}