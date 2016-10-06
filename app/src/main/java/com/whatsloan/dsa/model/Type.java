package com.whatsloan.dsa.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by S.K. Pissay on 23/4/16.
 */
public class Type implements Parcelable{

    @SerializedName("data")
    @Expose
    private LeadsSrcDatum data;

    /**
     *
     * @return
     * The data
     */
    public LeadsSrcDatum getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(LeadsSrcDatum data) {
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

    public Type() {}

    protected Type(Parcel in) {
        this.data = in.readParcelable(LeadsSrcDatum.class.getClassLoader());
    }

    public static final Creator<Type> CREATOR = new Creator<Type>() {
        public Type createFromParcel(Parcel source) {
            return new Type(source);
        }

        public Type[] newArray(int size) {
            return new Type[size];
        }
    };
}
