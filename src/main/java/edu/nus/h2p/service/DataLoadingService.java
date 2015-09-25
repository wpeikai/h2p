package edu.nus.h2p.service;

import edu.nus.h2p.domain.HubwayTrip;
import edu.nus.h2p.domain.IHubwayTripDAO;
import edu.nus.h2p.model.SeriesDataCache;
import edu.nus.h2p.model.VolumeDomainObject;
import edu.nus.h2p.model.VolumeItem;
import edu.nus.h2p.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    @PostConstruct
    public void init(){
        sampleInterval = sampleIntervalInMin * DateUtil.MINUTE_IN_MINI_SECOND;
    }

    public void createVolumeDomainList(int ensembleLength)  {
        Date startDate = DateUtil.parseDateByFormat(startDateStr);
        Date queryEndDate = DateUtil.parseDateByFormat(endDateStr);
        long backwardStepDateValue = sampleInterval * ensembleLength;
        Date endDate = DateUtil.addTime(queryEndDate, backwardStepDateValue);
        List<HubwayTrip> hubwayTrips = loadRawData(startDate, endDate);
        Map<Long, VolumeDomainObject> dataSeries = buildMapByRawData(hubwayTrips);
        addDataToCache(dataSeries, ensembleLength, endDate);
    }

    public void createVolumeDomainList(int startStn, int endStn, int ensembleLength)  {
        Date startDate = DateUtil.parseDateByFormat(startDateStr);
        Date queryEndDate = DateUtil.parseDateByFormat(endDateStr);
        long backwardStepDateValue = sampleInterval * ensembleLength;
        Date endDate = DateUtil.addTime(queryEndDate, backwardStepDateValue);
        List<HubwayTrip> hubwayTrips = loadRawData(startDate, endDate, startStn, endStn);
        Map<Long, VolumeDomainObject> dataSeries = buildMapByRawData(hubwayTrips);
        addDataToCache(dataSeries, ensembleLength, endDate);
    }

    public List<HubwayTrip> loadRawData(Date startDate,Date endDate, int startStn, int endStn){
        return hubwayTripDAO.getHubwayTripInTimeRangeAndStations(startDate, endDate, startStn, endStn);
    }

    public List<HubwayTrip> loadRawData(Date startDate,Date endDate){
        return hubwayTripDAO.getHubwayTripInTimeRange(startDate, endDate);
    }

    public Map<Long, VolumeDomainObject> buildMapByRawData(List<HubwayTrip> hubwayTripList){
        Map<Long, VolumeDomainObject> timeVolumeMap = new HashMap<>();
        for(HubwayTrip hubwayTrip: hubwayTripList){
            long rawTime = hubwayTrip.getStartTime().getTime();
            long time = rawTime - (rawTime)%sampleInterval;
            while (time <= hubwayTrip.getEndTime().getTime()){
                VolumeDomainObject vdo;
                if(!timeVolumeMap.containsKey(time)){
                    vdo = new VolumeDomainObject();
                    vdo.setTime(new Date(time));
                    vdo.setData(new VolumeItem());
                    ((VolumeItem)vdo.getData()).setValue(1);
                    timeVolumeMap.put(time, vdo);
                }else{
                    vdo = timeVolumeMap.get(time);
                    vdo.getData().increaseValue();
                }
                time += sampleInterval;
            }
        }
        return timeVolumeMap;
    }

    private void addDataToCache(Map<Long, VolumeDomainObject> series, int backwardStep, Date endTime) {
        seriesDataCache.setFullSeries(series);
        Map<Long, VolumeDomainObject> querySeries = new HashMap<>(series);
        long endTimeValue = endTime.getTime();
        for(int i=0; i< backwardStep; i++){
            long currTimeValue = endTimeValue - i*sampleInterval;
            querySeries.remove(currTimeValue);
        }
        seriesDataCache.setQuerySeries(querySeries);
    }

}
