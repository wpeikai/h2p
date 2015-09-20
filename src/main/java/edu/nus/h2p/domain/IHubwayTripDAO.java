package edu.nus.h2p.domain;

import java.util.Date;
import java.util.List;


public interface IHubwayTripDAO {
    List<HubwayTrip> getHubwayTripInTimeRange(Date startTime, Date endTime);
}
