package kh.com.kshrd.shortcourse.services;

import java.util.List;

import kh.com.kshrd.shortcourse.exceptions.BusinessException;
import kh.com.kshrd.shortcourse.filtering.DashboardFilter;
import kh.com.kshrd.shortcourse.models.Balance;
import kh.com.kshrd.shortcourse.models.PaymentHistory;
import kh.com.kshrd.shortcourse.utilities.Pagination;

public interface DashboardService {
	public Balance countTotalMoney(DashboardFilter filter) throws BusinessException;
	public List<PaymentHistory> getAllPaymentHistories(DashboardFilter filter, Pagination pagination) throws BusinessException;
}
