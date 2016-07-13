package kh.com.kshrd.shortcourse.repositories.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import kh.com.kshrd.shortcourse.filtering.CourseFilter;
import kh.com.kshrd.shortcourse.models.Course;
import kh.com.kshrd.shortcourse.models.CourseDetails;
import kh.com.kshrd.shortcourse.models.CourseType;
import kh.com.kshrd.shortcourse.models.Generation;
import kh.com.kshrd.shortcourse.models.Shift;
import kh.com.kshrd.shortcourse.repositories.CourseRepository;
import kh.com.kshrd.shortcourse.utilities.Pagination;

@Repository
public class CourseRepositoryImpl implements CourseRepository{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<Course> findAll(CourseFilter filter, Pagination pagination) throws SQLException {
		pagination.setTotalCount(this.count(filter));
		String sql = "SELECT DISTINCT A.id " 
				   + " 	   , (SELECT STRING_AGG(CONCAT('(START DATE: ',AA.start_date, ') SHIFT: ' ,BB.name),',') "   
				   + "		FROM course_details AA " 
				   + "		INNER JOIN shifts BB ON AA.shift = BB.id " 
				   + "		WHERE AA.course_id = A.id "
				   + "	    AND AA.status = '1' "  
				   + "		GROUP BY AA.course_id "
				   + "	) AS shift "
				   + "	   , A.course "
				   + "	   , A.description "
				   + "	   , B.id AS generation_id "
				   + "	   , B.name "
				   + "	   , A.created_date "
				   + "	   , A.status "
				   + "     , A.cost "
				   + "     , A.discount"
				   + "	   , (A.cost - (A.cost * A.discount / 100)) AS paid_amount "
				   + "FROM courses A "
				   + "LEFT JOIN generations B ON A.generation = B.id AND B.status = '1' "
				   + "LEFT JOIN course_types C ON C.id = B.course_type AND C.status = '1' "
				   + "LEFT JOIN users D ON A.created_by = D.id AND D.status = '1' "
				   + "LEFT JOIN course_details E ON A.id = E.course_id AND E.status = '1' "
				   + "LEFT JOIN shifts F ON F.id = E.shift AND F.status = '1' "
				   + "WHERE LOWER(A.course) LIKE LOWER(?) "
				   + "AND C.id::TEXT LIKE ? "
				   + "AND B.id::TEXT LIKE ? "
				   + "AND E.course_id::TEXT LIKE ? "
				   + "AND LOWER(E.shift::TEXT) LIKE LOWER(?) "
				   + "AND A.status = '1' "
				   + "ORDER BY 1"
				   + "LIMIT ? "
				   + "OFFSET ? ";
		
		System.out.println(sql);
		List<Course> courses = jdbcTemplate.query(
				sql,
				new Object[]{
						"%" + filter.getCourse() + "%",
						"%" + filter.getCourseTypeId() + "%",
						"%" + filter.getGenerationId() + "%",
						"%" + filter.getCourseId() + "%",
						"%" + filter.getShiftId() + "%",
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
				course.setPaidAmount(rs.getDouble("paid_amount"));
				
				Generation generation = new Generation();
				generation.setId(rs.getLong("generation_id"));
				generation.setName(rs.getString("name"));
				course.setGeneration(generation);
				
				return course;
			}
		});
		
		for(Course course : courses){
			course.setCourseDetails(this.findAllCourseDetailsByCourseId(course.getId()));
		}
		return courses;
	}
	
	@Override
	public Long count(CourseFilter filter) throws SQLException {
		System.out.println("COUNTING===>"+filter);
		String sql = "SELECT COUNT(DISTINCT A.id) " 
				   + "FROM courses A "
				   + "LEFT JOIN generations B ON A.generation = B.id AND B.status = '1' "
				   + "LEFT JOIN course_types C ON C.id = B.course_type AND C.status = '1' "
				   + "LEFT JOIN course_details E ON A.id = E.course_id AND E.status = '1' "
				   + "LEFT JOIN shifts F ON F.id = E.shift AND F.status = '1' "
				   + "WHERE LOWER(A.course) LIKE LOWER(?) "
				   + "AND C.id::TEXT LIKE LOWER(?) "
				   + "AND B.id::TEXT LIKE LOWER(?) "
				   + "AND E.course_id::TEXT LIKE LOWER(?) "
				   + "AND LOWER(E.shift::TEXT) LIKE LOWER(?) "
				   + "AND A.status = '1' ";
		return jdbcTemplate.queryForObject(
				sql,
				new Object[]{
					"%" + filter.getCourse() + "%",
					"%" + filter.getCourseTypeId() + "%",
					"%" + filter.getGenerationId() + "%",
					"%" + filter.getCourseId() + "%",
					"%" + filter.getShiftId() + "%",
				},
				Long.class);
	}

	@Override
	public List<Course> findAllByGenerationId(Long generationId) {
		String sql = "SELECT A.id " 
				   + "	   , A.course " 
				   + "	   , A.cost "
				   + "FROM courses A "
				   + "WHERE A.generation = ? "
				   + "AND A.status = '1' ";
		return jdbcTemplate.query(
				sql,
				new Object[]{
					generationId
				},
				new RowMapper<Course>(){
			@Override
			public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
				Course course = new Course();
				course.setId(rs.getLong("id"));
				course.setCourse(rs.getString("course"));
				course.setCost(rs.getDouble("cost"));
				return course;
			}
		});
	}

	@Override
	public Long save(Course course) throws SQLException {
		try{
			Long id = jdbcTemplate.queryForObject("SELECT nextval('courses_id_seq')",Long.class);
			String sql = "INSERT INTO courses(id "
					   + "		, course "
					   + "		, description "
					   + "		, generation "
					   + "		, created_date "
					   + "		, status "
					   + "		, created_by "
					   + "		, cost "
					   + "		, discount"
					   + "		, total_hours)"
					   + "VALUES(?, ?, ?, ?, TO_CHAR(NOW(),'YYYYMMDDHH24MISS'), '1', ?, ?, ?, ?) ";
			
			int result = jdbcTemplate.update(
					sql,
					new Object[]{
						id, 
						course.getCourse(),
						course.getDescription(),
						course.getGeneration().getId(),
						course.getCreatedBy().getId(),
						course.getCost(),
						course.getDiscount(),
						course.getTotalHour()
					});
			if(result>0){
				System.out.println(id);
				return id;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public int[] save(List<CourseDetails> courseDetails, Long courseId) throws SQLException {
		try{
			String sql = "INSERT INTO course_details("
												  + "course_id, "
												  + "shift, "
												  + "created_date, "
												  + "status, "
												  + "created_by, "
												  + "start_date) "
						 + "VALUES(?, ?, TO_CHAR(NOW(),'YYYYMMDDHH24MMSS'), '1', ?, ?);";
			int results[]= jdbcTemplate.batchUpdate(sql,new BatchPreparedStatementSetter() {
				    @Override
				    public void setValues(PreparedStatement ps, int i) throws SQLException {
				    	ps.setLong(1, courseId);
				    	ps.setLong(2, courseDetails.get(i).getShift().getId());
				    	ps.setLong(3, courseDetails.get(i).getCreatedBy().getId());
				    	ps.setString(4,  courseDetails.get(i).getStartDate());
				    }
				    
				    @Override
				    public int getBatchSize() {
				        return courseDetails.size();
				    }
			  });
			return results;
		}catch(org.springframework.dao.DataIntegrityViolationException  ex)
        {
            System.out.println("data integrity ex="+ex.getMessage());
            Throwable innerex = ex.getMostSpecificCause();
            if(innerex instanceof java.sql.BatchUpdateException)
            {
                java.sql.BatchUpdateException batchex = (java.sql.BatchUpdateException) innerex ;
                SQLException current = batchex;
                int count=1;
                   do {

                       System.out.println("inner ex " + count + " =" + current.getMessage());
                       count++;

                   } while ((current = current.getNextException()) != null);
            }

            throw ex;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public Long update(Course course) throws SQLException {
		String sql = "UPDATE courses "
				   + "SET course = ?, "
				   + "	  description = ?, "
				   + "	  generation = ?, "
				   + "    updated_date = TO_CHAR(NOW(),'YYYYMMDDHH24MMSS'), "
				   + "	  updated_by = ?, "
				   + "	  cost = ? , "
				   + "	  discount = ? , "
				   + "	  total_hours = ? "
				   + "WHERE id = ? ";
		
		int result = jdbcTemplate.update(
				sql,
				new Object[]{
					course.getCourse(),
					course.getDescription(),
					course.getGeneration().getId(),
					course.getUpdatedBy().getId(),
					course.getCost(),
					course.getDiscount(),
					course.getTotalHour(),
					course.getId(), 
				});
		if(result>0){
			return course.getId();
		}
		return null;
	}

	@Override
	public Long delete(Long id) throws SQLException {
		String sql = "UPDATE courses "
				   + "SET status = '0' "
				   + "WHERE id = ? ";
		
		int result = jdbcTemplate.update(
				sql,
				new Object[]{
					id, 
				});
		if(result>0){
			System.out.println(id);
			return id;
		}
		return null;
	}

	@Override
	public Course findOne(Long id) throws SQLException {
		String sql = "SELECT DISTINCT A.id " 
				   + " 	   , (SELECT STRING_AGG(CONCAT('(START DATE: ',AA.start_date, ') SHIFT: ' ,BB.name),',') "   
				   + "		FROM course_details AA " 
				   + "		INNER JOIN shifts BB ON AA.shift = BB.id " 
				   + "		WHERE AA.course_id = A.id "
				   + "		AND AA.status = '1' "  
				   + "		GROUP BY AA.course_id "
				   + "	) AS shift "
				   + "	   , A.course "
				   + "	   , A.description "
				   + "	   , B.id AS generation_id "
				   + "	   , B.name "
				   + "	   , C.id AS course_type_id "
				   + "	   , A.created_date "
				   + "	   , A.status "
				   + "     , A.cost "
				   + "     , A.discount "
				   + "	   , A.total_hours "
				   + "	   , (A.cost - (A.cost * A.discount / 100)) AS paid_amount "
				   + "FROM courses A "
				   + "LEFT JOIN generations B ON A.generation = B.id AND B.status = '1' "
				   + "LEFT JOIN course_types C ON C.id = B.course_type AND C.status = '1' "
				   + "LEFT JOIN users D ON A.created_by = D.id AND D.status = '1' "
				   + "LEFT JOIN course_details E ON A.id = E.course_id AND E.status = '1' "
				   + "LEFT JOIN shifts F ON F.id = E.shift AND F.status = '1' "
				   + "WHERE A.id = ? "
				   + "AND A.status = '1' ";
		
		System.out.println(sql);
		Course course = jdbcTemplate.queryForObject(
				sql,
				new Object[]{
					id
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
				course.setPaidAmount(rs.getDouble("paid_amount"));
				course.setTotalHour(rs.getInt("total_hours"));
				
				Generation generation = new Generation();
				generation.setId(rs.getLong("generation_id"));
				generation.setName(rs.getString("name"));
				
				CourseType courseType = new CourseType();
				courseType.setId(rs.getLong("course_type_id"));
				generation.setCourseType(courseType);
				
				course.setGeneration(generation);
				return course;
			}
		});
		course.setCourseDetails(this.findAllCourseDetailsByCourseId(id));
		return course;
	}

	@Override
	public List<CourseDetails> findAllCourseDetailsByCourseId(Long id) throws SQLException {
		String sql = "SELECT A.course_details_id "
				   + "     , A.course_id "
				   + "     , A.shift "
				   + "	   , B.name "
				   + "     , A.start_date "
				   + "FROM course_details A "
				   + "LEFT JOIN shifts B ON A.shift = B.id AND B.status = '1' "
				   + "WHERE A.course_id = ? " 
				   + "AND A.status = '1' ";
		
		System.out.
		println(sql);
		return jdbcTemplate.query(
				sql,
				new Object[]{
					id
				},
				new RowMapper<CourseDetails>(){
			@Override
			public CourseDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
				CourseDetails courseDetails = new CourseDetails();
				courseDetails.setId(rs.getLong("course_details_id"));
				courseDetails.setStartDate(rs.getString("start_date"));
				
				Shift shift = new Shift();
				shift.setId(rs.getLong("shift"));
				shift.setName(rs.getString("name"));
				courseDetails.setShift(shift);
				return courseDetails;
			}
		});
	}

	@Override
	public boolean deleteCourseDetails(Long courseId) throws SQLException {
		String sql = "UPDATE course_details "
				   + "SET status = '0' "
				   + "WHERE course_id = ? ";
		
		int result = jdbcTemplate.update(
				sql,
				new Object[]{
					courseId, 
				});
		if(result>0){
			System.out.println(courseId);
			return true;
		}
		return false;
	}
	
}
