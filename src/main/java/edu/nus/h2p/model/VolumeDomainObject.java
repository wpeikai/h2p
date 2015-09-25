package edu.nus.h2p.model;

import edu.nus.h2p.model.IData;

import java.util.Date;

/**
 * Created by Hao on 20/9/2015.
 * Object to save data
 */
public class VolumeDomainObject {
    private Date time;
    private IData data;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public IData getData() {
        return data;
    }

    public void setData(IData data) {
        this.data = data;
    }

    @Override
    public String toString(){
        return "time: " + time.toString() + ", data:" + (data!=null?data.getValue():-1);
    }
}
