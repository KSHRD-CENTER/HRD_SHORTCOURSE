package kh.com.kshrd.shortcourse.services.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kh.com.kshrd.shortcourse.exceptions.BusinessException;
import kh.com.kshrd.shortcourse.filtering.DashboardFilter;
import kh.com.kshrd.shortcourse.models.Balance;
import kh.com.kshrd.shortcourse.models.PaymentHistory;
import kh.com.kshrd.shortcourse.repositories.DashboardRepository;
import kh.com.kshrd.shortcourse.services.DashboardService;
import kh.com.kshrd.shortcourse.utilities.Pagination;

@Service
public class DashboardServiceImpl implements DashboardService{
	@Autowired
	private DashboardRepository dashboardRepository;

	@Override
	public Balance countTotalMoney(DashboardFilter filter) throws BusinessException{
		try {
			return dashboardRepository.count(filter);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BusinessException();
		}
	}

	@Override
	public List<PaymentHistory> getAllPaymentHistories(DashboardFilter filter, Pagination pagination) throws BusinessException {
		try{
			return dashboardRepository.findAll(filter, pagination);
		}catch(SQLException e){
			e.printStackTrace();
			throw new BusinessException();
		}
	}

}
