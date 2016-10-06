package com.whatsloan.dsa.model;

/**
 * Created by S.K. Pissay on 1/4/16.
 */
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Owner implements Parcelable{

    @SerializedName("data")
    @Expose
    private OwnerData data;

    /**
     *
     * @return
     * The data
     */
    public OwnerData getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(OwnerData data) {
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

    public Owner() {}

    protected Owner(Parcel in) {
        this.data = in.readParcelable(OwnerData.class.getClassLoader());
    }

    public static final Creator<Owner> CREATOR = new Creator<Owner>() {
        public Owner createFromParcel(Parcel source) {
            return new Owner(source);
        }

        public Owner[] newArray(int size) {
            return new Owner[size];
        }
    };
}