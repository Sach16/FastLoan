package com.whatsloan.dsa.model;

/**
 * Created by S.K. Pissay on 30/3/16.
 */
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdatedAt implements Parcelable {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("timezone_type")
    @Expose
    private Integer timezoneType;
    @SerializedName("timezone")
    @Expose
    private String timezone;

    public UpdatedAt() {}

    protected UpdatedAt(Parcel in) {
        this.date = in.readString();
        this.timezoneType = in.readInt();
        this.timezone = in.readString();
    }

    /**
     *
     * @return
     * The date
     */
    public String getDate() {
        return date;
    }

    /**
     *
     * @param date
     * The date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     *
     * @return
     * The timezoneType
     */
    public Integer getTimezoneType() {
        return timezoneType;
    }

    /**
     *
     * @param timezoneType
     * The timezone_type
     */
    public void setTimezoneType(Integer timezoneType) {
        this.timezoneType = timezoneType;
    }

    /**
     *
     * @return
     * The timezone
     */
    public String getTimezone() {
        return timezone;
    }

    /**
     *
     * @param timezone
     * The timezone
     */
    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.date);
        dest.writeInt(this.timezoneType);
        dest.writeString(this.timezone);
    }

    public static final Creator<UpdatedAt> CREATOR = new Creator<UpdatedAt>() {
        public UpdatedAt createFromParcel(Parcel source) {
            return new UpdatedAt(source);
        }

        public UpdatedAt[] newArray(int size) {
            return new UpdatedAt[size];
        }
    };
}
