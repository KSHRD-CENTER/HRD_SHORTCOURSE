package kh.com.kshrd.shortcourse.repositories.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import kh.com.kshrd.shortcourse.models.PaymentHistory;
import kh.com.kshrd.shortcourse.models.StudentDetails;
import kh.com.kshrd.shortcourse.models.User;
import kh.com.kshrd.shortcourse.repositories.PaymentHistoryRepository;

@Repository
public class PaymentHistoryRepositoryImpl implements PaymentHistoryRepository{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<PaymentHistory> findAllByStudentDetailsId(Long studentDetailsId) {
		String sql = "SELECT id "
				   + "	   , student_details_id "
				   + "	   , paid_amount "
				   + "     , paid_date "
				   + "	   , paid_by "
				   + "	   , created_date "
				   + "	   , created_by "
				   + "	   , updated_date "
				   + "	   , updated_by "
				   + "	   , status "
				   + "FROM payment_histories "
				   + "WHERE student_details_id = ? " ;
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

}