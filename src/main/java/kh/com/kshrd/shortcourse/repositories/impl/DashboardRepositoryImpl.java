package kh.com.kshrd.shortcourse.repositories.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import kh.com.kshrd.shortcourse.filtering.DashboardFilter;
import kh.com.kshrd.shortcourse.models.Balance;
import kh.com.kshrd.shortcourse.models.Course;
import kh.com.kshrd.shortcourse.models.PaymentHistory;
import kh.com.kshrd.shortcourse.models.Student;
import kh.com.kshrd.shortcourse.models.StudentDetails;
import kh.com.kshrd.shortcourse.repositories.DashboardRepository;
import kh.com.kshrd.shortcourse.utilities.Pagination;

@Repository
public class DashboardRepositoryImpl implements DashboardRepository{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public Balance count(DashboardFilter filter) {
		String sql = "SELECT SUM((SELECT SUM(paid_amount) " 
					+"			FROM payment_histories " 						
					+"			INNER JOIN students S ON student_id = S.ID "
					+"			INNER JOIN courses C ON SD.course_id = C . ID "
					+"			INNER JOIN generations G ON C.generation = G.id "
					+"			WHERE G.id::TEXT LIKE ? "
					+"			AND course_type::TEXT LIKE ? "
					+"			AND C.id::TEXT LIKE ? "
					+ "			AND LOWER(C.course) LIKE LOWER(?) "
					+"			AND student_details_id = SD.student_details_id)) AS PAID_BALANCE, "
					+"		 SUM(SD.cost - (SELECT SUM(paid_amount) " 
					+"			FROM payment_histories "		
					+"			INNER JOIN students S ON student_id = S.ID "
					+"			INNER JOIN courses C ON SD.course_id = C . ID "
					+"			INNER JOIN generations G ON C.generation = G.id "
					+"			WHERE G.id::TEXT LIKE ? "
					+"			AND course_type::TEXT LIKE ? "
					+"			AND C.id::TEXT LIKE ? "
					+ "			AND LOWER(C.course) LIKE LOWER(?) "
					+"			AND student_details_id = SD.student_details_id)) AS REMAINING_BALANCE "
					+"FROM student_details SD";
		System.out.println("FILTER REPO ============>" + filter);
		return jdbcTemplate.queryForObject(sql,
				new Object[]{
					"%"+filter.getGenerationId()+"%",
					"%"+filter.getCourseTypeId()+"%",
					"%"+filter.getCourseId()+"%",
					"%"+filter.getCourseName()+"%",
					"%"+filter.getGenerationId()+"%",
					"%"+filter.getCourseTypeId()+"%",
					"%"+filter.getCourseId()+"%",
					"%"+filter.getCourseName()+"%"
				},
				new RowMapper<Balance>() {
					@Override
					public Balance mapRow(ResultSet rs, int rowNum) throws SQLException {
						Balance balance = new Balance();
						balance.setActualBalance(rs.getDouble("PAID_BALANCE"));
						balance.setRemainingBalance(rs.getDouble("REMAINING_BALANCE"));
						return balance;
					}
				});
	}

	@Override
	public List<PaymentHistory> findAll(DashboardFilter filter, Pagination pagination) throws SQLException{
		pagination.setTotalCount(countRecord(filter));
		String sql = "SELECT A.s_name, "
					+"		 A.id, "
					+ "		 A.g_name, "
					+ "		 A.ct_name, "
					+ "		 A.course, "
					+ "		 A.total_paid, "
					+ "		 SUM(paid_amount) AS PAID_AMOUNT, "
					+ "		 (A.total_paid - SUM(paid_amount)) AS LEFT_AMOUNT "
					+ "FROM(SELECT S.NAME AS s_name, "
					+ "			   S.id, "
					+ "			   G.name AS g_name, "
					+ "			   CT.name AS ct_name, "
					+ "			   C.course, "
					+ "			   PH.paid_amount, "
					+ "			   TO_CHAR(TO_TIMESTAMP(paid_date,'YYYYMMDDHH24MISS'),'DD-Mon-YYYY HH24:MI:SS') AS paid_date, "
					+ "			   SD.discount, "
					+ "			   (C.COST - ((C.COST *(C.discount / 100.0))) - (C.cost * SD.discount/100.0)) AS total_paid, "
					+ "			   ((C . COST - ((C . COST *(C .discount / 100.0))) - (C.cost * SD.discount/100.0) ) - (SELECT SUM (paid_amount) "
					+ "																									FROM payment_histories "
					+ "																									WHERE student_details_id = PH.student_details_id "
					+ "																									AND paid_date <= PH.paid_date)) AS left_amount "
					+ "		FROM students S "
					+ "		LEFT JOIN student_details SD ON SD.student_id = S. ID "
					+ "		LEFT JOIN payment_histories PH ON PH.student_details_id = SD.student_details_id "
					+ "		LEFT JOIN courses C ON SD.course_id = C . ID "
					+ "		LEFT JOIN generations G ON C.generation = G.id "
					+ "		LEFT JOIN course_types CT ON G.course_type = CT.id "
					+ "WHERE G.id::TEXT LIKE ? "
					+ "AND CT.id::TEXT LIKE ? "
					+ "AND C.id::TEXT LIKE ? "
					+ "AND LOWER(C.course) LIKE LOWER(?) "
					+ "ORDER BY S.id DESC "
					+ ") A "
					+ "GROUP BY 1,2,3,4,5,6 "
					+ "ORDER BY A.id "
					+ "LIMIT ? "
					+ "OFFSET ? ";
		return jdbcTemplate.query(
				sql,
				new Object[]{
					"%"+ filter.getGenerationId() +"%",
					"%"+ filter.getCourseTypeId() +"%",
					"%"+ filter.getCourseId() +"%",
					"%"+ filter.getCourseName() +"%",
					pagination.getLimit(),
					pagination.getOffset()
				},
				new RowMapper<PaymentHistory>() {
			@Override
			public PaymentHistory mapRow(ResultSet rs, int rowNum) throws SQLException {
				PaymentHistory paymentHistory = new PaymentHistory();
				paymentHistory.setPaidAmount(rs.getDouble("paid_amount"));
				paymentHistory.setLeftCost(rs.getDouble("left_amount"));
				paymentHistory.setTotalPaid(rs.getDouble("total_paid"));
				paymentHistory.setGenerationName(rs.getString("g_name"));
				paymentHistory.setCourseTypeName(rs.getString("ct_name"));
				StudentDetails studentDetail = new StudentDetails();
				Course course = new Course();
				Student student = new Student();
				student.setName(rs.getString("s_name"));
				course.setCourse(rs.getString("course"));
				studentDetail.setCourse(course);
				studentDetail.setStudent(student);
				paymentHistory.setStudentDetails(studentDetail);
				return paymentHistory;
			}
		});
	}
	
	private Long countRecord(DashboardFilter filter){
		String sql = "SELECT COUNT(DISTINCT S.id) "
				+ "		FROM students S "
				+ "		LEFT JOIN student_details SD ON SD.student_id = S. ID "
				+ "		LEFT JOIN payment_histories PH ON PH.student_details_id = SD.student_details_id "
				+ "		LEFT JOIN courses C ON SD.course_id = C . ID "
				+ "		LEFT JOIN generations G ON C.generation = G.id "
				+ "		LEFT JOIN course_types CT ON G.course_type = CT.id "
				+ "WHERE G.id::TEXT LIKE ? "
				+ "AND CT.id::TEXT LIKE ? "
				+ "AND C.id::TEXT LIKE ? "
				+ "AND C.course LIKE ? ";
	return jdbcTemplate.queryForObject(
			sql,
			new Object[]{
				"%"+ filter.getGenerationId() +"%",
				"%"+ filter.getCourseTypeId() +"%",
				"%"+ filter.getCourseId() +"%",
				"%"+ filter.getCourseName() +"%"
			},
			Long.class);
	}

	@Override
	public Balance countTotalPayment(DashboardFilter filter) throws SQLException {
		String sql = "SELECT SUM (SD.cost*(100-SD.discount)/100) AS TOTAL_PAID FROM student_details SD "
					+ "INNER JOIN students S ON SD.student_id = S.ID "
					+ "INNER JOIN courses C ON SD.course_id = C . ID "
					+ "INNER JOIN generations G ON C.generation = G.id "
					+ "WHERE course_type::TEXT LIKE ? "
					+ "AND student_details_id = SD.student_details_id "
					+ "AND G.id::TEXT LIKE ? "
					+ "AND C.id::TEXT LIKE ? "
					+ "AND LOWER(C.course) LIKE LOWER(?)";
	return jdbcTemplate.queryForObject(
			sql,
			new Object[]{
				"%"+ filter.getCourseTypeId() +"%",
				"%"+ filter.getGenerationId() +"%",
				"%"+ filter.getCourseId() +"%",
				"%"+ filter.getCourseName() +"%"
			},new RowMapper<Balance>() {
				@Override
				public Balance mapRow(ResultSet rs, int rowNum) throws SQLException {
					Balance balance = new Balance();
					balance.setEstimateBalance(rs.getDouble("TOTAL_PAID"));
					return balance;
				}
			});
	}
}