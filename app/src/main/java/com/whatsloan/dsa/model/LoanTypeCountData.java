package com.whatsloan.dsa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by S.K. Pissay on 8/5/16.
 */
public class LoanTypeCountData {

    @SerializedName("leads")
    @Expose
    private LeadsHLPL leads;
    @SerializedName("customers")
    @Expose
    private CustomersHLPL customers;

    public LeadsHLPL getLeads() {
        return leads;
    }

    public void setLeads(LeadsHLPL leads) {
        this.leads = leads;
    }

    public CustomersHLPL getCustomers() {
        return customers;
    }

    public void setCustomers(CustomersHLPL customers) {
        this.customers = customers;
    }
}