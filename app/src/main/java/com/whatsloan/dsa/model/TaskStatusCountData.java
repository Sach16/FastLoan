package com.whatsloan.dsa.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by S.K. Pissay on 1/4/16.
 */
public class TaskStatusCountData implements Parcelable{

    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("status")
    @Expose
    private StatusData status;

    protected TaskStatusCountData(Parcel in) {
        this.count = in.readInt();
        this.status = in.readParcelable(StatusData.class.getClassLoader());
    }

    /**
     *
     * @return
     * The count
     */
    public Integer getCount() {
        return count;
    }

    /**
     *
     * @param count
     * The count
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     *
     * @return
     * The status
     */
    public StatusData getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(StatusData status) {
        this.status = status;
    }

    public TaskStatusCountData() {}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.count);
        dest.writeParcelable(this.status, 0);
    }

    public static final Creator<TaskStatusCountData> CREATOR = new Creator<TaskStatusCountData>() {
        public TaskStatusCountData createFromParcel(Parcel source) {
            return new TaskStatusCountData(source);
        }

        public TaskStatusCountData[] newArray(int size) {
            return new TaskStatusCountData[size];
        }
    };
}