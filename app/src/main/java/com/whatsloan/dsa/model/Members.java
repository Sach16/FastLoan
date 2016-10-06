package com.whatsloan.dsa.model;

/**
 * Created by S.K. Pissay on 1/4/16.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Members implements Parcelable{

    @SerializedName("data")
    @Expose
    private List<MembersData> data = new ArrayList<MembersData>();

    /**
     *
     * @return
     * The data
     */
    public List<MembersData> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<MembersData> data) {
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

    public Members() {}

    protected Members(Parcel in) {
        in.readList(this.data, MembersData.class.getClassLoader());
    }

    public static final Creator<Members> CREATOR = new Creator<Members>() {
        public Members createFromParcel(Parcel source) {
            return new Members(source);
        }

        public Members[] newArray(int size) {
            return new Members[size];
        }
    };
}