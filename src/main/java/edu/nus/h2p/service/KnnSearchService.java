package edu.nus.h2p.service;

import edu.nus.h2p.model.Series;
import edu.nus.h2p.model.SeriesDataCache;
import edu.nus.h2p.model.SlidingWindowDistanceItem;
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
    public List<Series> querySimilarSeries(Series querySeries){
        List<Series> similarSeriesCollection = new ArrayList<>();
        SlidingWindowDistanceItem[][] windowLevelIndexTable = buildWindowLevelIndexTable(querySeries);

        return similarSeriesCollection;
    }

    public SlidingWindowDistanceItem[][] buildWindowLevelIndexTable(Series series) {
        List<Series> querySWSeries = series.convertToSubSeriesList(windowSize);
        List<Series> dataSWSeries = seriesDataCache.getQuerySeries().convertToSubSeriesList(windowSize);
        SlidingWindowDistanceItem[][] indexTable = new SlidingWindowDistanceItem[querySWSeries.size()][dataSWSeries.size()];
        for(int d=0; d< dataSWSeries.size(); d++){
            //LOOP FOR QUERY WINDOW
            for(int q=0; q< querySWSeries.size(); q++){
                Series querySeries = querySWSeries.get(q);
                Series dataSeries = dataSWSeries.get(d);
                double[] queryValues = querySeries.getValueDoubleArray();
                double[] dataValues = dataSeries.getValueDoubleArray();
                double lowerBoundQuery = lbKeoghDwt.compute(queryValues, dataValues);
                double lowerBoundComponent = lbKeoghDwt.compute(dataValues, queryValues);
                SlidingWindowDistanceItem item = new SlidingWindowDistanceItem(q, d, lowerBoundQuery, lowerBoundComponent);
                indexTable[q][d] = item;
            }
        }
        return indexTable;
    }
}
