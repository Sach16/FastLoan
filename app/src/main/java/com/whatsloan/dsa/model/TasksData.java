package com.whatsloan.dsa.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by S.K. Pissay on 1/4/16.
 */
public class TasksData implements Parcelable{

    public static final String VIEW_ITEM = "VIEW_ITEM";
    public static final String VIEW_PROG = "VIEW_PROG";
    public static final String CUSTOMER_INFO = "CUSTOMER_INFO";

    CustomersData customersData;

    @SerializedName("customer")
    @Expose
    CustomersLoan customer;
    @SerializedName("uuid")
    @Expose
    private String uuid;
    /*@SerializedName("user_id")
    @Expose
    private Integer userId;*/
    /*@SerializedName("assigned_to")
    @Expose
    private Integer assignedto;*/
    @SerializedName("status")
    @Expose
    private Status status;
    @SerializedName("stage")
    @Expose
    private Status stage;
    @SerializedName("priority")
    @Expose
    private String priority;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("from")
    @Expose
    private CreatedAt from;
    @SerializedName("to")
    @Expose
    private UpdatedAt to;
    @SerializedName("members")
    @Expose
    private Members members;
    @SerializedName("tasks")
    @Expose
    private Tasks tasks;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("created_at")
    @Expose
    private CreatedAt createdAt;
    @SerializedName("updated_at")
    @Expose
    private UpdatedAt updatedAt;

    private String type;

    public TasksData() {}

    public TasksData(String type) {
        this.type = type;
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


    /*public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }*/

    /*public Integer getAssignedto() {
        return assignedto;
    }

    public void setAssignedto(Integer assignedto) {
        this.assignedto = assignedto;
    }*/

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public Status getStage() {
        return stage;
    }

    public void setStage(Status stage) {
        this.stage = stage;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CreatedAt getFrom() {
        return from;
    }

    public void setFrom(CreatedAt from) {
        this.from = from;
    }

    public UpdatedAt getTo() {
        return to;
    }

    public void setTo(UpdatedAt to) {
        this.to = to;
    }

    public Members getMembers() {
        return members;
    }

    public Tasks getTasks() {
        return tasks;
    }

    public void setTasks(Tasks tasks) {
        this.tasks = tasks;
    }

    public void setMembers(Members members) {
        this.members = members;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CustomersData getCustomersData() {
        return customersData;
    }

    public void setCustomersData(CustomersData customersData) {
        this.customersData = customersData;
    }

    public CustomersLoan getCustomer() {
        return customer;
    }

    public void setCustomer(CustomersLoan customer) {
        this.customer = customer;
    }

    public CreatedAt getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(CreatedAt createdAt) {
        this.createdAt = createdAt;
    }

    public UpdatedAt getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(UpdatedAt updatedAt) {
        this.updatedAt = updatedAt;
    }

    protected TasksData(Parcel in) {
        this.uuid = in.readString();
        this.status = in.readParcelable(Status.class.getClassLoader());
        this.stage = in.readParcelable(Status.class.getClassLoader());
        this.priority = in.readString();
        this.description = in.readString();
        this.from = in.readParcelable(CreatedAt.class.getClassLoader());
        this.to = in.readParcelable(UpdatedAt.class.getClassLoader());
        this.members = in.readParcelable(Team.class.getClassLoader());
        this.tasks = in.readParcelable(Tasks.class.getClassLoader());
        this.user = in.readParcelable(User.class.getClassLoader());
        this.customer = in.readParcelable(CustomersLoan.class.getClassLoader());
        this.createdAt = in.readParcelable(CreatedAt.class.getClassLoader());
        this.updatedAt = in.readParcelable(UpdatedAt.class.getClassLoader());
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uuid);
        dest.writeParcelable(this.status, 0);
        dest.writeParcelable(this.stage, 0);
        dest.writeString(this.priority);
        dest.writeString(this.description);
        dest.writeParcelable(this.from, 0);
        dest.writeParcelable(this.to, 0);
        dest.writeParcelable(this.members, 0);
        dest.writeParcelable(this.tasks, 0);
        dest.writeParcelable(this.user, 0);
        dest.writeParcelable(this.customer, 0);
        dest.writeParcelable(this.createdAt, 0);
        dest.writeParcelable(this.updatedAt, 0);
    }

    public static final Creator<TasksData> CREATOR = new Creator<TasksData>() {
        public TasksData createFromParcel(Parcel source) {
            return new TasksData(source);
        }

        public TasksData[] newArray(int size) {
            return new TasksData[size];
        }
    };
}