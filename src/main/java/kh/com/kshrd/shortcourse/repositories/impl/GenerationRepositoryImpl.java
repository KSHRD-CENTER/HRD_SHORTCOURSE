package kh.com.kshrd.shortcourse.repositories.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import kh.com.kshrd.shortcourse.models.Generation;
import kh.com.kshrd.shortcourse.repositories.GenerationRepository;

@Repository
public class GenerationRepositoryImpl implements GenerationRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Generation> findAll() throws SQLException {
		String sql = "SELECT id " 
				   + "	   , name " 
				   + "FROM generations " 
				   + "WHERE status = '1'";

		return jdbcTemplate.query(sql, new RowMapper<Generation>() {
			@Override
			public Generation mapRow(ResultSet rs, int rowNum) throws SQLException {
				Generation generation = new Generation();
				generation.setId(rs.getLong("id"));
				generation.setName(rs.getString("name"));
				return generation;
			}
		});
	}

	@Override
	public List<Generation> findAllByCourseTypeId(Long courseTypeId) throws SQLException {
		String sql = "SELECT id " 
				   + "	   , name " 
				   + "FROM generations " 
				   + "WHERE course_type = ? "
				   + "AND status = '1'";

		return jdbcTemplate.query(
				sql,
				new Object[]{
					courseTypeId	
				},
				new RowMapper<Generation>() {
			@Override
			public Generation mapRow(ResultSet rs, int rowNum) throws SQLException {
				Generation generation = new Generation();
				generation.setId(rs.getLong("id"));
				generation.setName(rs.getString("name"));
				return generation;
			}
		});
	}
	
	

}
