package com.whatsloan.dsa.model;

/**
 * Created by S.K. Pissay on 13/4/16.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Project implements Parcelable{

    @SerializedName("data")
    @Expose
    private ProjectData data;

    /**
     *
     * @return
     * The data
     */
    public ProjectData getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(ProjectData data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.data , 0);
    }

    protected Project(Parcel in) {
        this.data = in.readParcelable(ProjectData.class.getClassLoader());
    }

    public static final Creator<Project> CREATOR = new Creator<Project>() {
        public Project createFromParcel(Parcel source) {
            return new Project(source);
        }

        public Project[] newArray(int size) {
            return new Project[size];
        }
    };
}