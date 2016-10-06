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

public class Types implements Parcelable {

    @SerializedName("data")
    @Expose
    private List<LeadsSrcDatum> data = new ArrayList<LeadsSrcDatum>();

    /**
     * @return The data
     */
    public List<LeadsSrcDatum> getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(List<LeadsSrcDatum> data) {
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

    public Types() {
    }

    protected Types(Parcel in) {
        in.readList(this.data, LeadsSrcDatum.class.getClassLoader());
    }

    public static final Parcelable.Creator<Types> CREATOR = new Parcelable.Creator<Types>() {
        public Types createFromParcel(Parcel source) {
            return new Types(source);
        }

        public Types[] newArray(int size) {
            return new Types[size];
        }
    };
}
