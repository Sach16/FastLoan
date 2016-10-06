package com.whatsloan.dsa.model;

/**
 * Created by S.K. Pissay on 30/3/16.
 */
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LeadsSrcDatum implements Parcelable{

    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("created_at")
    @Expose
    private CreatedAt createdAt;
    @SerializedName("updated_at")
    @Expose
    private UpdatedAt updatedAt;
    @SerializedName("referrals")
    @Expose
    private Referrals referrals;

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
     * The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescription(String description) {
        this.description = description;
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
     * The referrals
     */
    public Referrals getReferrals() {
        return referrals;
    }

    /**
     *
     * @param referrals
     * The referrals
     */
    public void setReferrals(Referrals referrals) {
        this.referrals = referrals;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uuid);
        dest.writeString(this.name);
        dest.writeString(this.key);
        dest.writeString(this.description);
        dest.writeParcelable(this.createdAt, 0);
        dest.writeParcelable(this.updatedAt, 0);
        dest.writeParcelable(this.referrals, 0);
    }

    public LeadsSrcDatum() {}

    protected LeadsSrcDatum(Parcel in) {
        this.uuid = in.readString();
        this.name = in.readString();
        this.key = in.readString();
        this.description = in.readString();
        this.createdAt = in.readParcelable(CreatedAt.class.getClassLoader());
        this.updatedAt = in.readParcelable(UpdatedAt.class.getClassLoader());
        this.referrals = in.readParcelable(Referrals.class.getClassLoader());
    }

    public static final Creator<LeadsSrcDatum> CREATOR = new Creator<LeadsSrcDatum>() {
        public LeadsSrcDatum createFromParcel(Parcel source) {
            return new LeadsSrcDatum(source);
        }

        public LeadsSrcDatum[] newArray(int size) {
            return new LeadsSrcDatum[size];
        }
    };
}