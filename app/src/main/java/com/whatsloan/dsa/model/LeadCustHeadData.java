package com.whatsloan.dsa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by S.K. Pissay on 8/5/16.
 */
public class LeadCustHeadData {

    @SerializedName("loanTypeCount")
    @Expose
    private LoanTypeCount loanTypeCount;
    @SerializedName("leads")
    @Expose
    private Leads leads;
    //The Loans below contains the customersloans
    @SerializedName("customers")
    @Expose
    private Loans loans;

    /**
     *
     * @return
     * The loanTypeCount
     */
    public LoanTypeCount getLoanTypeCount() {
        return loanTypeCount;
    }

    /**
     *
     * @param loanTypeCount
     * The loanTypeCount
     */
    public void setLoanTypeCount(LoanTypeCount loanTypeCount) {
        this.loanTypeCount = loanTypeCount;
    }

    /**
     *
     * @return
     * The leads
     */
    public Leads getLeads() {
        return leads;
    }

    /**
     *
     * @param leads
     * The leads
     */
    public void setLeads(Leads leads) {
        this.leads = leads;
    }

    public Loans getLoans() {
        return loans;
    }

    public void setLoans(Loans loans) {
        this.loans = loans;
    }
}