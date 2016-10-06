package com.whatsloan.dsa.model;

/**
 * Created by S.K. Pissay on 18/4/16.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeamMembers implements Parcelable{

    @SerializedName("data")
    @Expose
    private TeamsData data;

    /**
     *
     * @return
     * The data
     */
    public TeamsData getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(TeamsData data) {
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

    public TeamMembers() {}

    protected TeamMembers(Parcel in) {
        this.data = in.readParcelable(TeamsData.class.getClassLoader());
    }

    public static final Creator<TeamMembers> CREATOR = new Creator<TeamMembers>() {
        public TeamMembers createFromParcel(Parcel source) {
            return new TeamMembers(source);
        }

        public TeamMembers[] newArray(int size) {
            return new TeamMembers[size];
        }
    };
}