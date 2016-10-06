package com.whatsloan.dsa.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by S.K. Pissay on 1/6/16.
 */
public class LsrDateData implements Parcelable{

    @SerializedName("initiation_date")
    @Expose
    private PosessionDate initiationDate;
    @SerializedName("completion_date")
    @Expose
    private PosessionDate completionDate;

    public PosessionDate getInitiationDate() {
        return initiationDate;
    }

    public void setInitiationDate(PosessionDate initiationDate) {
        this.initiationDate = initiationDate;
    }

    public PosessionDate getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(PosessionDate completionDate) {
        this.completionDate = completionDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.initiationDate , 0);
        dest.writeParcelable(this.completionDate , 0);
    }

    protected LsrDateData(Parcel in) {
        this.initiationDate = in.readParcelable(PosessionDate.class.getClassLoader());
        this.completionDate = in.readParcelable(PosessionDate.class.getClassLoader());
    }

    public static final Parcelable.Creator<LsrDateData> CREATOR = new Parcelable.Creator<LsrDateData>() {
        public LsrDateData createFromParcel(Parcel source) {
            return new LsrDateData(source);
        }

        public LsrDateData[] newArray(int size) {
            return new LsrDateData[size];
        }
    };
}
