package com.whatsloan.dsa.model;

/**
 * Created by S.K. Pissay on 30/3/16.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LeadsDatum implements Parcelable{

    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("existing_loan_emi")
    @Expose
    private Integer existingLoanEmi;
    @SerializedName("company_name")
    @Expose
    private String companyName;
    @SerializedName("created_by")
    @Expose
    private String createdBy;
    @SerializedName("created_at")
    @Expose
    private CreatedAt createdAt;
    @SerializedName("updated_at")
    @Expose
    private UpdatedAt updatedAt;
    @SerializedName("links")
    @Expose
    private Links links;
    @SerializedName("addresses")
    @Expose
    private Addresses addresses;
    @SerializedName("source")
    @Expose
    private Source sources;
    @SerializedName("type")
    @Expose
    private Type types;
    @SerializedName("assignee")
    @Expose
    private Assignee assignee;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("loan")
    @Expose
    private Loan loan;
    @SerializedName("loan_statuses")
    @Expose
    private Status status;
    @SerializedName("customer")
    @Expose
    private CustomersLoan customer;
    @SerializedName("agent")
    @Expose
    private Agent agent;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     *
     * @return
     * The existingLoanEmi
     */
    public Integer getExistingLoanEmi() {
        return existingLoanEmi;
    }

    /**
     *
     * @param existingLoanEmi
     * The existing_loan_emi
     */
    public void setExistingLoanEmi(Integer existingLoanEmi) {
        this.existingLoanEmi = existingLoanEmi;
    }

    /**
     *
     * @return
     * The companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     *
     * @param companyName
     * The company_name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     *
     * @return
     * The createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     *
     * @param createdBy
     * The created_by
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
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
     * The links
     */
    public Links getLinks() {
        return links;
    }

    /**
     *
     * @param links
     * The links
     */
    public void setLinks(Links links) {
        this.links = links;
    }

    /**
     *
     * @return
     * The addresses
     */
    public Addresses getAddresses() {
        return addresses;
    }

    /**
     *
     * @param addresses
     * The addresses
     */
    public void setAddresses(Addresses addresses) {
        this.addresses = addresses;
    }

    /**
     *
     * @return
     * The sources
     */
    public Source getSources() {
        return sources;
    }

    /**
     *
     * @param sources
     * The sources
     */
    public void setSources(Source sources) {
        this.sources = sources;
    }

    /**
     *
     * @return
     * The types
     */
    public Type getTypes() {
        return types;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    /**
     *
     * @param types
     * The types
     */
    public void setTypes(Type types) {
        this.types = types;
    }

    public Assignee getAssignee() {
        return assignee;
    }

    public void setAssignee(Assignee assignee) {
        this.assignee = assignee;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uuid);
        dest.writeString(this.amount);
        dest.writeInt(this.existingLoanEmi != null ? this.existingLoanEmi : 0);
        dest.writeString(this.companyName);
        dest.writeString(this.createdBy);
        dest.writeParcelable(this.createdAt, 0);
        dest.writeParcelable(this.updatedAt, 0);
        dest.writeParcelable(this.links, 0);
        dest.writeParcelable(this.addresses, 0);
        dest.writeParcelable(this.sources, 0);
        dest.writeParcelable(this.types, 0);
        dest.writeParcelable(this.assignee, 0);
        dest.writeParcelable(this.user, 0);
        dest.writeParcelable(this.loan, 0);
        dest.writeParcelable(this.customer, 0);
        dest.writeParcelable(this.agent, 0);
    }

    protected LeadsDatum(Parcel in) {
        this.uuid = in.readString();
        this.amount = in.readString();
        this.existingLoanEmi = in.readInt();
        this.companyName = in.readString();
        this.createdBy = in.readString();
        this.createdAt = in.readParcelable(CreatedAt.class.getClassLoader());
        this.updatedAt = in.readParcelable(UpdatedAt.class.getClassLoader());
        this.links = in.readParcelable(Links.class.getClassLoader());
        this.addresses = in.readParcelable(Addresses.class.getClassLoader());
        this.sources = in.readParcelable(Source.class.getClassLoader());
        this.types = in.readParcelable(Type.class.getClassLoader());
        this.assignee = in.readParcelable(Assignee.class.getClassLoader());
        this.user = in.readParcelable(User.class.getClassLoader());
        this.loan = in.readParcelable(Loan.class.getClassLoader());
        this.status = in.readParcelable(Status.class.getClassLoader());
        this.customer = in.readParcelable(CustomersLoan.class.getClassLoader());
        this.agent = in.readParcelable(Loan.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LeadsDatum> CREATOR = new Creator<LeadsDatum>() {
        public LeadsDatum createFromParcel(Parcel source) {
            return new LeadsDatum(source);
        }

        public LeadsDatum[] newArray(int size) {
            return new LeadsDatum[size];
        }
    };
}
