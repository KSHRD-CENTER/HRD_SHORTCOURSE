package kh.com.kshrd.shortcourse.services.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kh.com.kshrd.shortcourse.exceptions.BusinessException;
import kh.com.kshrd.shortcourse.filtering.UserFilter;
import kh.com.kshrd.shortcourse.models.User;
import kh.com.kshrd.shortcourse.repositories.UserRepository;
import kh.com.kshrd.shortcourse.services.UserService;
import kh.com.kshrd.shortcourse.utilities.Pagination;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public List<User> getAllUsers(UserFilter filter, Pagination pagination) throws BusinessException{
		try{
			return userRepository.findAll(filter, pagination);
		}catch(SQLException e){
			e.printStackTrace();
			throw new BusinessException();
		}
	}

	@Override
	public User getUserById(String status, int id) throws BusinessException {
		try{
			return userRepository.findOne(status, id);
		}catch(SQLException e){
			e.printStackTrace();
			throw new BusinessException();
		}
	}

	@Override
	public Long addUser(User user) throws BusinessException {
		try{
			Long userId = userRepository.save(user);
			if(userId != null){
				return userId;
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw new BusinessException();
		}
		return null;
	}

	@Override
	public boolean deleteUser(int id) throws BusinessException {
		try{
			if(userRepository.delete(id))
				return true;
			else
				return false;
		}catch(SQLException e){
			e.printStackTrace();
			throw new BusinessException();
		}
	}

	@Override
	public Long updateUser(User user) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long updatePassword(User user) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

}
