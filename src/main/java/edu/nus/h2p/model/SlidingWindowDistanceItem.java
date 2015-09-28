package edu.nus.h2p.model;

/**
 * Created by Hao on 28/9/2015.
 */
public class SlidingWindowDistanceItem {
    private final int swIndex;
    private final int sdIndex;
    private final double lowerBoundQuery;
    private final double lowerBoundComponent;

    public SlidingWindowDistanceItem(int swIndex, int sdIndex, double lowerBoundQuery, double lowerBoundComponent) {
        this.swIndex = swIndex;
        this.sdIndex = sdIndex;
        this.lowerBoundQuery = lowerBoundQuery;
        this.lowerBoundComponent = lowerBoundComponent;
    }

    public int getSwIndex() {
        return swIndex;
    }

    public int getSdIndex() {
        return sdIndex;
    }

    public double getLowerBoundQuery() {
        return lowerBoundQuery;
    }

    public double getLowerBoundComponent() {
        return lowerBoundComponent;
    }
}
