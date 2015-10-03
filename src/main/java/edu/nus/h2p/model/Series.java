package edu.nus.h2p.model;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Hao on 28/9/2015.
 * Series object
 */
public class Series {
    private static Logger log = Logger.getLogger(Series.class.getName());
    private List<VolumeItem> dataSeries;
    private long startTime;
    private long interval;
    private int length;

    public Series(long startTime, long interval, int length){
        this.startTime = startTime;
        this.interval = interval;
        this.length = length;
        dataSeries = new ArrayList<>(length);
        for(int i=0; i<length; i++){
            dataSeries.add(new VolumeItem());
        }
    }

    public Series(long startTime, long interval, int length, List<VolumeItem> data){
        this.startTime = startTime;
        this.interval = interval;
        this.length = length;
        dataSeries = new ArrayList<>(length);
        for(int i=0; i<length; i++){
            dataSeries.add(data.get(i));
        }
    }

    public void increaseDataValue(long time){
        int index = getIndex(time);
        if(index <0 || index>= length){
            log.info("input time is out of range: index=" + index + ", time=" + time);
            return;
        }
        VolumeItem data = dataSeries.get(index);
        data.increaseValue();
    }

    public List<Series> convertToSubSeriesList(int windowSize){
        List<Series> subSeries = new ArrayList<>(length/windowSize);
        for(int i=0; i<length; i+= windowSize){
            long subStartTime = this.startTime + i*interval;
            if(i + windowSize > length){
                break;
            }
            Series windowSeries = new Series(subStartTime, interval, windowSize, dataSeries.subList(i, i+windowSize));
            subSeries.add(windowSeries);
        }
        return subSeries;
    }

    public double[] getValueDoubleArray(){
        double[] valueArray = new double[length];
        for(int i=0; i<length; i++){
            valueArray[i] = (double)dataSeries.get(i).getValue();
        }
        return valueArray;
    }


    public int getIndex(long time){
        return (int) ((time - startTime)/interval);
    }


    public List<VolumeItem> getDataSeries() {
        return dataSeries;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getInterval() {
        return interval;
    }

    public int getLength() {
        return length;
    }

    public void setDataSeries(List<VolumeItem> dataSeries) {
        this.dataSeries = dataSeries;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
