package com.whatsloan.dsa.model;

/**
 * Created by S.K. Pissay on 16/4/16.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BuilderData implements Parcelable{

    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("name")
    @Expose
    private String name;

    /**
     *
     * @return
     * The uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     *
     * @param uuid
     * The uuid
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uuid);
        dest.writeString(this.name);
    }

    protected BuilderData(Parcel in) {
        this.uuid = in.readString();
        this.name = in.readString();
    }

    public static final Creator<BuilderData> CREATOR = new Creator<BuilderData>() {
        public BuilderData createFromParcel(Parcel source) {
            return new BuilderData(source);
        }

        public BuilderData[] newArray(int size) {
            return new BuilderData[size];
        }
    };
}