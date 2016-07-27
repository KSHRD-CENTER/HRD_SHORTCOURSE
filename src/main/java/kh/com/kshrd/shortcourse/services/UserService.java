package kh.com.kshrd.shortcourse.services;

import java.util.List;

import kh.com.kshrd.shortcourse.exceptions.BusinessException;
import kh.com.kshrd.shortcourse.filtering.UserFilter;
import kh.com.kshrd.shortcourse.models.User;
import kh.com.kshrd.shortcourse.utilities.Pagination;

public interface UserService {
	public List<User> getAllUsers(UserFilter filter, Pagination pagination) throws BusinessException;
	public User getUserById(String status, Long id) throws BusinessException;
	public Long addUser(User user) throws BusinessException;
	public boolean deleteUser(int id) throws BusinessException;
	public Long updateUser(User user) throws BusinessException;
	public Long changePassword(String oldPassword, String newPassword, Long id) throws BusinessException;
	public User login(User user) throws BusinessException;
	public User getUserByEmail(String status, String email) throws BusinessException;
}
