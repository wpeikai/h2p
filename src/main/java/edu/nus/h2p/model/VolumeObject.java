package edu.nus.h2p.model;

/**
 * Created by Hao on 20/9/2015.
 */
public class VolumeObject {
    private int key;
    private IData data;

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public IData getData() {
        return data;
    }

    public void setData(IData data) {
        this.data = data;
    }
}
