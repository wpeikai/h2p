package edu.nus.h2p.service;

import edu.nus.h2p.model.SeriesDataCache;
import edu.nus.h2p.model.SlidingWindowDistanceItem;
import edu.nus.h2p.model.VolumeDomainObject;
import edu.nus.h2p.util.SeriesUtil;
import edu.nus.h2p.util.dtw.IDtw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Hao on 27/9/2015.
 * service to do series knn search
 */
@Service
public class KnnSearchService {
    @Autowired
    SeriesDataCache seriesDataCache;
    @Value("${param.window.length}")
    private int windowSize;

    @Autowired
    private IDtw lbKeoghDwt;
    public List<Map<Long, VolumeDomainObject>> querySimilarSeries(Map<Long, VolumeDomainObject> query){
        List<Map<Long, VolumeDomainObject>> similarSeriesCollection = new ArrayList<>();
        List<Map<Long, VolumeDomainObject>> querySlidingWindows = convertSeriesToSW(query);
        List<Map<Long, VolumeDomainObject>> dataSlidingWindows = convertSeriesToSW(seriesDataCache.getQuerySeries());
        Map<Integer, Map<Integer,SlidingWindowDistanceItem>> windowLevelIndexTable = buildWindowLevelIndexTable(querySlidingWindows, dataSlidingWindows);

        return similarSeriesCollection;
    }

    private Map<Integer, Map<Integer, SlidingWindowDistanceItem>> buildWindowLevelIndexTable(List<Map<Long, VolumeDomainObject>> querySlidingWindows, List<Map<Long, VolumeDomainObject>> dataSlidingWindows) {
        Map<Integer, Map<Integer,SlidingWindowDistanceItem>> windowLevelIndexTable = new HashMap<>();
        for(int d=0; d< dataSlidingWindows.size(); d++){
            Map<Integer,SlidingWindowDistanceItem> swIndexColumn = new HashMap<>();
            for(int q=0; q<querySlidingWindows.size(); q++){
                Map<Long, VolumeDomainObject> querySeries = querySlidingWindows.get(q);
                Map<Long, VolumeDomainObject> dataSeries = dataSlidingWindows.get(d);
                double[] queryValues = SeriesUtil.getSeriesValueArray(querySeries);
                double[] dataValues = SeriesUtil.getSeriesValueArray(dataSeries);
                double lowerBoundQuery = lbKeoghDwt.compute(queryValues, dataValues);
                double lowerBoundComponent = lbKeoghDwt.compute(dataValues, queryValues);
                SlidingWindowDistanceItem item = new SlidingWindowDistanceItem(q, d, lowerBoundQuery, lowerBoundComponent);
                swIndexColumn.put(q, item);
            }
            windowLevelIndexTable.put(d, swIndexColumn);
        }
        return windowLevelIndexTable;
    }

    public List<Map<Long, VolumeDomainObject>> convertSeriesToSW(Map<Long, VolumeDomainObject> series) {
        List<Map<Long, VolumeDomainObject>> swList = new ArrayList<>();
        List<Long> timeList = new ArrayList<>(series.keySet());
        Collections.sort(timeList);
        for(int i=0; i<timeList.size(); i+= windowSize){
            Map<Long, VolumeDomainObject> windowSizeSeries = new HashMap<>();
            for(int j=0; j<windowSize; j++){
                int index = i + j;
                Long key = timeList.get(index);
                windowSizeSeries.put(key, series.get(key));
            }
            swList.add(windowSizeSeries);
        }
        return swList;
    }
}
