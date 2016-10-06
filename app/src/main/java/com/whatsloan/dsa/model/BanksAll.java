package com.whatsloan.dsa.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by S.K. Pissay on 27/4/16.
 */
public class BanksAll implements Parcelable {
    @SerializedName("data")
    @Expose
    private List<BanksData> banks = new ArrayList<BanksData>();

    @SerializedName("meta")
    @Expose
    private Meta meta;

    /**
     * @return The banks
     */
    public List<BanksData> getBanks() {
        return banks;
    }

    /**
     * @param banks The banks
     */
    public void setBanks(List<BanksData> banks) {
        this.banks = banks;
    }

    /**
     * @return The meta
     */
    public Meta getMeta() {
        return meta;
    }

    /**
     * @param meta The meta
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
        dest.writeList(this.banks);
        dest.writeParcelable(this.meta, 0);
    }

    public BanksAll() {
    }

    protected BanksAll(Parcel in) {
        in.readList(this.banks, BanksData.class.getClassLoader());
        this.meta = in.readParcelable(Meta.class.getClassLoader());
    }

    public static final Parcelable.Creator<BanksAll> CREATOR = new Parcelable.Creator<BanksAll>() {
        public BanksAll createFromParcel(Parcel source) {
            return new BanksAll(source);
        }

        public BanksAll[] newArray(int size) {
            return new BanksAll[size];
        }
    };
}