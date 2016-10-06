package com.whatsloan.dsa.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

/**
 * Created by S.K. Pissay on 5/5/16.
 */
public class AttachmentsData extends SugarRecord implements Parcelable{

    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("uri")
    @Expose
    private String uri;
    @SerializedName("type")
    @Expose
    private String type;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     * The uri
     */
    public String getUri() {
        return uri;
    }

    /**
     *
     * @param uri
     * The uri
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     *
     * @return
     * The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(String type) {
        this.type = type;
    }

    public AttachmentsData() {}

    public AttachmentsData(String uuid, String name, String description, String uri, String type){
        this.uuid = uuid;
        this.name = name;
        this.description = description;
        this.uri = uri;
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uuid);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeString(this.uri);
        dest.writeString(this.type);
    }

    protected AttachmentsData(Parcel in) {
        this.uuid = in.readString();
        this.name = in.readString();
        this.description = in.readString();
        this.uri = in.readString();
        this.type = in.readString();
    }

    public static final Creator<AttachmentsData> CREATOR = new Creator<AttachmentsData>() {
        public AttachmentsData createFromParcel(Parcel source) {
            return new AttachmentsData(source);
        }

        public AttachmentsData[] newArray(int size) {
            return new AttachmentsData[size];
        }
    };
}