package com.whatsloan.dsa.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by S.K. Pissay on 6/5/16.
 */
public class Source implements Parcelable{
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

    public Source() {}

    protected Source(Parcel in) {
        this.data = in.readParcelable(LeadsSrcDatum.class.getClassLoader());
    }

    public static final Creator<Source> CREATOR = new Creator<Source>() {
        public Source createFromParcel(Parcel source) {
            return new Source(source);
        }

        public Source[] newArray(int size) {
            return new Source[size];
        }
    };
}