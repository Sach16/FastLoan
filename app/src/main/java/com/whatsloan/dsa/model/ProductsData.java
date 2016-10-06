package com.whatsloan.dsa.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by S.K. Pissay on 5/5/16.
 */
public class ProductsData implements Parcelable {

    @SerializedName("bank_id")
    @Expose
    private String bankId;
    /*@SerializedName("product_id")
    @Expose
    private Object productId;*/
    @SerializedName("created_at")
    @Expose
    private CreatedAt createdAt;
    @SerializedName("updated_at")
    @Expose
    private UpdatedAt updatedAt;
    @SerializedName("attachments")
    @Expose
    private Attachments attachments;
    @SerializedName("addresses")
    @Expose
    private Addresses addresses;

    /**
     *
     * @return
     * The bankId
     */
    public String getBankId() {
        return bankId;
    }

    /**
     *
     * @param bankId
     * The bank_id
     */
    public void setBankId(String bankId) {
        this.bankId = bankId;
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
     * The attachments
     */
    public Attachments getAttachments() {
        return attachments;
    }

    /**
     *
     * @param attachments
     * The attachments
     */
    public void setAttachments(Attachments attachments) {
        this.attachments = attachments;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.bankId);
        dest.writeParcelable(this.createdAt, 0);
        dest.writeParcelable(this.updatedAt, 0);
        dest.writeParcelable(this.attachments, 0);
        dest.writeParcelable(this.addresses, 0);
    }

    public ProductsData() {}

    protected ProductsData(Parcel in) {
        this.bankId = in.readString();
        this.createdAt = in.readParcelable(CreatedAt.class.getClassLoader());
        this.updatedAt = in.readParcelable(UpdatedAt.class.getClassLoader());
        this.attachments = in.readParcelable(Attachments.class.getClassLoader());
        this.addresses = in.readParcelable(Addresses.class.getClassLoader());
    }

    public static final Creator<ProductsData> CREATOR = new Creator<ProductsData>() {
        public ProductsData createFromParcel(Parcel source) {
            return new ProductsData(source);
        }

        public ProductsData[] newArray(int size) {
            return new ProductsData[size];
        }
    };
}