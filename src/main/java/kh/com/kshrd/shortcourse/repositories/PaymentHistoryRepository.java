package kh.com.kshrd.shortcourse.repositories;

import java.sql.SQLException;
import java.util.List;

import kh.com.kshrd.shortcourse.models.PaymentHistory;

public interface PaymentHistoryRepository {

	public List<PaymentHistory> findAllByStudentDetailsId(Long studentDetailsId) throws SQLException;
	
	public Long save(PaymentHistory paymentHistory) throws SQLException;
}
