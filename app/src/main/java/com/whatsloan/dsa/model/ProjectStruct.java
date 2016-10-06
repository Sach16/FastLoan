package com.whatsloan.dsa.model;

/**
 * Created by S.K. Pissay on 13/4/16.
 */
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProjectStruct implements Parcelable{

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("bank_id")
    @Expose
    private Integer bankId;
    @SerializedName("project_id")
    @Expose
    private Integer projectId;
    @SerializedName("agent_id")
    @Expose
    private Integer agentId;
    @SerializedName("approved_date")
    @Expose
    private PosessionDate approvedDate;
    @SerializedName("created_at")
    @Expose
    private CreatedAt createdAt;
    @SerializedName("updated_at")
    @Expose
    private UpdatedAt updatedAt;
    @SerializedName("bank")
    @Expose
    private Bank bank;
    @SerializedName("project")
    @Expose
    private Project project;
    @SerializedName("agent")
    @Expose
    private Agent agent;

    /**
     *
     * @return
     * The status
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The bankId
     */
    public Integer getBankId() {
        return bankId;
    }

    /**
     *
     * @param bankId
     * The bank_id
     */
    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    /**
     *
     * @return
     * The projectId
     */
    public Integer getProjectId() {
        return projectId;
    }

    /**
     *
     * @param projectId
     * The project_id
     */
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    /**
     *
     * @return
     * The agentId
     */
    public Integer getAgentId() {
        return agentId;
    }

    /**
     *
     * @param agentId
     * The agent_id
     */
    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
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
     * The bank
     */
    public Bank getBank() {
        return bank;
    }

    /**
     *
     * @param bank
     * The bank
     */
    public void setBank(Bank bank) {
        this.bank = bank;
    }

    /**
     *
     * @return
     * The project
     */
    public Project getProject() {
        return project;
    }

    /**
     *
     * @param project
     * The project
     */
    public void setProject(Project project) {
        this.project = project;
    }

    /**
     *
     * @return
     * The agent
     */
    public Agent getAgent() {
        return agent;
    }

    /**
     *
     * @param agent
     * The agent
     */
    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public PosessionDate getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(PosessionDate approvedDate) {
        this.approvedDate = approvedDate;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.status);
        dest.writeInt(this.bankId);
        dest.writeInt(this.projectId);
        dest.writeInt(this.agentId);
        dest.writeParcelable(this.approvedDate, 0);
        dest.writeParcelable(this.createdAt, 0);
        dest.writeParcelable(this.updatedAt, 0);
        dest.writeParcelable(this.bank, 0);
        dest.writeParcelable(this.project, 0);
        dest.writeParcelable(this.agent, 0);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public ProjectStruct() {}

    protected ProjectStruct(Parcel in) {
        this.status = in.readString();
        this.bankId = in.readInt();
        this.projectId = in.readInt();
        this.agentId = in.readInt();
        this.approvedDate = in.readParcelable(PosessionDate.class.getClassLoader());
        this.createdAt = in.readParcelable(CreatedAt.class.getClassLoader());
        this.updatedAt = in.readParcelable(UpdatedAt.class.getClassLoader());
        this.bank = in.readParcelable(Bank.class.getClassLoader());
        this.project = in.readParcelable(Project.class.getClassLoader());
        this.agent = in.readParcelable(Agent.class.getClassLoader());
    }

    public static final Creator<ProjectStruct> CREATOR = new Creator<ProjectStruct>() {
        public ProjectStruct createFromParcel(Parcel source) {
            return new ProjectStruct(source);
        }

        public ProjectStruct[] newArray(int size) {
            return new ProjectStruct[size];
        }
    };
}
