package com.whatsloan.dsa.model;

/**
 * Created by S.K. Pissay on 14/4/16.
 */
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatusData implements Parcelable{

    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("created_at")
    @Expose
    private CreatedAt createdat;
    @SerializedName("updated_at")
    @Expose
    private UpdatedAt updatedat;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public CreatedAt getCreatedat() {
        return createdat;
    }

    public void setCreatedat(CreatedAt createdat) {
        this.createdat = createdat;
    }

    public UpdatedAt getUpdatedat() {
        return updatedat;
    }

    public void setUpdatedat(UpdatedAt updatedat) {
        this.updatedat = updatedat;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uuid);
        dest.writeString(this.status);
        dest.writeInt(this.id != null ? this.id : 0);
        dest.writeString(this.key);
        dest.writeString(this.label);
        dest.writeParcelable(this.createdat, 0);
        dest.writeParcelable(this.updatedat, 0);
    }

    public StatusData() {}

    protected StatusData(Parcel in) {
        this.uuid = in.readString();
        this.status = in.readString();
        this.id = in.readInt();
        this.key = in.readString();
        this.label = in.readString();
        this.createdat = in.readParcelable(CreatedAt.class.getClassLoader());
        this.updatedat = in.readParcelable(UpdatedAt.class.getClassLoader());


    }

    public static final Creator<StatusData> CREATOR = new Creator<StatusData>() {
        public StatusData createFromParcel(Parcel source) {
            return new StatusData(source);
        }

        public StatusData[] newArray(int size) {
            return new StatusData[size];
        }
    };
}
