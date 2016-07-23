package kh.com.kshrd.shortcourse.repositories;

import java.sql.SQLException;
import java.util.List;

import kh.com.kshrd.shortcourse.filtering.DashboardFilter;
import kh.com.kshrd.shortcourse.models.Balance;
import kh.com.kshrd.shortcourse.models.PaymentHistory;
import kh.com.kshrd.shortcourse.utilities.Pagination;

public interface DashboardRepository {
	public Balance count(DashboardFilter filter) throws SQLException;
	public Balance countTotalPayment(DashboardFilter filter) throws SQLException;
	public List<PaymentHistory> findAll(DashboardFilter filter, Pagination pagination) throws SQLException;
}
