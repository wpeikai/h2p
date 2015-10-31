package edu.nus.h2p.service;


import java.util.List;

import edu.nus.h2p.model.PredictOutput;
import edu.nus.h2p.model.Series;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PredictorService {
	
	
	public PredictOutput predict (Series inputSeries, List <Series> kNNSeries, List <Double> kNNValues) {
		PredictOutput po = new PredictOutput();
		double mean, variance = 0;
		double sum = 0;
		int size = kNNValues.size();
		for (int i=0; i<size; i++) {
			sum += kNNValues.get(i);
		}
		mean = sum/size;
		po.setMean(mean);
		for (int i=0; i<size; i++) {
			variance += (kNNValues.get(i) - mean)*(kNNValues.get(i) - mean);
		}
		variance = variance/size;
		po.setVariance(variance);
		return po;
	}
}
