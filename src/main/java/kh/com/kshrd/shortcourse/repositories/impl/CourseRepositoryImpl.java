package kh.com.kshrd.shortcourse.repositories.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import kh.com.kshrd.shortcourse.models.Course;
import kh.com.kshrd.shortcourse.repositories.CourseRepository;
import kh.com.kshrd.shortcourse.utilities.Pagination;

@Repository
public class CourseRepositoryImpl implements CourseRepository{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<Course> findAll(Pagination pagination) {
		String sql = "SELECT A.id " 
				   + " 	   , (SELECT STRING_AGG(BB.NAME,',') "   
				   + "		FROM COURSE_DETAILS AA " 
				   + "		INNER JOIN SHIFTS BB ON AA.SHIFT = BB.ID " 
				   + "		GROUP BY AA.COURSE_ID "  
				   + "	) AS SHIFT "
				   + "	   , A.course "
				   + "	   , A.description "
				   + "	   , A.generation "
				   + "	   , A.created_date "
				   + "	   , A.status "
				   + "     , A.cost "
				   + "     , A.discount "
				   + "FROM COURSES A ";
		return jdbcTemplate.query(sql, new RowMapper<Course>(){
			@Override
			public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
				Course course = new Course();
				course.setId(rs.getLong("id"));
				return course;
			}
		});
	}
}
