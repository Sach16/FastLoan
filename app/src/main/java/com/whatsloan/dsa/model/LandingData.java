package com.whatsloan.dsa.model;

/**
 * Created by S.K. Pissay on 1/4/16.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LandingData {
    @SerializedName("lead")
    @Expose
    private Lead lead;
    @SerializedName("customer")
    @Expose
    private Customer customer;
    @SerializedName("team")
    @Expose
    private Team team;

    /**
     *
     * @return
     * The lead
     */
    public Lead getLead() {
        return lead;
    }

    /**
     *
     * @param lead
     * The lead
     */
    public void setLead(Lead lead) {
        this.lead = lead;
    }

    /**
     *
     * @return
     * The customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     *
     * @param customer
     * The customer
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     *
     * @return
     * The team
     */
    public Team getTeam() {
        return team;
    }

    /**
     *
     * @param team
     * The team
     */
    public void setTeam(Team team) {
        this.team = team;
    }

}