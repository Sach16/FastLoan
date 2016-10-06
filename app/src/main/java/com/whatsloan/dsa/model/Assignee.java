package com.whatsloan.dsa.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by S.K. Pissay on 18/4/16.
 */
public class Assignee implements Parcelable {

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

    public Assignee() {}

    protected Assignee(Parcel in) {
        this.data = in.readParcelable(OwnerData.class.getClassLoader());
    }

    public static final Creator<Assignee> CREATOR = new Creator<Assignee>() {
        public Assignee createFromParcel(Parcel source) {
            return new Assignee(source);
        }

        public Assignee[] newArray(int size) {
            return new Assignee[size];
        }
    };
}