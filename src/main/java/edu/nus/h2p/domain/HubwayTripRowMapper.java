package edu.nus.h2p.domain;


import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HubwayTripRowMapper implements RowMapper<HubwayTrip> {

    @Override
    public HubwayTrip mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        HubwayTrip hubwayTrip= new HubwayTrip();
        hubwayTrip.setStartStation(resultSet.getInt("strt_statn"));
        hubwayTrip.setEndStation(resultSet.getInt("end_statn"));
        hubwayTrip.setStartTime(resultSet.getDate("start_date"));
        hubwayTrip.setEndTime(resultSet.getDate("end_date"));
        return hubwayTrip;
    }
}
