
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


    @SerializedName("activity")
    @Expose
    private List<Activity> activity = null;


    @SerializedName("project_list")
    @Expose
    private List<ProjectList> projectList = null;




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

    /**
     *
     * @param activity
     */

    public Data(List<WeekReport> weekReport,List<Activity> activity) {
        super();
        this.weekReport = weekReport;
        this.activity = activity;
    }

    public List<WeekReport> getWeekReport() {
        return weekReport;
    }

    public void setWeekReport(List<WeekReport> weekReport) {
        this.weekReport = weekReport;
    }





//    /**
//     *
//     * @param activity
//     */
//    public Data(List<Activity> activity) {
//        super();
//        this.activity = activity;
//    }

    public List<Activity> getActivity() {
        return activity;
    }

    public void setActivity(List<Activity> activity) {
        this.activity = activity;
    }



    /**
     *
     * @param projectList
     */
    public Data(List<ProjectList> projectList) {
        super();
        this.projectList = projectList;
    }

    public List<ProjectList> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<ProjectList> projectList) {
        this.projectList = projectList;
    }




    @Override
    public String toString() {
        return new ToStringBuilder(this).append("user", user).toString();
    }

}
