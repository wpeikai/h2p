package edu.nus.h2p.util.dtw;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class LbKeoghDwt implements IDtw {
    @Value("${param.wrapping.width}")
    private int wrappingWidth;

    /**
     * This method compute the LB_keogh of two time sequence
     * the require the length of c and q are equal!
     * @param c original time sequence
     * @param q query time sequence
     *
     */
    @Override
    public double compute(double[] c, double[] q) {
        validate(c, q);
        int n = c.length;
        double accumulatedDistance = 0.0;
        for (int i = 0; i < n; i++) {
            double upperBound = getBound(c, i, true);
            double lowerBound = getBound(c, i, false);
            double distance;
            if(q[i] > upperBound){
                distance = DtwUtil.distanceBetween(upperBound, q[i]);
            }else if(q[i] < lowerBound){
                distance = DtwUtil.distanceBetween(lowerBound, q[i]);
            }else {
                distance = 0.0;
            }
            accumulatedDistance += distance;

        }
        return accumulatedDistance;
    }

    private void validate(double[] c, double[] q){
        if(c == null || q == null || c.length != q.length){
            throw new IllegalArgumentException("LB_keogh is only able to calculated when " +
                    "given two sequence with same length!");
        }
    }

    public double getBound(double[] c, int index, boolean upper){
        int lowerIndex = index - wrappingWidth>0?index - wrappingWidth:0;
        int upperIndex = index + wrappingWidth<c.length?index + wrappingWidth:c.length;
        double bound = c[lowerIndex];
        for(int i = lowerIndex + 1; i< upperIndex ; i++){
            if(upper && bound < c[i] || !upper && bound > c[i] ){
                bound = c[i];
            }
        }
        return bound;
    }
}
