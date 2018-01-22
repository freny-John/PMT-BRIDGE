
package com.bridge.pmt.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class WeekReport {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("hourDetails")
    @Expose
    private List<HourDetail> hourDetails = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public WeekReport() {
    }

    /**
     * 
     * @param hourDetails
     * @param date
     */
    public WeekReport(String date, List<HourDetail> hourDetails) {
        super();
        this.date = date;
        this.hourDetails = hourDetails;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<HourDetail> getHourDetails() {
        return hourDetails;
    }

    public void setHourDetails(List<HourDetail> hourDetails) {
        this.hourDetails = hourDetails;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("date", date).append("hourDetails", hourDetails).toString();
    }

}
