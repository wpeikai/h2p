package edu.nus.h2p.bean;

import edu.nus.h2p.model.IData;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Hao on 20/9/2015.
 * Data Cache is a spring bean to save the cache data for cross module access
 */
@Component
public class DataCache {
    private HashMap<Integer, List<IData>> sampleMap = new HashMap<>();

    public HashMap<Integer, List<IData>> getSampleMap() {
        return sampleMap;
    }
}
