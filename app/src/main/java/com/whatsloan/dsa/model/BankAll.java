package com.whatsloan.dsa.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by S.K. Pissay on 14/4/16.
 */
public class BankAll implements Parcelable {

    @SerializedName("data")
    @Expose
    private List<Bank> banks = new ArrayList<Bank>();

    @SerializedName("meta")
    @Expose
    private Meta meta;

    /**
     *
     * @return
     * The banks
     */
    public List<Bank> getBanks() {
        return banks;
    }

    /**
     *
     * @param banks
     * The banks
     */
    public void setBanks(List<Bank> banks) {
        this.banks = banks;
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
        dest.writeList(this.banks);
        dest.writeParcelable(this.meta, 0);
    }

    public BankAll() {}

    protected BankAll(Parcel in) {
        in.readList(this.banks, Bank.class.getClassLoader());
        this.meta = in.readParcelable(Meta.class.getClassLoader());
    }

    public static final Creator<BankAll> CREATOR = new Creator<BankAll>() {
        public BankAll createFromParcel(Parcel source) {
            return new BankAll(source);
        }

        public BankAll[] newArray(int size) {
            return new BankAll[size];
        }
    };
}