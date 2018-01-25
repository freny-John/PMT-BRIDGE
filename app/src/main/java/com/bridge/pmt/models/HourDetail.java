
package com.bridge.pmt.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class HourDetail {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("pdate")
    @Expose
    private String pdate;
    @SerializedName("hours")
    @Expose
    private Integer hours;
    @SerializedName("proj_id")
    @Expose
    private Integer projId;
    @SerializedName("activity")
    @Expose
    private String activity;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("extra_work")
    @Expose
    private Integer extraWork;

    /**
     * No args constructor for use in serialization
     * 
     */
    public HourDetail() {
        this.id = 0;
        this.userId = 0;
        this.pdate = "";
        this.hours = 1;
        this.projId = 0;
        this.activity = "";
        this.description = "";
        this.extraWork = 0;
    }

    /**
     * 
     * @param id
     * @param projId
     * @param description
     * @param hours
     * @param userId
     * @param extraWork
     * @param pdate
     * @param activity
     */
    public HourDetail(Integer id, Integer userId, String pdate, Integer hours, Integer projId, String activity, String description, Integer extraWork) {
        super();
        this.id = id;
        this.userId = userId;
        this.pdate = pdate;
        this.hours = hours;
        this.projId = projId;
        this.activity = activity;
        this.description = description;
        this.extraWork = extraWork;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPdate() {
        return pdate;
    }

    public void setPdate(String pdate) {
        this.pdate = pdate;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public Integer getProjId() {
        return projId;
    }

    public void setProjId(Integer projId) {
        this.projId = projId;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getExtraWork() {
        return extraWork;
    }

    public void setExtraWork(Integer extraWork) {
        this.extraWork = extraWork;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("userId", userId).append("pdate", pdate).append("hours", hours).append("projId", projId).append("activity", activity).append("description", description).append("extraWork", extraWork).toString();
    }

}
