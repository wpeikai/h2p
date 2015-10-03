package edu.nus.h2p.model;

import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hao on 25/9/2015.
 */
@Component
public class SeriesDataCache {
    private Series fullSeries;
    private Series querySeries;

    public Series getFullSeries() {
        return fullSeries;
    }

    public void setFullSeries(Series fullSeries) {
        this.fullSeries = fullSeries;
    }

    public Series getQuerySeries() {
        return querySeries;
    }

    public void setQuerySeries(Series querySeries) {
        this.querySeries = querySeries;

    }
}
