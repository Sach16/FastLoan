package com.whatsloan.dsa.model;

/**
 * Created by S.K. Pissay on 13/4/16.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Banks implements Parcelable{

    @SerializedName("data")
    @Expose
    private List<BanksData> data = new ArrayList<BanksData>();

    /**
     *
     * @return
     * The data
     */
    public List<BanksData> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<BanksData> data) {
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

    public Banks() {}

    protected Banks(Parcel in) {
        in.readList(this.data, BanksData.class.getClassLoader());
    }

    public static final Creator<Banks> CREATOR = new Creator<Banks>() {
        public Banks createFromParcel(Parcel source) {
            return new Banks(source);
        }

        public Banks[] newArray(int size) {
            return new Banks[size];
        }
    };
}