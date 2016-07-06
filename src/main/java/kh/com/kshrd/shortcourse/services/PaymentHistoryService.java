package kh.com.kshrd.shortcourse.services;

import java.util.List;

import kh.com.kshrd.shortcourse.models.PaymentHistory;

public interface PaymentHistoryService {

	public List<PaymentHistory> getAllPaymentHistoryByStudentDetailsId(Long studentDetailsId);
}
