package com.whatsloan.dsa.model;

/**
 * Created by S.K. Pissay on 6/4/16.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AttendanceData implements Parcelable{

    @SerializedName("present")
    @Expose
    private Integer present;
    @SerializedName("leave")
    @Expose
    private Integer leave;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("start_time")
    @Expose
    private PosessionDate startTime;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("count")
    @Expose
    private Integer count;

    /**
     *
     * @return
     * The count
     */
    public Integer getCount() {
        return count;
    }

    /**
     *
     * @param count
     * The count
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     *
     * @return
     * The present
     */
    public Integer getPresent() {
        return present;
    }

    /**
     *
     * @param present
     * The present
     */
    public void setPresent(Integer present) {
        this.present = present;
    }

    /**
     *
     * @return
     * The leave
     */
    public Integer getLeave() {
        return leave;
    }

    /**
     *
     * @param leave
     * The leave
     */
    public void setLeave(Integer leave) {
        this.leave = leave;
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
     * The user
     */
    public User getUser() {
        return user;
    }

    /**
     *
     * @param user
     * The user
     */
    public void setUser(User user) {
        this.user = user;
    }

    public PosessionDate getStartTime() {
        return startTime;
    }

    public void setStartTime(PosessionDate startTime) {
        this.startTime = startTime;
    }

    protected AttendanceData(Parcel in) {
        this.present = in.readInt();
        this.leave = in.readInt();
        this.name = in.readString();
        this.uuid = in.readString();
        this.startTime = in.readParcelable(PosessionDate.class.getClassLoader());
        this.user = in.readParcelable(User.class.getClassLoader());
        this.count = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.present);
        dest.writeInt(this.leave);
        dest.writeString(this.name);
        dest.writeString(this.uuid);
        dest.writeParcelable(this.startTime, 0);
        dest.writeParcelable(this.user, 0);
        dest.writeInt(this.count);
    }

    public static final Creator<AttendanceData> CREATOR = new Creator<AttendanceData>() {
        public AttendanceData createFromParcel(Parcel source) {
            return new AttendanceData(source);
        }

        public AttendanceData[] newArray(int size) {
            return new AttendanceData[size];
        }
    };
}