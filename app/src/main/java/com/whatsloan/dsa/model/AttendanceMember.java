package com.whatsloan.dsa.model;

/**
 * Created by S.K. Pissay on 11/4/16.
 */
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class AttendanceMember implements Parcelable{

    @SerializedName("data")
    @Expose
    private List<AttendanceMemberData> data = new ArrayList<AttendanceMemberData>();

    /**
     *
     * @return
     * The data
     */
    public List<AttendanceMemberData> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<AttendanceMemberData> data) {
        this.data = data;
    }

    public AttendanceMember() {}

    @Override
    public int describeContents() {
        return 0;
    }

    protected AttendanceMember(Parcel in) {
        in.readList(this.data, AttendanceMemberData.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.data);
    }

    public static final Creator<AttendanceMember> CREATOR = new Creator<AttendanceMember>() {
        public AttendanceMember createFromParcel(Parcel source) {
            return new AttendanceMember(source);
        }

        public AttendanceMember[] newArray(int size) {
            return new AttendanceMember[size];
        }
    };
}