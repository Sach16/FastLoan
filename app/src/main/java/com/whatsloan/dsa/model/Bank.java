package com.whatsloan.dsa.model;

/**
 * Created by S.K. Pissay on 13/4/16.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bank implements Parcelable{

    @SerializedName("data")
    @Expose
    private BanksData data;

    /**
     *
     * @return
     * The data
     */
    public BanksData getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(BanksData data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.data , 0);
    }

    protected Bank(Parcel in) {
        this.data = in.readParcelable(BanksData.class.getClassLoader());
    }

    public static final Creator<Bank> CREATOR = new Creator<Bank>() {
        public Bank createFromParcel(Parcel source) {
            return new Bank(source);
        }

        public Bank[] newArray(int size) {
            return new Bank[size];
        }
    };
}