
package com.bridge.pmt.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class AddResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private String data;

    /**
     * No args constructor for use in serialization
     *
     */
    public AddResponse() {
    }

    /**
     *
     * @param message
     * @param status
     * @param data
     */
    public AddResponse(Integer status, String message, String data) {
        super();
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("status", status).append("message", message).append("data", data).toString();
    }

}
