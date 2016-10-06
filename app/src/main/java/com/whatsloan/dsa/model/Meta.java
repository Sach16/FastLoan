package com.whatsloan.dsa.model;

/**
 * Created by S.K. Pissay on 30/3/16.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Meta implements Parcelable{

    @SerializedName("pagination")
    @Expose
    private Pagination pagination;

    /**
     *
     * @return
     * The pagination
     */
    public Pagination getPagination() {
        return pagination;
    }

    /**
     *
     * @param pagination
     * The pagination
     */
    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.pagination, 0);
    }

    public Meta() {}

    protected Meta(Parcel in) {
        this.pagination = in.readParcelable(Pagination.class.getClassLoader());
    }

    public static final Creator<Meta> CREATOR = new Creator<Meta>() {
        public Meta createFromParcel(Parcel source) {
            return new Meta(source);
        }

        public Meta[] newArray(int size) {
            return new Meta[size];
        }
    };
}