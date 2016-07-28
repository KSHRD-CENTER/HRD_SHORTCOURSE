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

import kh.com.kshrd.shortcourse.filtering.StudentFilter;
import kh.com.kshrd.shortcourse.models.Course;
import kh.com.kshrd.shortcourse.models.Generation;
import kh.com.kshrd.shortcourse.models.Shift;
import kh.com.kshrd.shortcourse.models.Student;
import kh.com.kshrd.shortcourse.models.StudentDetails;
import kh.com.kshrd.shortcourse.models.User;
import kh.com.kshrd.shortcourse.repositories.StudentRepository;
import kh.com.kshrd.shortcourse.utilities.Pagination;

@Repository
public class StudentRepositoryImpl implements StudentRepository{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<StudentDetails> findAll(StudentFilter filter, Pagination pagination) throws SQLException {
		try{
			pagination.setTotalCount(this.count(filter));
			String sql = "SELECT A.id, " +
						 "		 A.name, " +  
						 "		 A.gender, " + 
						 "		 A.email, " + 
						 "		 A.university, " +
						 "		 A.address, " +
						 "		 A.telephone, " +
						 "		 E.name AS generation, " +
						 "		 TO_CHAR(TO_TIMESTAMP(B.registered_date,'YYYYMMDDHH24MI'),'DD-Mon-YYYY HH24:MI') AS registered_date, " +
						 "		 B.registered_by, " + 
						 "		 (  SELECT STRING_AGG(BB.name,', ') " + 
						 "		 	FROM student_details AA " +
						 "		 	INNER JOIN shifts BB ON AA.shift = BB.id " + 
						 "	 	 	WHERE AA.student_details_id = B.student_details_id" + 
						 "	     	GROUP BY AA.student_id " + 
						 "		 ) AS shift, " +
						 "		 D.course, " +
						 "		 B.cost, " +
						 " 		 (SELECT SUM(paid_amount) " +
						 "	 	 FROM payment_histories " +
						 "		 WHERE student_details_id = B.student_details_id " +
						 "	 	 AND status='1') AS paid_amount, " +
						 "	 	 B.student_details_id, " +
						 "	 	 B.status " + 
						 "FROM students A " +
						 "LEFT JOIN student_details B ON A.id = B.student_id " + 
						 "LEFT JOIN shifts C ON B.shift = C.id AND C.status = '1' " +
						 "LEFT JOIN courses D ON B.course_id = D.id AND D.status = '1' " +
						 "LEFT JOIN generations E ON D.generation = E.id AND E.status = '1' " +
						 "WHERE LOWER(A.name) LIKE LOWER(?) " +
						 "AND E.id::TEXT LIKE ? " +
						 "AND C.id::TEXT LIKE ? " +
						 "AND D.id::TEXT LIKE ? " +
						 "ORDER BY 9 DESC " +
						 "LIMIT ? " +
						 "OFFSET ?"; 
			return jdbcTemplate.query(
					sql,
					new Object[]{
						"%" + filter.getStudentName() + "%",
						"%" + filter.getGenerationId() + "%",
						"%" + filter.getShiftId() + "%",
						"%" + filter.getCourseId() + "%",
						pagination.getLimit(),
						pagination.offset()
					},
					new RowMapper<StudentDetails>(){
				@Override
				public StudentDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
					StudentDetails studentDetails = new StudentDetails();
					studentDetails.setId(rs.getLong("student_details_id"));
					studentDetails.setCost(rs.getDouble("cost"));
					studentDetails.setPaidAmount(rs.getDouble("paid_amount"));
					studentDetails.setRegisteredDate(rs.getString("registered_date"));
					
					User user = new User();
					user.setId(rs.getLong("registered_by"));
					studentDetails.setRegisteredBy(user);
					
					Shift shift = new Shift();
					shift.setName(rs.getString("shift"));
					studentDetails.setShift(shift);
					
					Student student = new Student();
					student.setId(rs.getLong("id"));
					student.setName(rs.getString("name"));
					student.setEmail(rs.getString("email"));
					student.setGender(rs.getString("gender"));
					student.setUniversity(rs.getLong("university"));
					student.setTelephone(rs.getString("telephone"));
					student.setAddress(rs.getString("address"));
					
					Course course = new Course();
					course.setCourse(rs.getString("course"));
					
					Generation generation = new Generation();
					generation.setName(rs.getString("generation"));
					course.setGeneration(generation);
					
					studentDetails.setStudent(student);
					studentDetails.setCourse(course);
					studentDetails.setShift(shift);
					studentDetails.setStatus(rs.getString("status"));
					return studentDetails;
				}
			});
		}catch(Exception ex){
			ex.printStackTrace();
			throw new SQLException();
		}
	}
	
	

	@Override
	public Long count(StudentFilter filter) throws SQLException {
		String sql = "SELECT COUNT(A.id) " +
					 "FROM students A " +
					 "LEFT JOIN student_details B ON A.id = B.student_id AND B.status = '1' " + 
					 "LEFT JOIN shifts C ON B.shift = C.id AND C.status = '1' " +
					 "LEFT JOIN courses D ON B.course_id = D.id AND D.status = '1' " +
					 "LEFT JOIN generations E ON D.generation = E.id AND E.status = '1' " +
					 "WHERE LOWER(A.name) LIKE LOWER(?) " +
					 "AND E.id::TEXT LIKE ? " +
					 "AND C.id::TEXT LIKE ? " +
					 "AND D.id::TEXT LIKE ? ";
		return jdbcTemplate.queryForObject(
				sql,
				new Object[]{
					"%" + filter.getStudentName() + "%",
					"%" + filter.getGenerationId() + "%",
					"%" + filter.getShiftId() + "%",
					"%" + filter.getCourseId() + "%"
				},
				Long.class);
	}

	@Override
	public Long save(Student student) throws SQLException {
		try{
			Long id = jdbcTemplate.queryForObject("SELECT nextval('courses_id_seq')",Long.class);
			String sql =  "INSERT INTO students(id, name, telephone, email, gender, university, year, address, created_date, created_by, status) "
						+ "VALUES(?, ?, ?, ?, ?, ?, ?, ? , TO_CHAR(NOW(),'YYYYMMDDHH24MISS'), ?, ?)";
			
			int result = jdbcTemplate.update(
					sql,
					new Object[]{
						id,
						student.getName(),
						student.getTelephone(),
						student.getEmail(),
						student.getGender(),
						student.getUniversity(),
						student.getYear(),
						student.getAddress(),
						student.getCreatedBy().getId(),
						student.getStatus()
						
					});
			if(result > 0){
				return id;
			}
		}catch(Exception ex){
			ex.printStackTrace();
			throw new SQLException();
		}
		return null;
	}

	@Override
	public int[] save(List<StudentDetails> studentDetails, Long studentId) {
		try{
			String sql = "INSERT INTO student_details("
												  + "student_id, "
												  + "course_id, "
												  + "shift, "
												  + "cost, "
												  + "discount, "
												  + "registered_date, "
												  + "status, "
												  + "registered_by, "
												  + "paid) "
						 + "VALUES(?, ?, ?, ?, ?, TO_CHAR(NOW(),'YYYYMMDDHH24MMSS'), '1', ?, ?);";
			int results[]= jdbcTemplate.batchUpdate(sql,new BatchPreparedStatementSetter() {
				    @Override
				    public void setValues(PreparedStatement ps, int i) throws SQLException {
				    	ps.setLong(1, studentId);
				    	ps.setLong(2, studentDetails.get(i).getCourse().getId());
				    	ps.setLong(3, studentDetails.get(i).getShift().getId());
				    	ps.setDouble(4, studentDetails.get(i).getCost());
				    	ps.setDouble(5, studentDetails.get(i).getDiscount());
				    	ps.setLong(6, studentDetails.get(i).getRegisteredBy().getId());
				    	ps.setDouble(7, studentDetails.get(i).getPaidAmount());
				    }
				    
				    @Override
				    public int getBatchSize() {
				        return studentDetails.size();
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
	public StudentDetails findOne(Long id) throws SQLException {
		try{
			String sql = "SELECT A.id, " +
						 "		 A.name, " +  
						 "		 A.gender, " + 
						 "		 A.email, " + 
						 "		 A.university, " +
						 "		 A.address, " +
						 "		 A.telephone, " +
						 "		 E.name AS generation, " +
						 "		 TO_CHAR(TO_TIMESTAMP(B.registered_date,'YYYYMMDDHH24MI'),'DD-Mon-YYYY HH24:MI') AS registered_date, " +
						 "		 B.registered_by, " + 
						 "		 (  SELECT STRING_AGG(BB.name,', ') " + 
						 "		 	FROM student_details AA " +
						 "		 	INNER JOIN shifts BB ON AA.shift = BB.id " + 
						 "	 	 	WHERE AA.student_details_id = B.student_details_id" + 
						 "	     	GROUP BY AA.student_id " + 
						 "		 ) AS shift, " +
						 "		 D.course, " +
						 "		 B.cost, " +
						 " 		 (SELECT SUM(paid_amount) " +
						 "	 	 FROM payment_histories " +
						 "		 WHERE student_details_id = B.student_details_id " +
						 "	 	 AND status='1') AS paid_amount, " +
						 "	 	 B.student_details_id, " +
						 "	 	 B.status " + 
						 "FROM students A " +
						 "LEFT JOIN student_details B ON A.id = B.student_id " +
						 "WHERE A.id = ?" ;
			return jdbcTemplate.queryForObject(
					sql,
					new Object[]{
						id
					},
					new RowMapper<StudentDetails>(){
				@Override
				public StudentDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
					StudentDetails studentDetails = new StudentDetails();
					studentDetails.setId(rs.getLong("student_details_id"));
					studentDetails.setCost(rs.getDouble("cost"));
					studentDetails.setPaidAmount(rs.getDouble("paid_amount"));
					studentDetails.setRegisteredDate(rs.getString("registered_date"));
					
					User user = new User();
					user.setId(rs.getLong("registered_by"));
					studentDetails.setRegisteredBy(user);
					
					Shift shift = new Shift();
					shift.setName(rs.getString("shift"));
					studentDetails.setShift(shift);
					
					Student student = new Student();
					student.setId(rs.getLong("id"));
					student.setName(rs.getString("name"));
					student.setEmail(rs.getString("email"));
					student.setGender(rs.getString("gender"));
					student.setUniversity(rs.getLong("university"));
					student.setTelephone(rs.getString("telephone"));
					student.setAddress(rs.getString("address"));
					
					Course course = new Course();
					course.setCourse(rs.getString("course"));
					
					Generation generation = new Generation();
					generation.setName(rs.getString("generation"));
					course.setGeneration(generation);
					
					studentDetails.setStudent(student);
					studentDetails.setCourse(course);
					studentDetails.setShift(shift);
					studentDetails.setStatus(rs.getString("status"));
					return studentDetails;
				}
			});
		}catch(Exception ex){
			ex.printStackTrace();
			throw new SQLException();
		}
	}
	
}
