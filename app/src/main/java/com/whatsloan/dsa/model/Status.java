package com.whatsloan.dsa.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by S.K. Pissay on 14/4/16.
 */
public class Status implements Parcelable{

    @SerializedName("data")
    @Expose
    private StatusData data;

    /**
     *
     * @return
     * The data
     */
    public StatusData getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(StatusData data) {
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

    public Status() {}

    protected Status(Parcel in) {
        this.data = in.readParcelable(StatusData.class.getClassLoader());

    }

    public static final Creator<Status> CREATOR = new Creator<Status>() {
        public Status createFromParcel(Parcel source) {
            return new Status(source);
        }

        public Status[] newArray(int size) {
            return new Status[size];
        }
    };
}
