package edu.nus.h2p.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hao on 28/9/2015.
 */
public class Series {
    private Map<Long, VolumeDomainObject> seriesMap = new HashMap<>();

    private void put(Long key, VolumeDomainObject value){
        seriesMap.put(key, value);
    }

    private int size(){
        return seriesMap.size();
    }



}
