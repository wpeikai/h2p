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
    private int key;

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

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public static int computeKey(int start, int end){
        return start * 1000 + end;
    }
}
