package kh.com.kshrd.shortcourse.repositories.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import kh.com.kshrd.shortcourse.models.Shift;
import kh.com.kshrd.shortcourse.repositories.ShiftRepository;

@Repository
public class ShiftRepositoryImpl implements ShiftRepository{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<Shift> findAll() throws SQLException {
		String sql = "SELECT id " 
				   + "	   , name " 
				   + "FROM shifts " 
				   + "WHERE status = '1'";
		return jdbcTemplate.query(sql, new RowMapper<Shift>() {
			@Override
			public Shift mapRow(ResultSet rs, int rowNum) throws SQLException {
				Shift shift = new Shift();
				shift.setId(rs.getLong("id"));
				shift.setName(rs.getString("name"));
				return shift;
			}
		});
	}
}
