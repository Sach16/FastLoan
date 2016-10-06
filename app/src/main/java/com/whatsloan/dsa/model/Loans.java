package com.whatsloan.dsa.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by S.K. Pissay on 23/4/16.
 */

public class Loans implements Parcelable{

    @SerializedName("data")
    @Expose
    private List<LoansData> data = new ArrayList<LoansData>();
    @SerializedName("meta")
    @Expose
    private Meta meta;

    /**
     *
     * @return
     * The data
     */
    public List<LoansData> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<LoansData> data) {
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

    public Loans() {}

    protected Loans(Parcel in) {
        in.readList(this.data, LoansData.class.getClassLoader());
        this.meta = in.readParcelable(Meta.class.getClassLoader());
    }

    public static final Creator<Loans> CREATOR = new Creator<Loans>() {
        public Loans createFromParcel(Parcel source) {
            return new Loans(source);
        }

        public Loans[] newArray(int size) {
            return new Loans[size];
        }
    };
}
