package com.whatsloan.dsa.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by S.K. Pissay on 23/4/16.
 */
public class SettingsData implements Parcelable{

    @SerializedName("resident_status")
    @Expose
    private String residentStatus;
    @SerializedName("profession")
    @Expose
    private String profession;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("education")
    @Expose
    private String education;
    @SerializedName("marital_status")
    @Expose
    private String maritalStatus;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("DOJ")
    @Expose
    private String DOJ;
    @SerializedName("exp_on_DOJ")
    @Expose
    private String expOnDOJ;
    @SerializedName("designation")
    @Expose
    private String designation;
    @SerializedName("reports_to")
    @Expose
    private String reportsTo;
    @SerializedName("net_income")
    @Expose
    private String netIncome;
    @SerializedName("pan")
    @Expose
    private String pan;
    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("salary_bank")
    @Expose
    private String salaryBank;
    @SerializedName("skype")
    @Expose
    private String skype;
    @SerializedName("facetime")
    @Expose
    private String facetime;
    @SerializedName("contact_time")
    @Expose
    private String contactTime;
    @SerializedName("cibil_score")
    @Expose
    private String cibilScore;
    @SerializedName("cibil_status")
    @Expose
    private String cibilStatus;
    @SerializedName("joined_as")
    @Expose
    private String joinedAs;


    /**
     *
     * @return
     * The residentStatus
     */
    public String getResidentStatus() {
        return residentStatus;
    }

    /**
     *
     * @param residentStatus
     * The resident_status
     */
    public void setResidentStatus(String residentStatus) {
        this.residentStatus = residentStatus;
    }

    /**
     *
     * @return
     * The profession
     */
    public String getProfession() {
        return profession;
    }

    /**
     *
     * @param profession
     * The profession
     */
    public void setProfession(String profession) {
        this.profession = profession;
    }

    /**
     *
     * @return
     * The dob
     */
    public String getDob() {
        return dob;
    }

    /**
     *
     * @param dob
     * The dob
     */
    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     *
     * @return
     * The education
     */
    public String getEducation() {
        return education;
    }

    /**
     *
     * @param education
     * The education
     */
    public void setEducation(String education) {
        this.education = education;
    }

    /**
     *
     * @return
     * The maritalStatus
     */
    public String getMaritalStatus() {
        return maritalStatus;
    }

    /**
     *
     * @param maritalStatus
     * The marital_status
     */
    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    /**
     *
     * @return
     * The company
     */
    public String getCompany() {
        return company;
    }

    /**
     *
     * @param company
     * The company
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     *
     * @return
     * The netIncome
     */
    public String getNetIncome() {
        return netIncome;
    }

    /**
     *
     * @param netIncome
     * The net_income
     */
    public void setNetIncome(String netIncome) {
        this.netIncome = netIncome;
    }

    /**
     *
     * @return
     * The pan
     */
    public String getPan() {
        return pan;
    }

    /**
     *
     * @param pan
     * The pan
     */
    public void setPan(String pan) {
        this.pan = pan;
    }

    /**
     *
     * @return
     * The salaryBank
     */
    public String getSalaryBank() {
        return salaryBank;
    }

    /**
     *
     * @param salaryBank
     * The salary_bank
     */
    public void setSalaryBank(String salaryBank) {
        this.salaryBank = salaryBank;
    }

    /**
     *
     * @return
     * The skype
     */
    public String getSkype() {
        return skype;
    }

    /**
     *
     * @param skype
     * The skype
     */
    public void setSkype(String skype) {
        this.skype = skype;
    }

    /**
     *
     * @return
     * The facetime
     */
    public String getFacetime() {
        return facetime;
    }

    /**
     *
     * @param facetime
     * The facetime
     */
    public void setFacetime(String facetime) {
        this.facetime = facetime;
    }

    /**
     *
     * @return
     * The contactTime
     */
    public String getContactTime() {
        return contactTime;
    }

    /**
     *
     * @param contactTime
     * The contact_time
     */
    public void setContactTime(String contactTime) {
        this.contactTime = contactTime;
    }

    /**
     *
     * @return
     * The cibilScore
     */
    public String getCibilScore() {
        return cibilScore;
    }

    /**
     *
     * @param cibilScore
     * The cibil_score
     */
    public void setCibilScore(String cibilScore) {
        this.cibilScore = cibilScore;
    }

    /**
     *
     * @return
     * The cibilStatus
     */
    public String getCibilStatus() {
        return cibilStatus;
    }

    /**
     *
     * @param cibilStatus
     * The cibil_status
     */
    public void setCibilStatus(String cibilStatus) {
        this.cibilStatus = cibilStatus;
    }

    public String getDOJ() {
        return DOJ;
    }

    public void setDOJ(String DOJ) {
        this.DOJ = DOJ;
    }

    public String getExpOnDOJ() {
        return expOnDOJ;
    }

    public void setExpOnDOJ(String expOnDOJ) {
        this.expOnDOJ = expOnDOJ;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getReportsTo() {
        return reportsTo;
    }

    public void setReportsTo(String reportsTo) {
        this.reportsTo = reportsTo;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getJoinedAs() {
        return joinedAs;
    }

    public void setJoinedAs(String joinedAs) {
        this.joinedAs = joinedAs;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.residentStatus);
        dest.writeString(this.profession);
        dest.writeString(this.dob);
        dest.writeString(this.gender);
        dest.writeString(this.education);
        dest.writeString(this.maritalStatus);
        dest.writeString(this.company);
        dest.writeString(this.DOJ);
        dest.writeString(this.expOnDOJ);
        dest.writeString(this.designation);
        dest.writeString(this.reportsTo);
        dest.writeString(this.netIncome);
        dest.writeString(this.pan);
        dest.writeString(this.age);
        dest.writeString(this.salaryBank);
        dest.writeString(this.skype);
        dest.writeString(this.facetime);
        dest.writeString(this.contactTime);
        dest.writeString(this.cibilScore);
        dest.writeString(this.cibilStatus);
        dest.writeString(this.joinedAs);
    }

    public SettingsData() {}

    protected SettingsData(Parcel in) {
        this.residentStatus = in.readString();
        this.profession = in.readString();
        this.dob = in.readString();
        this.gender = in.readString();
        this.education = in.readString();
        this.maritalStatus = in.readString();
        this.company = in.readString();
        this.DOJ = in.readString();
        this.expOnDOJ = in.readString();
        this.designation = in.readString();
        this.reportsTo = in.readString();
        this.netIncome = in.readString();
        this.pan = in.readString();
        this.age = in.readString();
        this.salaryBank = in.readString();
        this.skype = in.readString();
        this.facetime = in.readString();
        this.contactTime = in.readString();
        this.cibilScore = in.readString();
        this.cibilStatus = in.readString();
        this.joinedAs = in.readString();
    }

    public static final Creator<SettingsData> CREATOR = new Creator<SettingsData>() {
        public SettingsData createFromParcel(Parcel source) {
            return new SettingsData(source);
        }

        public SettingsData[] newArray(int size) {
            return new SettingsData[size];
        }
    };
}
