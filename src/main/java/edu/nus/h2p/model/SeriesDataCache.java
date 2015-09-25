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
    private Map<Long, VolumeDomainObject> fullSeries;
    private Map<Long, VolumeDomainObject> querySeries;

    public Map<Long, VolumeDomainObject> getFullSeries() {
        return fullSeries;
    }

    public void setFullSeries(Map<Long, VolumeDomainObject> fullSeries) {
        this.fullSeries = fullSeries;
    }

    public Map<Long, VolumeDomainObject> getQuerySeries() {
        return querySeries;
    }

    public void setQuerySeries(Map<Long, VolumeDomainObject> querySeries) {
        this.querySeries = querySeries;

    }
}
