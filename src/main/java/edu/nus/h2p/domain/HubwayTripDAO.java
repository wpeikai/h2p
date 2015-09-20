package edu.nus.h2p.domain;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by Hao on 20/9/2015.
 * Dao to load data from hubway table
 */
@Repository
public class HubwayTripDAO extends JdbcDaoSupport implements IHubwayTripDAO {
    @Override
    public List<HubwayTrip> getHubwayTripInTimeRange(Date startTime, Date endTime){
        final String sql = "SELECT start_date, end_date, strt_statn, end_statn FROM hubway.trip where start_date > ? and start_date < ?";
        return getJdbcTemplate().query(sql,new Object[]{startTime, endTime}, new HubwayTripRowMapper());

    }
}
