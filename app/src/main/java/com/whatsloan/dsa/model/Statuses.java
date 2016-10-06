package com.whatsloan.dsa.model;

/**
 * Created by S.K. Pissay on 14/4/16.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Statuses implements Parcelable {

    @SerializedName("data")
    @Expose
    private List<StatusData> data = new ArrayList<StatusData>();

    /**
     *
     * @return
     * The data
     */
    public List<StatusData> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<StatusData> data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.data);
    }

    public Statuses() {}

    protected Statuses(Parcel in) {
        in.readList(this.data, StatusData.class.getClassLoader());

    }

    public static final Creator<Statuses> CREATOR = new Creator<Statuses>() {
        public Statuses createFromParcel(Parcel source) {
            return new Statuses(source);
        }

        public Statuses[] newArray(int size) {
            return new Statuses[size];
        }
    };
}