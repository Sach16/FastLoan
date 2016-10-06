package com.whatsloan.dsa.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by S.K. Pissay on 28/5/16.
 */
public class HistoryData implements Parcelable{
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
    @SerializedName("emi")
    @Expose
    private String emi;
    @SerializedName("appid")
    @Expose
    private String appid;
    @SerializedName("interest_rate")
    @Expose
    private String interestRate;
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
    @SerializedName("status")
    @Expose
    private Status status;

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
     * The appliedOn
     */
    public PosessionDate getAppliedOn() {
        return appliedOn;
    }

    /**
     *
     * @param appliedOn
     * The applied_on
     */
    public void setAppliedOn(PosessionDate appliedOn) {
        this.appliedOn = appliedOn;
    }

    /**
     *
     * @return
     * The approvalDate
     */
    public PosessionDate getApprovalDate() {
        return approvalDate;
    }

    /**
     *
     * @param approvalDate
     * The approval_date
     */
    public void setApprovalDate(PosessionDate approvalDate) {
        this.approvalDate = approvalDate;
    }

    /**
     *
     * @return
     * The emiStartDate
     */
    public PosessionDate getEmiStartDate() {
        return emiStartDate;
    }

    /**
     *
     * @param emiStartDate
     * The emi_start_date
     */
    public void setEmiStartDate(PosessionDate emiStartDate) {
        this.emiStartDate = emiStartDate;
    }

    /**
     *
     * @return
     * The createdAt
     */
    public CreatedAt getCreatedAt() {
        return createdAt;
    }

    /**
     *
     * @param createdAt
     * The created_at
     */
    public void setCreatedAt(CreatedAt createdAt) {
        this.createdAt = createdAt;
    }

    /**
     *
     * @return
     * The updatedAt
     */
    public UpdatedAt getUpdatedAt() {
        return updatedAt;
    }

    /**
     *
     * @param updatedAt
     * The updated_at
     */
    public void setUpdatedAt(UpdatedAt updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     *
     * @return
     * The status
     */
    public Status getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    public HistoryData() {}

    protected HistoryData(Parcel in) {
        this.uuid = in.readString();
        this.amount = in.readString();
        this.eligibleAmount = in.readString();
        this.approvedAmount = in.readString();
        this.emi = in.readString();
        this.appid = in.readString();
        this.interestRate = in.readString();
        this.appliedOn = in.readParcelable(PosessionDate.class.getClassLoader());
        this.approvalDate = in.readParcelable(PosessionDate.class.getClassLoader());
        this.emiStartDate = in.readParcelable(PosessionDate.class.getClassLoader());
        this.createdAt = in.readParcelable(CreatedAt.class.getClassLoader());
        this.updatedAt = in.readParcelable(UpdatedAt.class.getClassLoader());
        this.status = in.readParcelable(Status.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uuid);
        dest.writeString(this.amount);
        dest.writeString(this.eligibleAmount);
        dest.writeString(this.approvedAmount);
        dest.writeString(this.emi);
        dest.writeString(this.appid);
        dest.writeString(this.interestRate);
        dest.writeParcelable(this.appliedOn, 0);
        dest.writeParcelable(this.approvalDate, 0);
        dest.writeParcelable(this.emiStartDate, 0);
        dest.writeParcelable(this.createdAt, 0);
        dest.writeParcelable(this.updatedAt, 0);
        dest.writeParcelable(this.status, 0);
    }

    public static final Creator<HistoryData> CREATOR = new Creator<HistoryData>() {
        public HistoryData createFromParcel(Parcel source) {
            return new HistoryData(source);
        }

        public HistoryData[] newArray(int size) {
            return new HistoryData[size];
        }
    };
}