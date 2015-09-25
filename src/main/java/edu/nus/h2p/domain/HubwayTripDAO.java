package edu.nus.h2p.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

/**
 * Created by Hao on 20/9/2015.
 * Dao to load data from hubway table
 */
@Repository
public class HubwayTripDAO extends JdbcDaoSupport implements IHubwayTripDAO {
    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    @Override
    public List<HubwayTrip> getHubwayTripInTimeRangeAndStations(Date startTime, Date endTime, int  startStation, int endStation){
        final String sql = "SELECT start_date, end_date, strt_statn, end_statn FROM hubway.trips where start_date > ? and start_date < ? and strt_statn = ? and end_statn = ?";
        return getJdbcTemplate().query(sql,new Object[]{startTime, endTime, startStation, endStation}, new HubwayTripRowMapper());
    }

    @Override
    public List<HubwayTrip> getHubwayTripInTimeRange(Date startTime, Date endTime) {
        final String sql = "SELECT start_date, end_date, strt_statn, end_statn FROM hubway.trips where start_date > ? and start_date < ?";
        return getJdbcTemplate().query(sql,new Object[]{startTime, endTime}, new HubwayTripRowMapper());
    }
}
