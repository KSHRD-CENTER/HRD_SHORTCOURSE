package kh.com.kshrd.shortcourse.repositories;

import java.util.List;

import kh.com.kshrd.shortcourse.models.Dashboard;

public interface DashboardRepository {
	public Long countTotalMoney(String courseTypeId);
	public List<Dashboard> findAll();
}
