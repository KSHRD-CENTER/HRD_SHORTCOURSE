package kh.com.kshrd.shortcourse.repositories;

import java.sql.SQLException;
import java.util.List;

import kh.com.kshrd.shortcourse.filtering.UserFilter;
import kh.com.kshrd.shortcourse.models.User;
import kh.com.kshrd.shortcourse.utilities.Pagination;

public interface UserRepository {
	public List<User> findAll(UserFilter filter, Pagination pagination) throws SQLException;
	public User findOne(String status, Long id) throws SQLException;
	public Long save(User user) throws SQLException;
	public Long update(User user) throws SQLException;
	public boolean delete(int id) throws SQLException;
	public Long updatePassword(String newPassword, Long id) throws SQLException;
}
