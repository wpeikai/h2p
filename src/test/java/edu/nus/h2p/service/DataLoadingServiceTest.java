package edu.nus.h2p.service;

import com.google.gson.Gson;
import edu.nus.h2p.domain.HubwayTrip;
import edu.nus.h2p.model.Series;
import edu.nus.h2p.model.SeriesDataCache;
import edu.nus.h2p.model.VolumeDomainObject;
import edu.nus.h2p.model.VolumeItem;
import edu.nus.h2p.util.DateUtil;
import edu.nus.h2p.util.GsonUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created by Hao on 22/9/2015.
 * Test Data Loading Service
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:Spring-Module-Test.xml"})
@Transactional
@Rollback
public class DataLoadingServiceTest {
    @Autowired
    private DataLoadingService dataLoadingService;
    @Autowired
    private SeriesDataCache seriesDataCache;

    @Test
    public void testCreateVolumeDomainList() {
        int drawbackStep = 48;
        dataLoadingService.createVolumeDomainList(drawbackStep);
        Series dataSeries = seriesDataCache.getFullSeries();
        Series querySeries = seriesDataCache.getQuerySeries();
        Assert.assertEquals(dataSeries.getStartTime(), querySeries.getStartTime());
        Assert.assertEquals(dataSeries.getInterval(), querySeries.getInterval());
        Assert.assertTrue(dataSeries.getLength() > querySeries.getLength());
        List<VolumeItem> dataItems = dataSeries.getDataSeries();
        List<VolumeItem> queryItems = querySeries.getDataSeries();
        for (int i=0; i< querySeries.getLength(); i++) {
            Assert.assertEquals(dataItems.get(i).getValue(), queryItems.get(i).getValue());
        }
 //       GsonUtil.writeObjectToJsonFile("D:\\workingFolder\\project\\h2p\\src\\main\\resources\\query.json", querySeries);
    }

    @Test
    public void testLoadData() {
        Date startTime = new Date();
        Date startDate = DateUtil.parseDateByFormat("20120801");
        Date endDate = DateUtil.parseDateByFormat("20120802");
        List<HubwayTrip> rawData = dataLoadingService.loadRawData(startDate, endDate);
        Date endTime = new Date();
        System.out.println("processing takes " + (double) (endTime.getTime() - startTime.getTime()) / 1000 + "seconds");
        Assert.assertFalse(rawData.isEmpty());

    }

}
