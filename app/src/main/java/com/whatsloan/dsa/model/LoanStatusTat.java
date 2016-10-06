package com.whatsloan.dsa.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by S.K. Pissay on 2/6/16.
 */
public class LoanStatusTat implements Parcelable{
    @SerializedName("data")
    @Expose
    private List<LoanStatusTatData> data = new ArrayList<LoanStatusTatData>();

    /**
     *
     * @return
     * The data
     */
    public List<LoanStatusTatData> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<LoanStatusTatData> data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    //Todo remove the (Parcelable) cast below

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.data);
    }

    public LoanStatusTat() {}

    protected LoanStatusTat(Parcel in) {
        in.readList(this.data, LoanStatusTatData.class.getClassLoader());
    }

    public static final Parcelable.Creator<LoanStatusTat> CREATOR = new Parcelable.Creator<LoanStatusTat>() {
        public LoanStatusTat createFromParcel(Parcel source) {
            return new LoanStatusTat(source);
        }

        public LoanStatusTat[] newArray(int size) {
            return new LoanStatusTat[size];
        }
    };

}
