package com.whatsloan.dsa.model;

/**
 * Created by S.K. Pissay on 13/4/16.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CityAll implements Parcelable{

    @SerializedName("data")
    @Expose
    private List<CityData> data = new ArrayList<CityData>();
    @SerializedName("meta")
    @Expose
    private Meta meta;

    /**
     *
     * @return
     * The data
     */
    public List<CityData> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<CityData> data) {
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
    }

    public CityAll() {}

    protected CityAll(Parcel in) {
        in.readList(this.data, CityData.class.getClassLoader());
    }

    public static final Creator<CityAll> CREATOR = new Creator<CityAll>() {
        public CityAll createFromParcel(Parcel source) {
            return new CityAll(source);
        }

        public CityAll[] newArray(int size) {
            return new CityAll[size];
        }
    };

}
