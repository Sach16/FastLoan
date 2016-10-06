package com.whatsloan.dsa.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by S.K. Pissay on 2/6/16.
 */
public class LoanStatusTatData implements Parcelable {

    @SerializedName("status_tat")
    @Expose
    private Integer statusTat;
    @SerializedName("status")
    @Expose
    private String status;

    public Integer getStatusTat() {
        return statusTat;
    }

    public void setStatusTat(Integer statusTat) {
        this.statusTat = statusTat;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.statusTat);
        dest.writeString(this.status);
    }

    public LoanStatusTatData() {}

    protected LoanStatusTatData(Parcel in) {
        this.statusTat = in.readInt();
        this.status = in.readString();
    }


    public static final Creator<LoanStatusTatData> CREATOR = new Creator<LoanStatusTatData>() {
        public LoanStatusTatData createFromParcel(Parcel source) {
            return new LoanStatusTatData(source);
        }

        public LoanStatusTatData[] newArray(int size) {
            return new LoanStatusTatData[size];
        }
    };
}
