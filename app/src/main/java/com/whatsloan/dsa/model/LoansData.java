package com.whatsloan.dsa.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by S.K. Pissay on 23/4/16.
 */
public class LoansData implements Parcelable{

    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("eligible_amount")
    @Expose
    private String eligibleAmount;
    @SerializedName("approved_amount")
    @Expose
    private String approvedAmount;
    @SerializedName("interest_rate")
    @Expose
    private String interestRate;
    @SerializedName("emi")
    @Expose
    private String emi;
    @SerializedName("appid")
    @Expose
    private String appid;
    @SerializedName("bank")
    @Expose
    private Bank bank;
    @SerializedName("type")
    @Expose
    private Type type;
    @SerializedName("agent")
    @Expose
    private Agent agent;

    //added extras
    @SerializedName("applied_on")
    @Expose
    private PosessionDate appliedOn;
    @SerializedName("approval_date")
    @Expose
    private PosessionDate approvalDate;
    @SerializedName("emi_start_date")
    @Expose
    private PosessionDate emiStartDate;
    @SerializedName("created_at")
    @Expose
    private CreatedAt createdAt;
    @SerializedName("updated_at")
    @Expose
    private UpdatedAt updatedAt;
    @SerializedName("loan_statuses")
    @Expose
    private Status status;
    @SerializedName("customer")
    @Expose
    private CustomersLoan customer;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("history")
    @Expose
    private History history;
    @SerializedName("attachments")
    @Expose
    private Attachments attachments;
    @SerializedName("total_tat")
    @Expose
    private TotalTat totalTat;
    @SerializedName("loan_status_tat")
    @Expose
    private LoanStatusTat loanStatusTat;
    @SerializedName("team")
    @Expose
    private TeamMembers team;


    /**
     *
     * @return
     * The uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     *
     * @param uuid
     * The uuid
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     *
     * @return
     * The amount
     */
    public String getAmount() {
        return amount;
    }

    /**
     *
     * @param amount
     * The amount
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     *
     * @return
     * The eligibleAmount
     */
    public String getEligibleAmount() {
        return eligibleAmount;
    }

    /**
     *
     * @param eligibleAmount
     * The eligible_amount
     */
    public void setEligibleAmount(String eligibleAmount) {
        this.eligibleAmount = eligibleAmount;
    }

    /**
     *
     * @return
     * The approvedAmount
     */
    public String getApprovedAmount() {
        return approvedAmount;
    }

    /**
     *
     * @param approvedAmount
     * The approved_amount
     */
    public void setApprovedAmount(String approvedAmount) {
        this.approvedAmount = approvedAmount;
    }

    /**
     *
     * @return
     * The interestRate
     */
    public String getInterestRate() {
        return interestRate;
    }

    /**
     *
     * @param interestRate
     * The interest_rate
     */
    public void setInterestRate(String interestRate) {
        this.interestRate = interestRate;
    }
    /**
     *
     * @return
     * The emi
     */
    public String getEmi() {
        return emi;
    }

    /**
     *
     * @param emi
     * The emi
     */
    public void setEmi(String emi) {
        this.emi = emi;
    }

    /**
     *
     * @return
     * The appid
     */
    public String getAppid() {
        return appid;
    }

    /**
     *
     * @param appid
     * The appid
     */
    public void setAppid(String appid) {
        this.appid = appid;
    }

    /**
     *
     * @return
     * The bank
     */
    public Bank getBank() {
        return bank;
    }

    /**
     *
     * @param bank
     * The bank
     */
    public void setBank(Bank bank) {
        this.bank = bank;
    }

    /**
     *
     * @return
     * The type
     */
    public Type getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     *
     * @return
     * The agent
     */
    public Agent getAgent() {
        return agent;
    }

    /**
     *
     * @param agent
     * The agent
     */
    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public PosessionDate getAppliedOn() {
        return appliedOn;
    }

    public void setAppliedOn(PosessionDate appliedOn) {
        this.appliedOn = appliedOn;
    }

    public PosessionDate getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(PosessionDate approvalDate) {
        this.approvalDate = approvalDate;
    }

    public PosessionDate getEmiStartDate() {
        return emiStartDate;
    }

    public void setEmiStartDate(PosessionDate emiStartDate) {
        this.emiStartDate = emiStartDate;
    }

    public CreatedAt getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(CreatedAt createdAt) {
        this.createdAt = createdAt;
    }

    public UpdatedAt getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(UpdatedAt updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public CustomersLoan getCustomer() {
        return customer;
    }

    public void setCustomer(CustomersLoan customer) {
        this.customer = customer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public History getHistory() {
        return history;
    }

    public void setHistory(History history) {
        this.history = history;
    }

    public Attachments getAttachments() {
        return attachments;
    }

    public void setAttachments(Attachments attachments) {
        this.attachments = attachments;
    }

    public TotalTat getTotalTat() {
        return totalTat;
    }

    public void setTotalTat(TotalTat totalTat) {
        this.totalTat = totalTat;
    }

    public LoanStatusTat getLoanStatusTat() {
        return loanStatusTat;
    }

    public void setLoanStatusTat(LoanStatusTat loanStatusTat) {
        this.loanStatusTat = loanStatusTat;
    }

    public TeamMembers getTeam() {
        return team;
    }

    public void setTeam(TeamMembers team) {
        this.team = team;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uuid);
        dest.writeString(this.amount);
        dest.writeString(this.eligibleAmount);
        dest.writeString(this.approvedAmount);
        dest.writeString(this.interestRate);
        dest.writeString(this.emi);
        dest.writeString(this.appid);
        dest.writeParcelable(this.bank, 0);
        dest.writeParcelable(this.type, 0);
        dest.writeParcelable(this.agent, 0);
        dest.writeParcelable(this.appliedOn, 0);
        dest.writeParcelable(this.approvalDate, 0);
        dest.writeParcelable(this.emiStartDate, 0);
        dest.writeParcelable(this.createdAt, 0);
        dest.writeParcelable(this.updatedAt, 0);
        dest.writeParcelable(this.status, 0);
        dest.writeParcelable(this.customer, 0);
        dest.writeParcelable(this.user, 0);
        dest.writeParcelable(this.history, 0);
        dest.writeParcelable(this.attachments, 0);
        dest.writeParcelable(this.totalTat, 0);
        dest.writeParcelable(this.loanStatusTat, 0);
        dest.writeParcelable(this.team, 0);
    }

    public LoansData() {}

    protected LoansData(Parcel in) {
        this.uuid = in.readString();
        this.amount = in.readString();
        this.eligibleAmount = in.readString();
        this.approvedAmount = in.readString();
        this.interestRate = in.readString();
        this.emi = in.readString();
        this.appid = in.readString();
        this.bank = in.readParcelable(Bank.class.getClassLoader());
        this.type = in.readParcelable(Type.class.getClassLoader());
        this.agent = in.readParcelable(Agent.class.getClassLoader());
        this.appliedOn = in.readParcelable(PosessionDate.class.getClassLoader());
        this.approvalDate = in.readParcelable(PosessionDate.class.getClassLoader());
        this.emiStartDate = in.readParcelable(PosessionDate.class.getClassLoader());
        this.createdAt = in.readParcelable(CreatedAt.class.getClassLoader());
        this.updatedAt = in.readParcelable(UpdatedAt.class.getClassLoader());
        this.status = in.readParcelable(Status.class.getClassLoader());
        this.customer = in.readParcelable(CustomersLoan.class.getClassLoader());
        this.user = in.readParcelable(User.class.getClassLoader());
        this.history = in.readParcelable(History.class.getClassLoader());
        this.attachments = in.readParcelable(Attachments.class.getClassLoader());
        this.totalTat = in.readParcelable(TotalTat.class.getClassLoader());
        this.loanStatusTat = in.readParcelable(TotalTat.class.getClassLoader());
        this.team = in.readParcelable(TeamMembers.class.getClassLoader());
    }


    public static final Creator<LoansData> CREATOR = new Creator<LoansData>() {
        public LoansData createFromParcel(Parcel source) {
            return new LoansData(source);
        }

        public LoansData[] newArray(int size) {
            return new LoansData[size];
        }
    };
}
