package com.whatsloan.dsa.model;

/**
 * Created by S.K. Pissay on 16/4/16.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Builder implements Parcelable{

    @SerializedName("data")
    @Expose
    private BuilderData data;

    /**
     *
     * @return
     * The data
     */
    public BuilderData getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(BuilderData data) {
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

    protected Builder(Parcel in) {
        this.data = in.readParcelable(BuilderData.class.getClassLoader());
    }

    public static final Creator<Builder> CREATOR = new Creator<Builder>() {
        public Builder createFromParcel(Parcel source) {
            return new Builder(source);
        }

        public Builder[] newArray(int size) {
            return new Builder[size];
        }
    };
}