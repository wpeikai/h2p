package edu.nus.h2p.service;

import edu.nus.h2p.domain.HubwayTrip;
import edu.nus.h2p.model.SeriesDataCache;
import edu.nus.h2p.model.VolumeDomainObject;
import edu.nus.h2p.util.DateUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by Hao on 22/9/2015.
 * Test Data Loading Service
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:Spring-Module-Test.xml"})
@Transactional
@Rollback
public class DataLoadingServiceTest {
    @Autowired
    private DataLoadingService dataLoadingService;
    @Autowired
    private SeriesDataCache seriesDataCache;
    @Test
    public void testCreateVolumeDomainList(){
        int startStn = 22;
        int endStn = 20;
        int drawbackStep = 48;
        dataLoadingService.createVolumeDomainList(drawbackStep);
        Assert.assertFalse(seriesDataCache.getFullSeries().isEmpty());
        Assert.assertFalse(seriesDataCache.getQuerySeries().isEmpty());
        System.out.println("seriesDataCache.getFullSeries() size: " + seriesDataCache.getFullSeries().size());
        System.out.println("seriesDataCache.getQuerySeries() size: " + seriesDataCache.getQuerySeries().size());
    }

    @Test
    public void testLoadData(){
        Date startTime = new Date();
        int startStn = 22;
        int endStn = 20;
        Date startDate = DateUtil.parseDateByFormat("20120801");
        Date endDate = DateUtil.parseDateByFormat("20120802");
        List<HubwayTrip> rawData = dataLoadingService.loadRawData(startDate, endDate);
        Date endTime = new Date();
        System.out.println("processing takes " + (double)(endTime.getTime() - startTime.getTime())/1000 + "seconds");
        Assert.assertFalse(rawData.isEmpty());
    }

    @Test
    public void testBuildMap(){
        List<HubwayTrip> rawData = new ArrayList<>(4);
        rawData.add(new HubwayTrip(DateUtil.parseDateByFormat("20120801"), DateUtil.parseDateByFormat("20120803"), 1, 2));
        rawData.add(new HubwayTrip(DateUtil.parseDateByFormat("20120801"), DateUtil.parseDateByFormat("20120804"), 1, 2));

        Map<Long, VolumeDomainObject> map = dataLoadingService.buildMapByRawData(rawData);
        Collection<VolumeDomainObject> values = map.values();
        List<VolumeDomainObject> valueList = new ArrayList<>(values);
        Collections.sort(valueList, new Comparator<VolumeDomainObject>() {
            @Override
            public int compare(VolumeDomainObject o1, VolumeDomainObject o2) {
                return (int) (o1.getTime().getTime() - o2.getTime().getTime());
            }
        });

        Assert.assertFalse(map.isEmpty());
        //24*2 hours/day * 3 day + 1 extra data point.. = 145
 //       Assert.assertTrue(map.size() == 145);
    }

}
