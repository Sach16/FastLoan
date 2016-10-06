package com.whatsloan.dsa.model;

/**
 * Created by S.K. Pissay on 11/4/16.
 */
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AttendanceMemberData implements Parcelable{

    @SerializedName("isPresent")
    @Expose
    private Integer isPresent;
    @SerializedName("day")
    @Expose
    private Integer day;

    /**
     *
     * @return
     * The isPresent
     */
    public Integer getIsPresent() {
        return isPresent;
    }

    /**
     *
     * @param isPresent
     * The isPresent
     */
    public void setIsPresent(Integer isPresent) {
        this.isPresent = isPresent;
    }

    /**
     *
     * @return
     * The day
     */
    public Integer getDay() {
        return day;
    }

    /**
     *
     * @param day
     * The day
     */
    public void setDay(Integer day) {
        this.day = day;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.isPresent);
        dest.writeInt(this.day);
    }

    protected AttendanceMemberData(Parcel in) {
        this.isPresent = in.readInt();
        this.day = in.readInt();
    }

    public static final Creator<AttendanceMemberData> CREATOR = new Creator<AttendanceMemberData>() {
        public AttendanceMemberData createFromParcel(Parcel source) {
            return new AttendanceMemberData(source);
        }

        public AttendanceMemberData[] newArray(int size) {
            return new AttendanceMemberData[size];
        }
    };
}