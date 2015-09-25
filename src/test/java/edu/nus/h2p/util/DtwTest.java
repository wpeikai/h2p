package edu.nus.h2p.util;


import edu.nus.h2p.util.dtw.DtwUtil;
import edu.nus.h2p.util.dtw.IDtw;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:Spring-Module-Test.xml"})
public class DtwTest {

    @Autowired
    @Qualifier("normalDtw")
    IDtw normalDtw;

    @Autowired
    @Qualifier("lbKeoghDwt")
    IDtw lbKeoghDwt;


    @Test
    public void testNormalDtw(){
        final double DELTA = 0.01;
        double[] n2 = {1.5f, 3.9f, 4.1f, 3.3f};
        double[] n1 = {2.1f, 2.45f, 3.673f, 4.32f, 2.05f, 1.93f, 5.67f, 6.01f};
        double distance = normalDtw.compute(n1, n2);
        Assert.assertEquals(2.22035, distance, DELTA);
    }

    @Test
    public void testLbKeoghDtw(){
        final double DELTA = 0.01;
        double[] q1 = {2.11f, 2.46f, 3.673f, 4.32f, 2.05f, 1.93f, 5.67f, 6.01f};
        double[] q2 = {5.1f, 1.45f, 1.673f, 4.32f, 2.05f, 1.93f, 3.67f, 3.01f};
        double[] c = {2.1f, 2.45f, 3.673f, 4.32f, 2.05f, 1.93f, 5.67f, 6.01f};
        double distance1 = lbKeoghDwt.compute(c, q1);
        Assert.assertEquals(0.00, distance1, DELTA);
        System.out.println("distance1: " + distance1);
        double distance2 = lbKeoghDwt.compute(c, q2);
        Assert.assertNotEquals(0.00, distance2, DELTA);
        System.out.println("distance2: " + distance2);
    }

}
