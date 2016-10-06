package com.whatsloan.dsa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by S.K. Pissay on 7/5/16.
 */
public class DashboardsTeam {

    //Teams

    @SerializedName("attendance")
    @Expose
    private Lead attendance;
    @SerializedName("leads")
    @Expose
    private Lead leads;
    @SerializedName("logins")
    @Expose
    private Logins logins;
    @SerializedName("disbursements")
    @Expose
    private Disbursements disbursements;
    @SerializedName("waivers")
    @Expose
    private Waivers waivers;
    @SerializedName("sanctions")
    @Expose
    private Sanctions sanctions;
    @SerializedName("deviations")
    @Expose
    private Deviations deviations;
    @SerializedName("target")
    @Expose
    private Deviations target;
    @SerializedName("achieved")
    @Expose
    private Deviations achieved;
    @SerializedName("incentive_plan")
    @Expose
    private Deviations incentivePlan;
    @SerializedName("incentive_earned")
    @Expose
    private Deviations incentiveEarned;

    //Builders

    @SerializedName("first_disbursement")
    @Expose
    private Disbursement firstDisbursement;
    @SerializedName("part_disbursement")
    @Expose
    private Disbursement partDisbursement;
    @SerializedName("final_disbursement")
    @Expose
    private Disbursement finalDisbursement;
    @SerializedName("payout")
    @Expose
    private Payout payout;

    //Referrals

    @SerializedName("disbursals")
    @Expose
    private Disbursement disbursals;
    @SerializedName("total_paid")
    @Expose
    private TotalPaid totalPaid;
    @SerializedName("total_payout_earned")
    @Expose
    private TotalPaid totalPayoutEarned;
    @SerializedName("balance")
    @Expose
    private TotalPaid balance;


    /**
     *
     * @return
     * The attendance
     */
    public Lead getAttendance() {
        return attendance;
    }

    /**
     *
     * @param attendance
     * The attendance
     */
    public void setAttendance(Lead attendance) {
        this.attendance = attendance;
    }

    /**
     *
     * @return
     * The leads
     */
    public Lead getLeads() {
        return leads;
    }

    /**
     *
     * @param leads
     * The leads
     */
    public void setLeads(Lead leads) {
        this.leads = leads;
    }

    /**
     *
     * @return
     * The logins
     */
    public Logins getLogins() {
        return logins;
    }

    /**
     *
     * @param logins
     * The logins
     */
    public void setLogins(Logins logins) {
        this.logins = logins;
    }

    /**
     *
     * @return
     * The disbursements
     */
    public Disbursements getDisbursements() {
        return disbursements;
    }

    /**
     *
     * @param disbursements
     * The disbursements
     */
    public void setDisbursements(Disbursements disbursements) {
        this.disbursements = disbursements;
    }

    /**
     *
     * @return
     * The waivers
     */
    public Waivers getWaivers() {
        return waivers;
    }

    /**
     *
     * @param waivers
     * The waivers
     */
    public void setWaivers(Waivers waivers) {
        this.waivers = waivers;
    }

    /**
     *
     * @return
     * The sanctions
     */
    public Sanctions getSanctions() {
        return sanctions;
    }

    /**
     *
     * @param sanctions
     * The sanctions
     */
    public void setSanctions(Sanctions sanctions) {
        this.sanctions = sanctions;
    }

    /**
     *
     * @return
     * The deviations
     */
    public Deviations getDeviations() {
        return deviations;
    }

    /**
     *
     * @param deviations
     * The deviations
     */
    public void setDeviations(Deviations deviations) {
        this.deviations = deviations;
    }

    public Disbursement getFirstDisbursement() {
        return firstDisbursement;
    }

    public void setFirstDisbursement(Disbursement firstDisbursement) {
        this.firstDisbursement = firstDisbursement;
    }

    public Disbursement getPartDisbursement() {
        return partDisbursement;
    }

    public void setPartDisbursement(Disbursement partDisbursement) {
        this.partDisbursement = partDisbursement;
    }

    public Disbursement getFinalDisbursement() {
        return finalDisbursement;
    }

    public void setFinalDisbursement(Disbursement finalDisbursement) {
        this.finalDisbursement = finalDisbursement;
    }

    public Payout getPayout() {
        return payout;
    }

    public void setPayout(Payout payout) {
        this.payout = payout;
    }

    public Disbursement getDisbursals() {
        return disbursals;
    }

    public void setDisbursals(Disbursement disbursals) {
        this.disbursals = disbursals;
    }

    public TotalPaid getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(TotalPaid totalPaid) {
        this.totalPaid = totalPaid;
    }

    public TotalPaid getTotalPayoutEarned() {
        return totalPayoutEarned;
    }

    public void setTotalPayoutEarned(TotalPaid totalPayoutEarned) {
        this.totalPayoutEarned = totalPayoutEarned;
    }

    public TotalPaid getBalance() {
        return balance;
    }

    public void setBalance(TotalPaid balance) {
        this.balance = balance;
    }

    public Deviations getTarget() {
        return target;
    }

    public void setTarget(Deviations target) {
        this.target = target;
    }

    public Deviations getAchieved() {
        return achieved;
    }

    public void setAchieved(Deviations achieved) {
        this.achieved = achieved;
    }

    public Deviations getIncentivePlan() {
        return incentivePlan;
    }

    public void setIncentivePlan(Deviations incentivePlan) {
        this.incentivePlan = incentivePlan;
    }

    public Deviations getIncentiveEarned() {
        return incentiveEarned;
    }

    public void setIncentiveEarned(Deviations incentiveEarned) {
        this.incentiveEarned = incentiveEarned;
    }
}