package edu.nus.h2p.service;

import edu.nus.h2p.model.Series;
import edu.nus.h2p.model.SeriesDataCache;
import edu.nus.h2p.model.SlidingWindowDistanceItem;
import edu.nus.h2p.util.GsonUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Hao on 3/10/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:Spring-Module-Test.xml"})
@Transactional
@Rollback
public class KnnSearchServiceTest {
    Series query;
    @Autowired
    SeriesDataCache dataCache;
    @Autowired
    KnnSearchService knnSearchService;
    @Value("${test.query.path}")
    private String testQueryPath;
    @Value("${test.query.series.path}")
    private String testQuerySeriesPath;
    @Before
    public void beforeTest(){
        Series querySeries = GsonUtil.readObjectToJsonFile(testQuerySeriesPath, Series.class);
        dataCache.setQuerySeries(querySeries);
        query = GsonUtil.readObjectToJsonFile(testQueryPath, Series.class);
    }

    @Test
    public void buildWindowLevelIndexTableTest(){
        SlidingWindowDistanceItem[][] indexTable = knnSearchService.buildWindowLevelIndexTable(query);
        Assert.assertNotNull(indexTable);
        Assert.assertFalse(indexTable.length == 0);
        Assert.assertFalse(indexTable[1].length == 0);
        for(SlidingWindowDistanceItem[] items : indexTable){
            for(SlidingWindowDistanceItem item: items){
                System.out.print(String.format("%10.0f", item.getLowerBoundComponent()) + " "  + String.format("%10.0f", item.getLowerBoundQuery()) + "| ");
            }
            System.out.println();
        }
    }

}
