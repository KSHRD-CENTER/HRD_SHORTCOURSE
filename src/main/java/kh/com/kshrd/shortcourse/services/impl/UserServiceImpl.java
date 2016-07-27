package kh.com.kshrd.shortcourse.services.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
	@Autowired
	private PasswordEncoder passwordEncoder;
	
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
	public User getUserById(String status, Long id) throws BusinessException {
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
		try{
			Long userId = userRepository.update(user);
			if(userId > 0){
				return userId;
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw new BusinessException();
		}
		return null;
	}

	@Override
	public Long changePassword(String oldPassword, String newPassword, Long id) throws BusinessException {
		try{
			User user = userRepository.findOne("1", id);
			if(passwordEncoder.matches(oldPassword, user.getPassword())){
				Long userId = userRepository.updatePassword(newPassword, id);
				if(userId > 0){
					return userId;
				}
			}else{
				return 0L;
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw new BusinessException();
		}
		return null;
	}

	@Override
	public User login(User user) throws BusinessException{
		try{
			User u = userRepository.findByEmail("1", user.getEmail());
			if(u != null){
				if(passwordEncoder.matches(user.getPassword(), u.getPassword())){
					user.setPassword(u.getPassword());
					return userRepository.findByEmailAndPasswordAndStatus(user);
				}else{
					return null;
				}
			}else{
				return null;
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw new BusinessException();
		}
	}

	@Override
	public User getUserByEmail(String status, String email) throws BusinessException{
		try{
			return userRepository.findByEmail(status, email);
		}catch(SQLException e){
			e.printStackTrace();
			throw new BusinessException();
		}
	}
}
