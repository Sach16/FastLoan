package com.whatsloan.dsa.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by S.K. Pissay on 27/6/16.
 */
public class RatingData implements Parcelable{

    @SerializedName("dsa_rating")
    @Expose
    private String dsaRating;
    @SerializedName("dsa_wise")
    @Expose
    private String dsaWise;
    @SerializedName("city_wise")
    @Expose
    private String cityWise;
    @SerializedName("all_india")
    @Expose
    private String allIndia;

    public String getDsaRating() {
        return dsaRating;
    }

    public void setDsaRating(String dsaRating) {
        this.dsaRating = dsaRating;
    }

    public String getDsaWise() {
        return dsaWise;
    }

    public void setDsaWise(String dsaWise) {
        this.dsaWise = dsaWise;
    }

    public String getCityWise() {
        return cityWise;
    }

    public void setCityWise(String cityWise) {
        this.cityWise = cityWise;
    }

    public String getAllIndia() {
        return allIndia;
    }

    public void setAllIndia(String allIndia) {
        this.allIndia = allIndia;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.dsaRating);
        dest.writeString(this.dsaWise);
        dest.writeString(this.cityWise);
        dest.writeString(this.allIndia);
    }

    protected RatingData(Parcel in) {
        this.dsaRating = in.readString();
        this.dsaWise = in.readString();
        this.cityWise = in.readString();
        this.allIndia = in.readString();
    }

    public static final Parcelable.Creator<RatingData> CREATOR = new Parcelable.Creator<RatingData>() {
        public RatingData createFromParcel(Parcel source) {
            return new RatingData(source);
        }

        public RatingData[] newArray(int size) {
            return new RatingData[size];
        }
    };
}
