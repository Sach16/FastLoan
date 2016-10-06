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

//rename to projects all
public class Projects  implements Parcelable {

    @SerializedName("data")
    @Expose
    private List<ProjectData> ProjectData = new ArrayList<ProjectData>();
    @SerializedName("meta")
    @Expose
    private Meta meta;


    public List<ProjectData> getProjectData() {
        return ProjectData;
    }

    public void setProjectData(List<ProjectData> ProjectData) {
        this.ProjectData = ProjectData;
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
        dest.writeList(this.ProjectData);
        dest.writeParcelable(this.meta, 0);
    }

    public Projects() {}

    protected Projects(Parcel in) {
        in.readList(this.ProjectData, ProjectData.class.getClassLoader());
        this.meta = in.readParcelable(Meta.class.getClassLoader());
    }

    public static final Creator<Projects> CREATOR = new Creator<Projects>() {
        public Projects createFromParcel(Parcel source) {
            return new Projects(source);
        }

        public Projects[] newArray(int size) {
            return new Projects[size];
        }
    };
}