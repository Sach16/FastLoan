package com.whatsloan.dsa.model;

/**
 * Created by S.K. Pissay on 6/4/16.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Team implements Parcelable{

    @SerializedName("data")
    @Expose
    private List<TeamsData> data = new ArrayList<TeamsData>();

    /**
     *
     * @return
     * The data
     */
    public List<TeamsData> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<TeamsData> data) {
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

    public Team() {}

    protected Team(Parcel in) {
        in.readList(this.data, TeamsData.class.getClassLoader());
    }

    public static final Creator<Team> CREATOR = new Creator<Team>() {
        public Team createFromParcel(Parcel source) {
            return new Team(source);
        }

        public Team[] newArray(int size) {
            return new Team[size];
        }
    };
}
