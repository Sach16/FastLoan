package com.whatsloan.dsa.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by S.K. Pissay on 14/4/16.
 */
public class ProjectsAll  implements Parcelable {

    @SerializedName("data")
    @Expose
    private List<ProjectStruct> projectStruct = new ArrayList<ProjectStruct>();
    @SerializedName("meta")
    @Expose
    private Meta meta;

    /**
     *
     * @return
     * The projects
     */
    public List<ProjectStruct> getProjectStruct() {
        return projectStruct;
    }

    /**
     *
     * @param projectStruct
     * The projects
     */
    public void setProjectStruct(List<ProjectStruct> projectStruct) {
        this.projectStruct = projectStruct;
    }

    /**
     *
     * @return
     * The meta
     */
    public Meta getMeta() {
        return meta;
    }

    /**
     *
     * @param meta
     * The meta
     */
    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.projectStruct);
        dest.writeParcelable(this.meta, 0);
    }

    public ProjectsAll() {}

    protected ProjectsAll(Parcel in) {
        in.readList(this.projectStruct, ProjectStruct.class.getClassLoader());
        this.meta = in.readParcelable(Meta.class.getClassLoader());
    }

    public static final Creator<ProjectsAll> CREATOR = new Creator<ProjectsAll>() {
        public ProjectsAll createFromParcel(Parcel source) {
            return new ProjectsAll(source);
        }

        public ProjectsAll[] newArray(int size) {
            return new ProjectsAll[size];
        }
    };
}