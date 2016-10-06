package com.whatsloan.dsa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

public class Example extends SugarRecord{

    @SerializedName("Accept-Language")
    @Expose
    private String AcceptLanguage;
    @SerializedName("Host")
    @Expose
    private String Host;
    @SerializedName("UserData-Agent")
    @Expose
    private String UserAgent;
    @SerializedName("Accept")
    @Expose
    private String Accept;
    @SerializedName("Cache-Control")
    @Expose
    private String CacheControl;

    public Example(){}

    public Example(String AcceptLanguage,  String Host,  String UserAgent, String Accept, String CacheControl){
        this.AcceptLanguage = AcceptLanguage;
        this.Host = Host;
        this.UserAgent = UserAgent;
        this.Accept = Accept;
        this.CacheControl = CacheControl;
    }

    /**
     *
     * @return
     * The AcceptLanguage
     */
    public String getAcceptLanguage() {
        return AcceptLanguage;
    }

    /**
     *
     * @param AcceptLanguage
     * The Accept-Language
     */
    public void setAcceptLanguage(String AcceptLanguage) {
        this.AcceptLanguage = AcceptLanguage;
    }

    /**
     *
     * @return
     * The Host
     */
    public String getHost() {
        return Host;
    }

    /**
     *
     * @param Host
     * The Host
     */
    public void setHost(String Host) {
        this.Host = Host;
    }

    /**
     *
     * @return
     * The UserAgent
     */
    public String getUserAgent() {
        return UserAgent;
    }

    /**
     *
     * @param UserAgent
     * The UserData-Agent
     */
    public void setUserAgent(String UserAgent) {
        this.UserAgent = UserAgent;
    }

    /**
     *
     * @return
     * The Accept
     */
    public String getAccept() {
        return Accept;
    }

    /**
     *
     * @param Accept
     * The Accept
     */
    public void setAccept(String Accept) {
        this.Accept = Accept;
    }

    /**
     *
     * @return
     * The CacheControl
     */
    public String getCacheControl() {
        return CacheControl;
    }

    /**
     *
     * @param CacheControl
     * The Cache-Control
     */
    public void setCacheControl(String CacheControl) {
        this.CacheControl = CacheControl;
    }

}
