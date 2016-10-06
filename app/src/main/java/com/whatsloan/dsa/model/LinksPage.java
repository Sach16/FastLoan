package com.whatsloan.dsa.model;

/**
 * Created by S.K. Pissay on 30/3/16.
 */
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LinksPage implements Parcelable{

    @SerializedName("next")
    @Expose
    private String next;
    @SerializedName("current")
    @Expose
    private String current;
    @SerializedName("previous")
    @Expose
    private String previous;

    /**
     *
     * @return
     * The next
     */
    public String getNext() {
        return next;
    }

    /**
     *
     * @param next
     * The next
     */
    public void setNext(String next) {
        this.next = next;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.next);
        dest.writeString(this.current);
        dest.writeString(this.previous);
    }

    public LinksPage() {}

    protected LinksPage(Parcel in) {
        this.next = in.readString();
        this.current = in.readString();
        this.previous = in.readString();
    }

    public static final Creator<LinksPage> CREATOR = new Creator<LinksPage>() {
        public LinksPage createFromParcel(Parcel source) {
            return new LinksPage(source);
        }

        public LinksPage[] newArray(int size) {
            return new LinksPage[size];
        }
    };
}
