package com.whatsloan.dsa.model;

/**
 * Created by S.K. Pissay on 30/3/16.
 */
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Addresses implements Parcelable{

    @SerializedName("data")
    @Expose
    private List<AddressesData> data = new ArrayList<AddressesData>();

    /**
     *
     * @return
     * The data
     */
    public List<AddressesData> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<AddressesData> data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.data);
    }

    public Addresses() {}

    protected Addresses(Parcel in) {
        in.readList(this.data, AddressesData.class.getClassLoader());
    }

    public static final Creator<Addresses> CREATOR = new Creator<Addresses>() {
        public Addresses createFromParcel(Parcel source) {
            return new Addresses(source);
        }

        public Addresses[] newArray(int size) {
            return new Addresses[size];
        }
    };
}
