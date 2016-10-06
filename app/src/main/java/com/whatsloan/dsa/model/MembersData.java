package com.whatsloan.dsa.model;

/**
 * Created by S.K. Pissay on 1/4/16.
 */
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MembersData implements Parcelable{

    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("created_at")
    @Expose
    private CreatedAt createdAt;
    @SerializedName("updated_at")
    @Expose
    private UpdatedAt updatedAt;
    @SerializedName("tasks")
    @Expose
    private Tasks tasks;
    @SerializedName("taskStatusCount")
    @Expose
    private TaskStatusCount taskStatusCount;
    @SerializedName("attendances")
    @Expose
    private Attendances attendances;

    public MembersData() {}

    protected MembersData(Parcel in) {
        this.uuid = in.readString();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.email = in.readString();
        this.phone = in.readString();
        this.role = in.readString();
        this.token = in.readString();
        this.createdAt = in.readParcelable(CreatedAt.class.getClassLoader());
        this.updatedAt = in.readParcelable(UpdatedAt.class.getClassLoader());
        this.tasks = in.readParcelable(Tasks.class.getClassLoader());
        this.taskStatusCount = in.readParcelable(TaskStatusCount.class.getClassLoader());
        this.attendances = in.readParcelable(Attendances.class.getClassLoader());
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
     * The firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     *
     * @param firstName
     * The first_name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     *
     * @return
     * The lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     *
     * @param lastName
     * The last_name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     *
     * @return
     * The email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     * The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     * The role
     */
    public String getRole() {
        return role;
    }

    /**
     *
     * @param role
     * The role
     */
    public void setRole(String role) {
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     *
     * @return
     * The token
     */
    public String getToken() {
        return token;
    }

    /**
     *
     * @param token
     * The token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     *
     * @return
     * The createdAt
     */
    public CreatedAt getCreatedAt() {
        return createdAt;
    }

    /**
     *
     * @param createdAt
     * The created_at
     */
    public void setCreatedAt(CreatedAt createdAt) {
        this.createdAt = createdAt;
    }

    /**
     *
     * @return
     * The updatedAt
     */
    public UpdatedAt getUpdatedAt() {
        return updatedAt;
    }

    /**
     *
     * @param updatedAt
     * The updated_at
     */
    public void setUpdatedAt(UpdatedAt updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     *
     * @return
     * The tasks
     */
    public Tasks getTasks() {
        return tasks;
    }

    /**
     *
     * @param tasks
     * The tasks
     */
    public void setTasks(Tasks tasks) {
        this.tasks = tasks;
    }

    /**
     *
     * @return
     * The taskStatusCount
     */
    public TaskStatusCount getTaskStatusCount() {
        return taskStatusCount;
    }

    /**
     *
     * @param taskStatusCount
     * The taskStatusCount
     */
    public void setTaskStatusCount(TaskStatusCount taskStatusCount) {
        this.taskStatusCount = taskStatusCount;
    }

    public Attendances getAttendances() {
        return attendances;
    }

    public void setAttendances(Attendances attendances) {
        this.attendances = attendances;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uuid);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.email);
        dest.writeString(this.phone);
        dest.writeString(this.role);
        dest.writeString(this.token != null ? this.token : "");
        dest.writeParcelable(this.createdAt, 0);
        dest.writeParcelable(this.updatedAt, 0);
        dest.writeParcelable(this.tasks, 0);
        dest.writeParcelable(this.taskStatusCount, 0);
        dest.writeParcelable(this.attendances, 0);
    }

    public static final Creator<MembersData> CREATOR = new Creator<MembersData>() {
        public MembersData createFromParcel(Parcel source) {
            return new MembersData(source);
        }

        public MembersData[] newArray(int size) {
            return new MembersData[size];
        }
    };

}
