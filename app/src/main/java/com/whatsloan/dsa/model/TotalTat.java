package com.whatsloan.dsa.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by S.K. Pissay on 28/5/16.
 */
public class TotalTat implements Parcelable{
    @SerializedName("data")
    @Expose
    private TotalTatsData data;

    /**
     *
     * @return
     * The data
     */
    public TotalTatsData getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(TotalTatsData data) {
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

    protected TotalTat(Parcel in) {
        this.data = in.readParcelable(TotalTatsData.class.getClassLoader());
    }

    public static final Parcelable.Creator<TotalTat> CREATOR = new Parcelable.Creator<TotalTat>() {
        public TotalTat createFromParcel(Parcel source) {
            return new TotalTat(source);
        }

        public TotalTat[] newArray(int size) {
            return new TotalTat[size];
        }
    };
}