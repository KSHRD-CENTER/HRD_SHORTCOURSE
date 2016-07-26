package kh.com.kshrd.shortcourse.services;

import java.util.List;

import kh.com.kshrd.shortcourse.exceptions.BusinessException;
import kh.com.kshrd.shortcourse.filtering.UserFilter;
import kh.com.kshrd.shortcourse.models.User;
import kh.com.kshrd.shortcourse.utilities.Pagination;

public interface UserService {
	public List<User> getAllUsers(UserFilter filter, Pagination pagination) throws BusinessException;
	public User getUserById(String status, int id) throws BusinessException;
	public Long addUser(User user) throws BusinessException;
	public boolean deleteUser(int id) throws BusinessException;
	public Long updateUser(User user) throws BusinessException;
	public Long updatePassword(User user) throws BusinessException;
}
