package kh.com.kshrd.shortcourse.services.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kh.com.kshrd.shortcourse.exceptions.BusinessException;
import kh.com.kshrd.shortcourse.models.PaymentHistory;
import kh.com.kshrd.shortcourse.repositories.PaymentHistoryRepository;
import kh.com.kshrd.shortcourse.services.PaymentHistoryService;

@Service
@Transactional
public class PaymentHistoryServiceImpl implements PaymentHistoryService {

	@Autowired
	private PaymentHistoryRepository paymentHistoryRepository;
	
	@Override
	public List<PaymentHistory> getAllPaymentHistoryByStudentDetailsId(Long studentDetailsId) throws BusinessException {
		try {
			return paymentHistoryRepository.findAllByStudentDetailsId(studentDetailsId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BusinessException();
		}
	}

	@Override
	public Long save(PaymentHistory paymentHistory) throws BusinessException {
		try {
			return paymentHistoryRepository.save(paymentHistory);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BusinessException();
		}
	}
	
	
}
