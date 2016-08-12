package kh.com.kshrd.shortcourse.repositories.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import kh.com.kshrd.shortcourse.models.Course;
import kh.com.kshrd.shortcourse.models.PaymentHistory;
import kh.com.kshrd.shortcourse.models.Student;
import kh.com.kshrd.shortcourse.models.StudentDetails;
import kh.com.kshrd.shortcourse.models.User;
import kh.com.kshrd.shortcourse.repositories.PaymentHistoryRepository;
import kh.com.kshrd.shortcourse.utilities.Pagination;

@Repository
public class PaymentHistoryRepositoryImpl implements PaymentHistoryRepository{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<PaymentHistory> findAllByStudentDetailsId(Long studentDetailsId) throws SQLException {
		String sql = "SELECT id "
				   + "	   , student_details_id "
				   + "	   , paid_amount "
				   + "	   , TO_CHAR(TO_TIMESTAMP(paid_date,'YYYYMMDDHH24MISS'),'DD-Mon-YYYY HH24:MI:SS') AS paid_date "
				   + "	   , paid_by "
				   + "	   , created_date "
				   + "	   , created_by "
				   + "	   , updated_date "
				   + "	   , updated_by "
				   + "	   , status "
				   + "FROM payment_histories "
				   + "WHERE student_details_id = ? "
				   + "ORDER BY created_date" ;
		return jdbcTemplate.query(
				sql,
				new Object[]{
					studentDetailsId	
				}, new RowMapper<PaymentHistory>(){
				@Override
				public PaymentHistory mapRow(ResultSet rs, int rowNum) throws SQLException {
					PaymentHistory paymentHistory = new PaymentHistory();
					paymentHistory.setId(rs.getLong("id"));
					paymentHistory.setPaidAmount(rs.getDouble("paid_amount"));
					paymentHistory.setPaidDate(rs.getString("paid_date"));
					
					StudentDetails studentDetails = new StudentDetails();
					studentDetails.setId(rs.getLong("student_details_id"));
					
					paymentHistory.setStatus(rs.getString("status"));
					paymentHistory.setUpdatedDate(rs.getString("updated_date"));
					
					User createdBy = new User();
					createdBy.setId(rs.getLong("created_by"));
					paymentHistory.setCreatedBy(createdBy);
					
					User updatedBy = new User();
					updatedBy.setId(rs.getLong("updated_by"));
					paymentHistory.setUpdatedBy(updatedBy);
					
					User paidBy = new User();
					paidBy.setId(rs.getLong("paid_by"));
					paymentHistory.setPaidBy(paidBy);
					return paymentHistory;
				}
			});
	}
	
	@Override
	public List<PaymentHistory> findAll(String startDate, String endDate, Pagination pagination) throws SQLException {
		pagination.setTotalCount(count(startDate, endDate));
		String sql = "SELECT S.name AS s_name, "
					+ "		 C.course AS c_name, "
					+ "		 PH.paid_amount AS ph_paid_amount, "
					+ "		 TO_CHAR(TO_TIMESTAMP(PH.paid_date,'YYYYMMDDHH24MISS'),'DD-Mon-YYYY HH24:MI:SS') AS ph_paid_date, "
					+ "		 (C.COST - ((C.COST *(C.discount / 100.0))) - (C.cost * SD.discount/100.0)) AS total_paid, "
					+ "		 ((C.COST - ((C.COST *(C.discount / 100.0))) - (C.cost * SD.discount/100.0)) - (SELECT SUM(paid_amount) "
					+ "					FROM payment_histories "
					+ "					WHERE student_details_id = PH.student_details_id "
					+ "					AND paid_date <= PH.paid_date)) AS left_amount  "
					+ "FROM payment_histories PH "
					+ "INNER JOIN student_details SD ON PH.student_details_id = SD.student_details_id "
					+ "INNER JOIN students S ON SD.student_id = S.id "
					+ "INNER JOIN courses C ON SD.course_id = C.id "
					+ "WHERE SUBSTR(PH.paid_date,0,9) BETWEEN ? AND ? "
					+ "ORDER BY 4 DESC "
					+ "LIMIT ? "
					+ "OFFSET ?";
		return jdbcTemplate.query(
				sql
				, new Object[]{
					startDate,
					endDate,
					pagination.getLimit(),
					pagination.getOffset()
				}
				, new RowMapper<PaymentHistory>(){
				@Override
				public PaymentHistory mapRow(ResultSet rs, int rowNum) throws SQLException {
					PaymentHistory paymentHistory = new PaymentHistory();
					Student student = new Student();
					Course course = new Course();
					StudentDetails studentDetail = new StudentDetails();
					student.setName(rs.getString("s_name"));
					course.setCourse(rs.getString("c_name"));
					paymentHistory.setLeftCost(rs.getDouble("left_amount"));
					paymentHistory.setTotalPaid(rs.getDouble("total_paid"));
					paymentHistory.setPaidAmount(rs.getDouble("ph_paid_amount"));
					paymentHistory.setPaidDate(rs.getString("ph_paid_date"));
					studentDetail.setStudent(student);
					studentDetail.setCourse(course);
					paymentHistory.setStudentDetails(studentDetail);
					return paymentHistory;
				}
			});
	}
	
	private Long count(String startDate, String endDate){
		String sql = "SELECT COUNT(PH.id)"
				+ "FROM payment_histories PH "
				+ "INNER JOIN student_details SD ON PH.student_details_id = SD.student_details_id "
				+ "INNER JOIN students S ON SD.student_id = S.id "
				+ "INNER JOIN courses C ON SD.course_id = C.id "
				+ "WHERE SUBSTR(PH.paid_date,0,9) BETWEEN ? AND ? ";
		
		return jdbcTemplate.queryForObject(sql, new Object[]{
				startDate,
				endDate
		}, Long.class);
	}

	@Override
	public Long save(PaymentHistory paymentHistory) throws SQLException {
		Long id = jdbcTemplate.queryForObject("SELECT nextval('payment_history_id_seq')",Long.class);
		String sql = "INSERT INTO payment_histories( "
				   + "		id, "
				   + "		student_details_id, "
				   + "		paid_amount, "
				   + "		paid_by, "
				   + "		paid_date, "
				   + "		created_date, "
				   + "		created_by, "
				   + "		status) "
				   + "VALUES(?, ?, ?, ?, TO_CHAR(NOW(),'YYYYMMDDHH24MISS'), TO_CHAR(NOW(),'YYYYMMDDHH24MISS'), ?,'1') ";
		
		int result = jdbcTemplate.update(
					sql,
					new Object[]{
						id,
						paymentHistory.getStudentDetails().getId(),
						paymentHistory.getPaidAmount(),
						paymentHistory.getPaidBy().getId(),
						paymentHistory.getCreatedBy().getId()
					});
		if(result > 0){
			return id;
		}
		return null;
	}
}