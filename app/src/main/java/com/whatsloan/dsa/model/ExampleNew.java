
package com.whatsloan.dsa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

public class ExampleNew extends SugarRecord{

    @SerializedName("object_or_array")
    @Expose
    private String objectOrArray;
    @SerializedName("empty")
    @Expose
    private Boolean empty;
    @SerializedName("parse_time_nanoseconds")
    @Expose
    private Integer parseTimeNanoseconds;
    @SerializedName("validate")
    @Expose
    private Boolean validate;
    @SerializedName("size")
    @Expose
    private Integer size;

    public ExampleNew (){}

    public ExampleNew (String objectOrArray, Boolean empty, Integer parseTimeNanoseconds, Boolean validate, Integer size){
        this.objectOrArray = objectOrArray;
        this.empty = empty;
        this.parseTimeNanoseconds = parseTimeNanoseconds;
        this.validate = validate;
        this.size = size;
    }

    /**
     *
     * @return
     * The objectOrArray
     */
    public String getObjectOrArray() {
        return objectOrArray;
    }

    /**
     *
     * @param objectOrArray
     * The object_or_array
     */
    public void setObjectOrArray(String objectOrArray) {
        this.objectOrArray = objectOrArray;
    }

    /**
     *
     * @return
     * The empty
     */
    public Boolean getEmpty() {
        return empty;
    }

    /**
     *
     * @param empty
     * The empty
     */
    public void setEmpty(Boolean empty) {
        this.empty = empty;
    }

    /**
     *
     * @return
     * The parseTimeNanoseconds
     */
    public Integer getParseTimeNanoseconds() {
        return parseTimeNanoseconds;
    }

    /**
     *
     * @param parseTimeNanoseconds
     * The parse_time_nanoseconds
     */
    public void setParseTimeNanoseconds(Integer parseTimeNanoseconds) {
        this.parseTimeNanoseconds = parseTimeNanoseconds;
    }

    /**
     *
     * @return
     * The validate
     */
    public Boolean getValidate() {
        return validate;
    }

    /**
     *
     * @param validate
     * The validate
     */
    public void setValidate(Boolean validate) {
        this.validate = validate;
    }

    /**
     *
     * @return
     * The size
     */
    public Integer getSize() {
        return size;
    }

    /**
     *
     * @param size
     * The size
     */
    public void setSize(Integer size) {
        this.size = size;
    }

}

