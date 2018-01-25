
package com.bridge.pmt.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class Activity {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("act_show")
    @Expose
    private Integer actShow;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Activity() {
    }

    /**
     * 
     * @param id
     * @param name
     * @param code
     * @param actShow
     */
    public Activity(Integer id, String code, String name, Integer actShow) {
        super();
        this.id = id;
        this.code = code;
        this.name = name;
        this.actShow = actShow;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getActShow() {
        return actShow;
    }

    public void setActShow(Integer actShow) {
        this.actShow = actShow;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("code", code).append("name", name).append("actShow", actShow).toString();
    }

}
