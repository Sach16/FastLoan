package com.whatsloan.dsa.model;

/**
 * Created by S.K. Pissay on 13/4/16.
 */
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class City implements Parcelable{

    @SerializedName("data")
    @Expose
    private CityData data;

    /**
     *
     * @return
     * The data
     */
    public CityData getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(CityData data) {
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

    public City() {}

    protected City(Parcel in) {
        this.data = in.readParcelable(CityData.class.getClassLoader());
    }

    public static final Creator<City> CREATOR = new Creator<City>() {
        public City createFromParcel(Parcel source) {
            return new City(source);
        }

        public City[] newArray(int size) {
            return new City[size];
        }
    };
}