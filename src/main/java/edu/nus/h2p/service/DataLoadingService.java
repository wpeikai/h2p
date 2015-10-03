package edu.nus.h2p.service;

import edu.nus.h2p.domain.HubwayTrip;
import edu.nus.h2p.domain.IHubwayTripDAO;
import edu.nus.h2p.model.*;
import edu.nus.h2p.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Created by Hao on 20/9/2015.
 * DataLoadingService is for loading data from database
 */
@Service
public class DataLoadingService {
    @Autowired
    IHubwayTripDAO hubwayTripDAO;
    @Autowired
    SeriesDataCache seriesDataCache;

    @Value("${start.date}")
    private String startDateStr;
    @Value("${end.date}")
    private String endDateStr;
    @Value("${param.sample.interval}")
    private long sampleIntervalInMin;

    private long sampleInterval;
    private int sampleNumber;
    private Date startDate;
    private Date endDate;
    @PostConstruct
    public void init(){
        sampleInterval = sampleIntervalInMin * DateUtil.MINUTE_IN_MINI_SECOND;
        startDate = DateUtil.parseDateByFormat(startDateStr);
        endDate = DateUtil.parseDateByFormat(endDateStr);
        sampleNumber = (int) ((endDate.getTime() - startDate.getTime())/sampleInterval);
    }

    public void createVolumeDomainList(int ensembleLength)  {
        long backwardStepDateValue = sampleInterval * ensembleLength;
        Date totalEndDate = DateUtil.addTime(endDate, backwardStepDateValue);
        List<HubwayTrip> hubwayTrips = loadRawData(startDate, totalEndDate);
        Series dataSeries = buildSeriesByRawData(hubwayTrips, ensembleLength);
        addDataToCache(dataSeries);
    }

    public void createVolumeDomainList(int startStn, int endStn, int ensembleLength)  {
        Date startDate = DateUtil.parseDateByFormat(startDateStr);
        Date queryEndDate = DateUtil.parseDateByFormat(endDateStr);
        long backwardStepDateValue = sampleInterval * ensembleLength;
        Date endDate = DateUtil.addTime(queryEndDate, backwardStepDateValue);
        List<HubwayTrip> hubwayTrips = loadRawData(startDate, endDate, startStn, endStn);
        Series dataSeries = buildSeriesByRawData(hubwayTrips, ensembleLength);
        addDataToCache(dataSeries);
    }

    public List<HubwayTrip> loadRawData(Date startDate,Date endDate, int startStn, int endStn){
        return hubwayTripDAO.getHubwayTripInTimeRangeAndStations(startDate, endDate, startStn, endStn);
    }

    public List<HubwayTrip> loadRawData(Date startDate,Date endDate){
        return hubwayTripDAO.getHubwayTripInTimeRange(startDate, endDate);
    }

    public Series buildSeriesByRawData(List<HubwayTrip> hubwayTripList, int ensembleLength){
        Series dataSeries = new Series(startDate.getTime(), sampleInterval, sampleNumber + ensembleLength);
        for(HubwayTrip hubwayTrip: hubwayTripList){
            long rawTime = hubwayTrip.getStartTime().getTime();
            long time = rawTime - (rawTime)%sampleInterval;
            while (time < hubwayTrip.getEndTime().getTime()){
                dataSeries.increaseDataValue (time);
                time += sampleInterval;
            }
        }
        return dataSeries;
    }

    private void addDataToCache(Series series) {
        seriesDataCache.setFullSeries(series);
        Series dataSeries = new Series(startDate.getTime(), sampleInterval, sampleNumber, series.getDataSeries());
        seriesDataCache.setQuerySeries(dataSeries);
    }

}
