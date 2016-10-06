package com.whatsloan.dsa.model;

/**
 * Created by S.K. Pissay on 13/4/16.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProjectData implements Parcelable{

    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("builder_name")
    @Expose
    private String builderName;
    @SerializedName("status")
    @Expose
    private Status status;
    @SerializedName("unit_number")
    @Expose
    private Integer unitNumber;
    @SerializedName("possession_date")
    @Expose
    private PosessionDate posessionDate;
    @SerializedName("lsr_start_date")
    @Expose
    private PosessionDate lsrStartDate;
    @SerializedName("lsr_end_date")
    @Expose
    private PosessionDate lsrEndDate;
    @SerializedName("image")
    @Expose
    private String image;
    //TODO ask for int
    /*@SerializedName("is_approved")
    @Expose
    private Boolean isApproved;*/
    @SerializedName("builder")
    @Expose
    private Builder builder;
    @SerializedName("address")
    @Expose
    private Address address;
    @SerializedName("banks")
    @Expose
    private Banks banks;
    @SerializedName("owner")
    @Expose
    private Owner owner;
    @SerializedName("assignee")
    @Expose
    private Assignee assignee;
    @SerializedName("queries")
    @Expose
    private Queries queries;
    @SerializedName("attachments")
    @Expose
    private Attachments attachments;

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
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The builderName
     */
    public String getBuilderName() {
        return builderName;
    }

    /**
     *
     * @param builderName
     * The builder_name
     */
    public void setBuilderName(String builderName) {
        this.builderName = builderName;
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

    /**
     *
     * @return
     * The unitNumber
     */
    public Integer getUnitNumber() {
        return unitNumber;
    }

    /**
     *
     * @param unitNumber
     * The unit_number
     */
    public void setUnitNumber(Integer unitNumber) {
        this.unitNumber = unitNumber;
    }

    /**
     *
     * @return
     * The posessionDate
     */
    public PosessionDate getPosessionDate() {
        return posessionDate;
    }

    /**
     *
     * @param posessionDate
     * The posession_date
     */
    public void setPosessionDate(PosessionDate posessionDate) {
        this.posessionDate = posessionDate;
    }

    public PosessionDate getLsrStartDate() {
        return lsrStartDate;
    }

    public void setLsrStartDate(PosessionDate lsrStartDate) {
        this.lsrStartDate = lsrStartDate;
    }

    public PosessionDate getLsrEndDate() {
        return lsrEndDate;
    }

    public void setLsrEndDate(PosessionDate lsrEndDate) {
        this.lsrEndDate = lsrEndDate;
    }

    /**
     *
     * @return
     * The image
     */
    public String getImage() {
        return image;
    }

    /**
     *
     * @param image
     * The image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     *
     * @return
     * The builder
     */
    public Builder getBuilder() {
        return builder;
    }

    /**
     *
     * @param builder
     * The builder
     */
    public void setBuilder(Builder builder) {
        this.builder = builder;
    }

    /**
     *
     * @return
     * The address
     */
    public Address getAddress() {
        return address;
    }

    /**
     *
     * @param address
     * The address
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     *
     * @return
     * The banks
     */
    public Banks getBanks() {
        return banks;
    }

    /**
     *
     * @param banks
     * The banks
     */
    public void setBanks(Banks banks) {
        this.banks = banks;
    }

    /**
     *
     * @return
     * The owner
     */
    public Owner getOwner() {
        return owner;
    }

    /**
     *
     * @param owner
     * The owner
     */
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Assignee getAssignee() {
        return assignee;
    }

    public void setAssignee(Assignee assignee) {
        this.assignee = assignee;
    }

    /**
     *
     * @return
     * The queries
     */
    public Queries getQueries() {
        return queries;
    }

    /**
     *
     * @param queries
     * The queries
     */
    public void setQueries(Queries queries) {
        this.queries = queries;
    }

    public Attachments getAttachments() {
        return attachments;
    }

    public void setAttachments(Attachments attachments) {
        this.attachments = attachments;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uuid);
        dest.writeString(this.name);
        dest.writeString(this.builderName);
        dest.writeParcelable(this.status, 0);
        dest.writeInt(this.unitNumber != null ? this.unitNumber : 0);
        dest.writeParcelable(this.posessionDate, 0);
        dest.writeParcelable(this.lsrStartDate, 0);
        dest.writeParcelable(this.lsrEndDate, 0);
        dest.writeString(this.image);
        dest.writeParcelable(this.builder, 0);
        dest.writeParcelable(this.address, 0);
        dest.writeParcelable(this.banks, 0);
        dest.writeParcelable(this.owner, 0);
        dest.writeParcelable(this.assignee, 0);
        dest.writeParcelable(this.queries, 0);
        dest.writeParcelable(this.attachments, 0);
    }

    public ProjectData() {}

    protected ProjectData(Parcel in) {
        this.uuid = in.readString();
        this.name = in.readString();
        this.builderName = in.readString();
        this.status = in.readParcelable(Status.class.getClassLoader());
        this.unitNumber = in.readInt();
        this.posessionDate = in.readParcelable(PosessionDate.class.getClassLoader());
        this.lsrStartDate = in.readParcelable(PosessionDate.class.getClassLoader());
        this.lsrEndDate = in.readParcelable(PosessionDate.class.getClassLoader());
        this.image = in.readString();
        this.builder = in.readParcelable(Builder.class.getClassLoader());
        this.address = in.readParcelable(Address.class.getClassLoader());
        this.banks = in.readParcelable(Banks.class.getClassLoader());
        this.owner = in.readParcelable(Owner.class.getClassLoader());
        this.assignee = in.readParcelable(Assignee.class.getClassLoader());
        this.assignee = in.readParcelable(Queries.class.getClassLoader());
        this.attachments = in.readParcelable(Attachments.class.getClassLoader());

    }

    public static final Creator<ProjectData> CREATOR = new Creator<ProjectData>() {
        public ProjectData createFromParcel(Parcel source) {
            return new ProjectData(source);
        }

        public ProjectData[] newArray(int size) {
            return new ProjectData[size];
        }
    };
}