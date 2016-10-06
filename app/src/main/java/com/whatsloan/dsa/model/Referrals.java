package com.whatsloan.dsa.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by S.K. Pissay on 4/5/16.
 */
public class Referrals implements Parcelable{

    @SerializedName("data")
    @Expose
    private List<AgentData> data = new ArrayList<AgentData>();

    /**
     *
     * @return
     * The data
     */
    public List<AgentData> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<AgentData> data) {
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

    public Referrals() {}

    protected Referrals(Parcel in) {
        in.readList(this.data, AgentData.class.getClassLoader());
    }

    public static final Creator<Referrals> CREATOR = new Creator<Referrals>() {
        public Referrals createFromParcel(Parcel source) {
            return new Referrals(source);
        }

        public Referrals[] newArray(int size) {
            return new Referrals[size];
        }
    };
}