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

public class Tasks implements Parcelable{

    @SerializedName("data")
    @Expose
    private List<TasksData> data = new ArrayList<TasksData>();
    @SerializedName("meta")
    @Expose
    private Meta meta;

    /**
     *
     * @return
     * The data
     */
    public List<TasksData> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<TasksData> data) {
        this.data = data;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    //Todo remove the (Parcelable) cast below

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.data);
        dest.writeParcelable(this.meta, 0);
    }

    public Tasks() {}

    protected Tasks(Parcel in) {
        in.readList(this.data, TasksData.class.getClassLoader());
        this.meta = in.readParcelable(Meta.class.getClassLoader());
    }

    public static final Creator<Tasks> CREATOR = new Creator<Tasks>() {
        public Tasks createFromParcel(Parcel source) {
            return new Tasks(source);
        }

        public Tasks[] newArray(int size) {
            return new Tasks[size];
        }
    };
    
}
