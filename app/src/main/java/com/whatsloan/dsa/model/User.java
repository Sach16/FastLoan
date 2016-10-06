package com.whatsloan.dsa.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by S.K. Pissay on 6/5/16.
 */
public class User implements Parcelable{
    @SerializedName("data")
    @Expose
    private UserData data;

    /**
     * @return The data
     */
    public UserData getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(UserData data) {
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

    public User() {}

    protected User(Parcel in) {
        this.data = in.readParcelable(UserData.class.getClassLoader());
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
}