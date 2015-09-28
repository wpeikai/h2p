package edu.nus.h2p.domain;

import java.util.Date;

/**
 * Created by Hao on 20/9/2015.
 *
 */
public class HubwayTrip {
    private Date startTime;
    private Date endTime;
    private int startStation;
    private int endStation;

    public HubwayTrip(){

    }

    public HubwayTrip(Date startTime, Date endTime, int startStation, int endStation) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.startStation = startStation;
        this.endStation = endStation;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getStartStation() {
        return startStation;
    }

    public void setStartStation(int startStation) {
        this.startStation = startStation;
    }

    public int getEndStation() {
        return endStation;
    }

    public void setEndStation(int endStation) {
        this.endStation = endStation;
    }
}
