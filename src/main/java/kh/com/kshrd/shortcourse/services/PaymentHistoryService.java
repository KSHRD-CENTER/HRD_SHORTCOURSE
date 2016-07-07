package kh.com.kshrd.shortcourse.services;

import java.sql.SQLException;
import java.util.List;

import kh.com.kshrd.shortcourse.exceptions.BusinessException;
import kh.com.kshrd.shortcourse.models.PaymentHistory;

public interface PaymentHistoryService {

	public List<PaymentHistory> getAllPaymentHistoryByStudentDetailsId(Long studentDetailsId) throws BusinessException;
	
	public Long save(PaymentHistory paymentHistory) throws BusinessException, SQLException;
}
