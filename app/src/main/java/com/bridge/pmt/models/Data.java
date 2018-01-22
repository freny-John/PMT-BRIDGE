
package com.bridge.pmt.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;


public class Data {

    @SerializedName("user")
    @Expose
    private User user;


    @SerializedName("weekReport")
    @Expose
    private List<WeekReport> weekReport = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Data() {
    }

    /**
     * 
     * @param user
     */
    public Data(User user) {
        super();
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    /**
     *
     * @param weekReport
     */

    public Data(List<WeekReport> weekReport) {
        super();
        this.weekReport = weekReport;
    }

    public List<WeekReport> getWeekReport() {
        return weekReport;
    }

    public void setWeekReport(List<WeekReport> weekReport) {
        this.weekReport = weekReport;
    }





    @Override
    public String toString() {
        return new ToStringBuilder(this).append("user", user).toString();
    }

}
