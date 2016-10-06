package com.whatsloan.dsa.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by S.K. Pissay on 27/6/16.
 */
public class Rating implements Parcelable{

    @SerializedName("data")
    @Expose
    private RatingData data;

    /**
     *
     * @return
     * The data
     */
    public RatingData getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(RatingData data) {
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

    protected Rating(Parcel in) {
        this.data = in.readParcelable(RatingData.class.getClassLoader());
    }

    public static final Parcelable.Creator<Rating> CREATOR = new Parcelable.Creator<Rating>() {
        public Rating createFromParcel(Parcel source) {
            return new Rating(source);
        }

        public Rating[] newArray(int size) {
            return new Rating[size];
        }
    };
}
