package kh.com.kshrd.shortcourse.repositories.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import kh.com.kshrd.shortcourse.models.CourseType;
import kh.com.kshrd.shortcourse.repositories.CourseTypeRepository;

@Repository
public class CourseTypeRepositoryImpl implements CourseTypeRepository {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<CourseType> findAll() throws SQLException{
		String sql = "SELECT id "
				   + "	   , name "
				   + "FROM course_types "
				   + "WHERE status = '1'";
		
		return jdbcTemplate.query(
				sql,
				new RowMapper<CourseType>(){
			@Override
			public CourseType mapRow(ResultSet rs, int rowNum) throws SQLException {
				CourseType course = new CourseType();
				course.setId(rs.getLong("id"));
				course.setName(rs.getString("name"));
				return course;
			}
		});
	}
}