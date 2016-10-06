package com.whatsloan.dsa.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by S.K. Pissay on 23/4/16.
 */
public class Settings implements Parcelable{
    @SerializedName("data")
    @Expose
    private SettingsData data;

    /**
     *
     * @return
     * The data
     */
    public SettingsData getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(SettingsData data) {
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

    public Settings() {}

    protected Settings(Parcel in) {
        this.data = in.readParcelable(SettingsData.class.getClassLoader());
    }

    public static final Creator<Settings> CREATOR = new Creator<Settings>() {
        public Settings createFromParcel(Parcel source) {
            return new Settings(source);
        }

        public Settings[] newArray(int size) {
            return new Settings[size];
        }
    };
}
