package com.whatsloan.dsa.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by  S.K. Pissay  on 1/6/16.
 */
public class LsrDate implements Parcelable {

    @SerializedName("data")
    @Expose
    private LsrDateData data;

    /**
     *
     * @return
     * The data
     */
    public LsrDateData getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(LsrDateData data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.data , 0);
    }

    protected LsrDate(Parcel in) {
        this.data = in.readParcelable(LsrDateData.class.getClassLoader());
    }

    public static final Creator<LsrDate> CREATOR = new Creator<LsrDate>() {
        public LsrDate createFromParcel(Parcel source) {
            return new LsrDate(source);
        }

        public LsrDate[] newArray(int size) {
            return new LsrDate[size];
        }
    };
}
