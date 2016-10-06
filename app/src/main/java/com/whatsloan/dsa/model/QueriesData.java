package com.whatsloan.dsa.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by S.K. Pissay on 20/4/16.
 */

public class QueriesData implements Parcelable{

    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("query")
    @Expose
    private String query;
    @SerializedName("start_date")
    @Expose
    private PosessionDate startDate;
    @SerializedName("end_date")
    @Expose
    private PosessionDate endDate;
    @SerializedName("due_date")
    @Expose
    private PosessionDate dueDate;
    @SerializedName("raised_date")
    @Expose
    private PosessionDate raisedDate;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("pending_with")
    @Expose
    private String pendingWith;
    @SerializedName("assignee")
    @Expose
    private Assignee assignee;

    /**
     * @return The uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * @param uuid The uuid
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * @return The query
     */
    public String getQuery() {
        return query;
    }

    /**
     * @param query The query
     */
    public void setQuery(String query) {
        this.query = query;
    }

    /**
     * @return The startDate
     */
    public PosessionDate getStartDate() {
        return startDate;
    }

    /**
     * @param startDate The start_date
     */
    public void setStartDate(PosessionDate startDate) {
        this.startDate = startDate;
    }

    /**
     * @return The endDate
     */
    public PosessionDate getEndDate() {
        return endDate;
    }

    /**
     * @param endDate The end_date
     */
    public void setEndDate(PosessionDate endDate) {
        this.endDate = endDate;
    }

    /**
     * @return The dueDate
     */
    public PosessionDate getDueDate() {
        return dueDate;
    }

    /**
     * @param dueDate The due_date
     */
    public void setDueDate(PosessionDate dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * @return The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return The assignee
     */
    public Assignee getAssignee() {
        return assignee;
    }

    /**
     * @param assignee The assignee
     */
    public void setAssignee(Assignee assignee) {
        this.assignee = assignee;
    }

    public String getPendingWith() {
        return pendingWith;
    }

    public void setPendingWith(String pendingWith) {
        this.pendingWith = pendingWith;
    }

    public PosessionDate getRaisedDate() {
        return raisedDate;
    }

    public void setRaisedDate(PosessionDate raisedDate) {
        this.raisedDate = raisedDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uuid);
        dest.writeString(this.query);
        dest.writeParcelable(this.startDate, 0);
        dest.writeParcelable(this.endDate, 0);
        dest.writeParcelable(this.dueDate, 0);
        dest.writeParcelable(this.raisedDate, 0);
        dest.writeString(this.status);
        dest.writeString(this.pendingWith);
        dest.writeParcelable(this.assignee, 0);
    }

    public QueriesData() {}

    protected QueriesData(Parcel in) {
        this.uuid = in.readString();
        this.query = in.readString();
        this.startDate = in.readParcelable(PosessionDate.class.getClassLoader());
        this.endDate = in.readParcelable(PosessionDate.class.getClassLoader());
        this.dueDate = in.readParcelable(PosessionDate.class.getClassLoader());
        this.raisedDate = in.readParcelable(PosessionDate.class.getClassLoader());
        this.status = in.readString();
        this.pendingWith = in.readString();
        this.assignee = in.readParcelable(Assignee.class.getClassLoader());
    }

    public static final Creator<QueriesData> CREATOR = new Creator<QueriesData>() {
        public QueriesData createFromParcel(Parcel source) {
            return new QueriesData(source);
        }

        public QueriesData[] newArray(int size) {
            return new QueriesData[size];
        }
    };
}
