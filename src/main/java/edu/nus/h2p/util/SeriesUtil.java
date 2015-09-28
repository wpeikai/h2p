package edu.nus.h2p.util;

import edu.nus.h2p.model.VolumeDomainObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by Hao on 28/9/2015.
 * Series utilities methods
 */
public class SeriesUtil {
    public static double [] getSeriesValueArray(Map<Long, VolumeDomainObject> valueMap){
        double[] values = new double[valueMap.size()];
        List<Long> timeList = new ArrayList<>(valueMap.keySet());
        Collections.sort(timeList);
        for(int i=0; i< values.length; i++){
            values[i] = (double)valueMap.get(timeList.get(i)).getData().getValue();
        }
        return values;
    }
}
