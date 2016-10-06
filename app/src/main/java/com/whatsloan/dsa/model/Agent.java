package com.whatsloan.dsa.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by S.K. Pissay on 23/4/16.
 */
public class Agent implements Parcelable{
    @SerializedName("data")
    @Expose
    private AgentData data;

    /**
     *
     * @return
     * The data
     */
    public AgentData getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(AgentData data) {
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

    public Agent() {}

    protected Agent(Parcel in) {
        this.data = in.readParcelable(AgentData.class.getClassLoader());
    }

    public static final Creator<Agent> CREATOR = new Creator<Agent>() {
        public Agent createFromParcel(Parcel source) {
            return new Agent(source);
        }

        public Agent[] newArray(int size) {
            return new Agent[size];
        }
    };
}
