package kh.com.kshrd.shortcourse.repositories.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import kh.com.kshrd.shortcourse.filtering.CourseFilter;
import kh.com.kshrd.shortcourse.models.Course;
import kh.com.kshrd.shortcourse.models.Generation;
import kh.com.kshrd.shortcourse.repositories.CourseRepository;
import kh.com.kshrd.shortcourse.utilities.Pagination;

@Repository
public class CourseRepositoryImpl implements CourseRepository{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<Course> findAll(CourseFilter filter, Pagination pagination) throws SQLException {
		pagination.setTotalCount(this.count(filter));
		String sql = "SELECT A.id " 
				   + " 	   , (SELECT STRING_AGG(BB.name,',') "   
				   + "		FROM course_details AA " 
				   + "		INNER JOIN shifts BB ON AA.shift = BB.id " 
				   + "		WHERE AA.course_id = A.id "  
				   + "		GROUP BY AA.course_id "
				   + "	) AS shift "
				   + "	   , A.course "
				   + "	   , A.description "
				   + "	   , B.id AS generation_id "
				   + "	   , B.generation "
				   + "	   , A.created_date "
				   + "	   , A.status "
				   + "     , A.cost "
				   + "     , A.discount "
				   + "FROM courses A "
				   + "LEFT JOIN generations B ON A.generation = B.id AND B.status = '1' "
				   + "LEFT JOIN users C ON A.created_by = C.id AND A.status = '1' "
				   + "LIMIT ? "
				   + "OFFSET ? ";
		return jdbcTemplate.query(
				sql,
				new Object[]{
						pagination.getLimit(), 
						pagination.offset()
				},
				new RowMapper<Course>(){
			@Override
			public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
				Course course = new Course();
				course.setId(rs.getLong("id"));
				course.setCourse(rs.getString("course"));
				course.setDescription(rs.getString("description"));
				course.setCreatedDate(rs.getString("created_date"));
				course.setStatus(rs.getString("status"));
				course.setDiscount(rs.getDouble("discount"));
				course.setCost(rs.getDouble("cost"));
				course.setShift(rs.getString("shift"));
				
				Generation generation = new Generation();
				generation.setId(rs.getLong("generation_id"));
				generation.setGeneration(rs.getString("generation"));
				course.setGeneration(generation);
				
				return course;
			}
		});
	}
	
	@Override
	public Long count(CourseFilter filter) throws SQLException {
		String sql = "SELECT COUNT(1) " 
				   + "FROM courses A "
				   + "WHERE A.status = '1'";
		return jdbcTemplate.queryForObject(
				sql, Long.class);
	}
}
