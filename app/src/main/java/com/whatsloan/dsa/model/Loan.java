package com.whatsloan.dsa.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by S.K. Pissay  on 6/5/16.
 */
public class Loan implements Parcelable {
    @SerializedName("data")
    @Expose
    private LoansData data;

    /**
     * @return The data
     */
    public LoansData getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(LoansData data) {
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

    public Loan() {
    }

    protected Loan(Parcel in) {
        this.data = in.readParcelable(LoansData.class.getClassLoader());
    }

    public static final Parcelable.Creator<Loan> CREATOR = new Parcelable.Creator<Loan>() {
        public Loan createFromParcel(Parcel source) {
            return new Loan(source);
        }

        public Loan[] newArray(int size) {
            return new Loan[size];
        }
    };
}