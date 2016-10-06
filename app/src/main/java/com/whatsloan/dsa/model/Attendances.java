package com.whatsloan.dsa.model;

/**
 * Created by S.K. Pissay on 6/4/16.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Attendances implements Parcelable {

    @SerializedName("data")
    @Expose
    private List<AttendanceData> data = new ArrayList<AttendanceData>();
    @SerializedName("meta")
    @Expose
    private Meta meta;

    /**
     *
     * @return
     * The data
     */
    public List<AttendanceData> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<AttendanceData> data) {
        this.data = data;
    }

    /**
     *
     * @return
     * The meta
     */
    public Meta getMeta() {
        return meta;
    }

    /**
     *
     * @param meta
     * The meta
     */
    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.data);
        dest.writeParcelable(this.meta, 0);
    }

    public Attendances() {}

    protected Attendances(Parcel in) {
        in.readList(this.data, AttendanceData.class.getClassLoader());
        this.meta = in.readParcelable(Meta.class.getClassLoader());
    }

    public static final Creator<Attendances> CREATOR = new Creator<Attendances>() {
        public Attendances createFromParcel(Parcel source) {
            return new Attendances(source);
        }

        public Attendances[] newArray(int size) {
            return new Attendances[size];
        }
    };
}