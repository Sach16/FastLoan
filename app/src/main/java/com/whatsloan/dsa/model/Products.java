package com.whatsloan.dsa.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by S.K. Pissay on 5/5/16.
 */
public class Products implements Parcelable{

    @SerializedName("data")
    @Expose
    private List<ProductsData> data = new ArrayList<ProductsData>();
    @SerializedName("meta")
    @Expose
    private Meta meta;

    /**
     *
     * @return
     * The data
     */
    public List<ProductsData> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<ProductsData> data) {
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

    public Products() {}

    protected Products(Parcel in) {
        in.readList(this.data, ProductsData.class.getClassLoader());
        this.meta = in.readParcelable(Meta.class.getClassLoader());
    }

    public static final Creator<Products> CREATOR = new Creator<Products>() {
        public Products createFromParcel(Parcel source) {
            return new Products(source);
        }

        public Products[] newArray(int size) {
            return new Products[size];
        }
    };
}