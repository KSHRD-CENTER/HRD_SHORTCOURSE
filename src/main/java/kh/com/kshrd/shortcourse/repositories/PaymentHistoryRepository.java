package kh.com.kshrd.shortcourse.repositories;

import java.util.List;

import kh.com.kshrd.shortcourse.models.PaymentHistory;

public interface PaymentHistoryRepository {

	public List<PaymentHistory> findAllByStudentDetailsId(Long studentDetailsId);
}
