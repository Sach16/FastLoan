package com.whatsloan.dsa.model;

/**
 * Created by S.K. Pissay on 23/4/16.
 */
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Customers implements Parcelable{

    @SerializedName("data")
    @Expose
    private List<CustomersData> data = new ArrayList<CustomersData>();
    @SerializedName("meta")
    @Expose
    private Meta meta;

    /**
     *
     * @return
     * The data
     */
    public List<CustomersData> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<CustomersData> data) {
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

    public Customers() {}

    protected Customers(Parcel in) {
        in.readList(this.data, CustomersData.class.getClassLoader());
        this.meta = in.readParcelable(Meta.class.getClassLoader());
    }

    public static final Creator<Customers> CREATOR = new Creator<Customers>() {
        public Customers createFromParcel(Parcel source) {
            return new Customers(source);
        }

        public Customers[] newArray(int size) {
            return new Customers[size];
        }
    };

}