package edu.nus.h2p.service;

import edu.nus.h2p.domain.HubwayTrip;
import edu.nus.h2p.domain.IHubwayTripDAO;
import edu.nus.h2p.model.VolumeDomainObject;
import edu.nus.h2p.model.VolumeItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Hao on 20/9/2015.
 * DataLoadingService is for loading data from database
 */
@Service
public class DataLoadingService {
    @Autowired
    IHubwayTripDAO hubwayTripDAO;
    private static final DateFormat format = new SimpleDateFormat("yyyymmdd", Locale.ENGLISH);
    @Value("${startDate}")
    private String startDateStr;
    @Value("${endDate}")
    private String endDateStr;

    private static final long MINUTE_IN_MINI_SECOND = 60000;
    public Map<Integer, Map<Long, VolumeDomainObject>> createVolumeDomainList() throws ParseException {
        Map<Integer, Map<Long, VolumeDomainObject>> volumeDomainObjectMap = new HashMap<>();
        Date startDate = format.parse(startDateStr);
        Date endDate = format.parse(endDateStr);
        List<HubwayTrip> hubwayTrips = hubwayTripDAO.getHubwayTripInTimeRange(startDate, endDate);

        for(HubwayTrip hubwayTrip: hubwayTrips){
            int key = VolumeDomainObject.computeKey(hubwayTrip.getStartStation(), hubwayTrip.getEndStation());
            Map<Long, VolumeDomainObject> timeVolumeMap;
            if(!volumeDomainObjectMap.containsKey(key)){
                timeVolumeMap = new HashMap<>();
                volumeDomainObjectMap.put(key, timeVolumeMap);
            }else{
                timeVolumeMap = volumeDomainObjectMap.get(key);
            }

            long time = hubwayTrip.getStartTime().getTime();
            while (time <= hubwayTrip.getEndTime().getTime()){
                VolumeDomainObject vdo;
                if(!timeVolumeMap.containsKey(time)){
                    vdo = new VolumeDomainObject();
                    vdo.setKey(key);
                    vdo.setTime(new Date(time));
                    vdo.setData(new VolumeItem());
                    ((VolumeItem)vdo.getData()).setValue(1);
                    timeVolumeMap.put(time, vdo);
                }else{
                    vdo = timeVolumeMap.get(time);
                    vdo.getData().increaseValue();
                }
                time += MINUTE_IN_MINI_SECOND;
            }
        }
        return volumeDomainObjectMap;
    }
}
