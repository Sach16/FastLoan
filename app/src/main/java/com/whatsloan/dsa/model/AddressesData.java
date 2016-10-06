package com.whatsloan.dsa.model;

/**
 * Created by S.K. Pissay on 30/3/16.
 */
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddressesData implements Parcelable{

    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("alpha_street")
    @Expose
    private String alphaStreet;
    @SerializedName("beta_street")
    @Expose
    private String betaStreet;
    @SerializedName("city")
    @Expose
    private City city;
    @SerializedName("city_id")
    @Expose
    private Integer cityId;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("zip")
    @Expose
    private Integer zip;
    @SerializedName("created_at")
    @Expose
    private CreatedAt createdAt;
    @SerializedName("updated_at")
    @Expose
    private UpdatedAt updatedAt;

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
     * The email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     * The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     * The phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     *
     * @param phone
     * The phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     *
     * @return
     * The alphaStreet
     */
    public String getAlphaStreet() {
        return alphaStreet;
    }

    /**
     *
     * @param alphaStreet
     * The alpha_street
     */
    public void setAlphaStreet(String alphaStreet) {
        this.alphaStreet = alphaStreet;
    }

    /**
     *
     * @return
     * The betaStreet
     */
    public String getBetaStreet() {
        return betaStreet;
    }

    /**
     *
     * @param betaStreet
     * The beta_street
     */
    public void setBetaStreet(String betaStreet) {
        this.betaStreet = betaStreet;
    }

    /**
     *
     * @return
     * The city
     */
    public City getCity() {
        return city;
    }

    /**
     *
     * @param city
     * The city
     */
    public void setCity(City city) {
        this.city = city;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    /**
     *
     * @return
     * The state
     */
    public String getState() {
        return state;
    }

    /**
     *
     * @param state
     * The state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     *
     * @return
     * The country
     */
    public String getCountry() {
        return country;
    }

    /**
     *
     * @param country
     * The country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     *
     * @return
     * The zip
     */
    public Integer getZip() {
        return zip;
    }

    /**
     *
     * @param zip
     * The zip
     */
    public void setZip(Integer zip) {
        this.zip = zip;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uuid);
        dest.writeString(this.email);
        dest.writeString(this.phone);
        dest.writeString(this.alphaStreet);
        dest.writeString(this.betaStreet);
        dest.writeParcelable(this.city, 0);
        dest.writeInt(this.cityId);
        dest.writeString(this.state);
        dest.writeString(this.country);
        dest.writeInt(this.zip);
        dest.writeParcelable(this.createdAt, 0);
        dest.writeParcelable(this.updatedAt, 0);
    }

    public AddressesData(){}

    protected AddressesData(Parcel in) {
        this.uuid = in.readString();
        this.email = in.readString();
        this.phone = in.readString();
        this.alphaStreet = in.readString();
        this.betaStreet = in.readString();
        this.city = in.readParcelable(City.class.getClassLoader());
        this.cityId  = in.readInt();
        this.state = in.readString();
        this.country = in.readString();
        this.zip = in.readInt();
        this.createdAt = in.readParcelable(CreatedAt.class.getClassLoader());
        this.updatedAt = in.readParcelable(UpdatedAt.class.getClassLoader());
    }

    public static final Creator<AddressesData> CREATOR = new Creator<AddressesData>() {
        public AddressesData createFromParcel(Parcel source) {
            return new AddressesData(source);
        }

        public AddressesData[] newArray(int size) {
            return new AddressesData[size];
        }
    };
}
