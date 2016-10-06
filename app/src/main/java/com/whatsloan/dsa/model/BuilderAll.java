package com.whatsloan.dsa.model;

/**
 * Created by S.K. Pissay on 18/4/16.
 */
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class BuilderAll implements Parcelable{

    @SerializedName("data")
    @Expose
    private List<BuilderData> data = new ArrayList<BuilderData>();
    @SerializedName("meta")
    @Expose
    private Meta meta;

    /**
     *
     * @return
     * The data
     */
    public List<BuilderData> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<BuilderData> data) {
        this.data = data;
    }

    /**
     *
     * @return
     * The meta
     */
    public Meta getMeta() {
        return meta;
    }

    /**
     *
     * @param meta
     * The meta
     */
    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.data);
        dest.writeParcelable(this.meta, 0);
    }

    public BuilderAll() {}

    protected BuilderAll(Parcel in) {
        in.readList(this.data, BuilderData.class.getClassLoader());
        this.meta = in.readParcelable(Meta.class.getClassLoader());
    }

    public static final Creator<BuilderAll> CREATOR = new Creator<BuilderAll>() {
        public BuilderAll createFromParcel(Parcel source) {
            return new BuilderAll(source);
        }

        public BuilderAll[] newArray(int size) {
            return new BuilderAll[size];
        }
    };
}