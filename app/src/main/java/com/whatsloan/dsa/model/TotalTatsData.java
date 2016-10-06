package com.whatsloan.dsa.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by S.K. Pissay on 28/5/16.
 */
public class TotalTatsData implements Parcelable{

    @SerializedName("duration")
    @Expose
    private Integer duration;

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.duration);
    }

    protected TotalTatsData(Parcel in) {
        this.duration = in.readInt();
    }

    public static final Parcelable.Creator<TotalTatsData> CREATOR = new Parcelable.Creator<TotalTatsData>() {
        public TotalTatsData createFromParcel(Parcel source) {
            return new TotalTatsData(source);
        }

        public TotalTatsData[] newArray(int size) {
            return new TotalTatsData[size];
        }
    };
}