package edu.nus.h2p.service;

import edu.nus.h2p.model.VolumeDomainObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

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

    @Test
    public void testLoadData(){
        Date startTime = new Date();
        Map<Integer, Map<Long, VolumeDomainObject>> map = dataLoadingService.createVolumeDomainList();
        Date endTime = new Date();
        System.out.println("processing takes " + (double)(endTime.getTime() - startTime.getTime())/1000 + "seconds");
        Assert.assertFalse(map.isEmpty());
    }
}
