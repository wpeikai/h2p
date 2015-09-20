package edu.nus.h2p.model;

/**
 * Created by Hao on 20/9/2015.
 */
public interface IData<D> {
    D getValue();
    void setValue(D data);
    void increaseValue();
}
