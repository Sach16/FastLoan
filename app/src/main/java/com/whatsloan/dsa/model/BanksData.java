package com.whatsloan.dsa.model;

/**
 * Created by S.K. Pissay on 13/4/16.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BanksData implements Parcelable{

    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("branch")
    @Expose
    private String branch;
    @SerializedName("ifsccode")
    @Expose
    private String ifsccode;
    @SerializedName("address")
    @Expose
    private Address address;
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
     * The branch
     */
    public String getBranch() {
        return branch;
    }

    /**
     *
     * @param branch
     * The branch
     */
    public void setBranch(String branch) {
        this.branch = branch;
    }

    /**
     *
     * @return
     * The ifsccode
     */
    public String getIfsccode() {
        return ifsccode;
    }

    /**
     *
     * @param ifsccode
     * The ifsccode
     */
    public void setIfsccode(String ifsccode) {
        this.ifsccode = ifsccode;
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
        dest.writeString(this.branch);
        dest.writeString(this.ifsccode);
        dest.writeParcelable(this.address, 0);
        dest.writeParcelable(this.attachments, 0);
    }

    public BanksData() {}

    protected BanksData(Parcel in) {
        this.uuid = in.readString();
        this.name = in.readString();
        this.branch = in.readString();
        this.ifsccode = in.readString();
        this.address = in.readParcelable(Address.class.getClassLoader());
        this.attachments = in.readParcelable(Attachments.class.getClassLoader());

    }

    public static final Creator<BanksData> CREATOR = new Creator<BanksData>() {
        public BanksData createFromParcel(Parcel source) {
            return new BanksData(source);
        }

        public BanksData[] newArray(int size) {
            return new BanksData[size];
        }
    };
}