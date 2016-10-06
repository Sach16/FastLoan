package com.whatsloan.dsa.model;

/**
 * Created by S.K. Pissay on 1/4/16.
 */
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TaskStatusCount implements Parcelable{

    @SerializedName("data")
    @Expose
    private List<TaskStatusCountData> data = new ArrayList<TaskStatusCountData>();

    /**
     *
     * @return
     * The data
     */
    public List<TaskStatusCountData> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<TaskStatusCountData> data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    //Todo reve the (Parcelable) cast below

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.data);
    }

    public TaskStatusCount() {}

    protected TaskStatusCount(Parcel in) {
        in.readList(this.data, TaskStatusCountData.class.getClassLoader());
    }

    public static final Creator<TaskStatusCount> CREATOR = new Creator<TaskStatusCount>() {
        public TaskStatusCount createFromParcel(Parcel source) {
            return new TaskStatusCount(source);
        }

        public TaskStatusCount[] newArray(int size) {
            return new TaskStatusCount[size];
        }
    };
}
