package com.whatsloan.dsa.model;

/**
 * Created by S.K. Pissay on 13/4/16.
 */
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Address implements Parcelable{

    @SerializedName("data")
    @Expose
    private AddressesData data;

    /**
     *
     * @return
     * The data
     */
    public AddressesData getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(AddressesData data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.data, 0);
    }

    protected Address(Parcel in) {
        this.data = in.readParcelable(AddressesData.class.getClassLoader());
    }

    public static final Creator<Address> CREATOR = new Creator<Address>() {
        public Address createFromParcel(Parcel source) {
            return new Address(source);
        }

        public Address[] newArray(int size) {
            return new Address[size];
        }
    };
}
