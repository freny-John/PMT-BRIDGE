
package com.bridge.pmt.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ProjectList {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("project_id")
    @Expose
    private Integer projectId;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("company_id")
    @Expose
    private Integer companyId;
    @SerializedName("status_id")
    @Expose
    private Integer statusId;
    @SerializedName("status_name")
    @Expose
    private String statusName;
    @SerializedName("project")
    @Expose
    private Project project;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ProjectList() {
    }

    /**
     * 
     * @param statusId
     * @param id
     * @param project
     * @param statusName
     * @param userId
     * @param companyId
     * @param projectId
     */
    public ProjectList(Integer id, Integer projectId, Integer userId, Integer companyId, Integer statusId, String statusName, Project project) {
        super();
        this.id = id;
        this.projectId = projectId;
        this.userId = userId;
        this.companyId = companyId;
        this.statusId = statusId;
        this.statusName = statusName;
        this.project = project;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("projectId", projectId).append("userId", userId).append("companyId", companyId).append("statusId", statusId).append("statusName", statusName).append("project", project).toString();
    }

}
