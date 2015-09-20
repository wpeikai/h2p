package edu.nus.h2p.model;

/**
 * Created by Hao on 20/9/2015.
 * VolumeItem is a value object with the detailed path volume data
 */
public class VolumeItem implements IData<Integer>{
    private Integer data;

    @Override
    public Integer getValue() {
        return data;
    }

    @Override
    public void setValue(Integer data) {
        this.data = data;
    }

    @Override
    public void increaseValue() {
        data++;
    }
}
