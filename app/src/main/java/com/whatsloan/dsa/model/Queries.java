package com.whatsloan.dsa.model;

/**
 * Created by S.K. Pissay on 20/4/16.
 */
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Queries implements Parcelable{

    @SerializedName("data")
    @Expose
    private List<QueriesData> data = new ArrayList<QueriesData>();

    /**
     *
     * @return
     * The data
     */
    public List<QueriesData> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<QueriesData> data) {
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

    public Queries() {}

    protected Queries(Parcel in) {
        in.readList(this.data, QueriesData.class.getClassLoader());
    }

    public static final Creator<Queries> CREATOR = new Creator<Queries>() {
        public Queries createFromParcel(Parcel source) {
            return new Queries(source);
        }

        public Queries[] newArray(int size) {
            return new Queries[size];
        }
    };
}