package com.whatsloan.dsa.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by S.K. Pissay on 5/5/16.
 */
public class Attachments extends SugarRecord implements Parcelable{

    @SerializedName("data")
    @Expose
    private List<AttachmentsData> data = new ArrayList<AttachmentsData>();

    /**
     *
     * @return
     * The data
     */
    public List<AttachmentsData> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<AttachmentsData> data) {
        this.data = data;
    }

    public Attachments(){}

    public Attachments(List<AttachmentsData> data){
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.data);
    }

    protected Attachments(Parcel in) {
        in.readList(this.data, AttachmentsData.class.getClassLoader());
    }

    public static final Creator<Attachments> CREATOR = new Creator<Attachments>() {
        public Attachments createFromParcel(Parcel source) {
            return new Attachments(source);
        }

        public Attachments[] newArray(int size) {
            return new Attachments[size];
        }
    };
}