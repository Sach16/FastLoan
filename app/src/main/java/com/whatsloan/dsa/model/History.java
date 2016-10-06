package com.whatsloan.dsa.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by S.K. Pissay on 28/5/16.
 */
public class History implements Parcelable{

    @SerializedName("data")
    @Expose
    private List<HistoryData> data = new ArrayList<HistoryData>();

    /**
     *
     * @return
     * The data
     */
    public List<HistoryData> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<HistoryData> data) {
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

    public History() {}

    protected History(Parcel in) {
        in.readList(this.data, HistoryData.class.getClassLoader());
    }

    public static final Creator<History> CREATOR = new Creator<History>() {
        public History createFromParcel(Parcel source) {
            return new History(source);
        }

        public History[] newArray(int size) {
            return new History[size];
        }
    };
}
