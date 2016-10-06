package com.whatsloan.dsa.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by S.K. Pissay on 9/5/16.
 */
public class CustomersLoan implements Parcelable{

    @SerializedName("data")
    @Expose
    private CustomersData data;

    /**
     *
     * @return
     * The data
     */
    public CustomersData getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(CustomersData data) {
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

    public CustomersLoan() {}

    protected CustomersLoan(Parcel in) {
        this.data = in.readParcelable(CustomersData.class.getClassLoader());
    }

    public static final Creator<CustomersLoan> CREATOR = new Creator<CustomersLoan>() {
        public CustomersLoan createFromParcel(Parcel source) {
            return new CustomersLoan(source);
        }

        public CustomersLoan[] newArray(int size) {
            return new CustomersLoan[size];
        }
    };
}