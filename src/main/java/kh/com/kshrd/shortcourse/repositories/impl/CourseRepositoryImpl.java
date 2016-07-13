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
		String sql = "SELECT DISTINCT A.id " 
				   + " 	   , (SELECT STRING_AGG(BB.name,',') "   
				   + "		FROM course_details AA " 
				   + "		INNER JOIN shifts BB ON AA.shift = BB.id " 
				   + "		WHERE AA.course_id = A.id "  
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
				   + "AND LOWER(C.id::TEXT) LIKE LOWER(?) "
				   + "AND LOWER(B.id::TEXT) LIKE LOWER(?) "
				   + "AND LOWER(E.course_id::TEXT) LIKE LOWER(?) "
				   + "AND LOWER(E.shift::TEXT) LIKE LOWER(?) "
				   + "AND A.status = '1' "
				   + "ORDER BY 1"
				   + "LIMIT ? "
				   + "OFFSET ? ";
		
		System.out.println(sql);
		return jdbcTemplate.query(
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
				   + "AND LOWER(C.id::TEXT) LIKE LOWER(?) "
				   + "AND LOWER(B.id::TEXT) LIKE LOWER(?) "
				   + "AND LOWER(E.course_id::TEXT) LIKE LOWER(?) "
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
	
}
