package kh.com.kshrd.shortcourse.services.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kh.com.kshrd.shortcourse.exceptions.BusinessException;
import kh.com.kshrd.shortcourse.models.Shift;
import kh.com.kshrd.shortcourse.repositories.ShiftRepository;
import kh.com.kshrd.shortcourse.services.ShiftService;

@Service
public class ShiftServiceImpl implements ShiftService {
	
	@Autowired
	private ShiftRepository shiftRepository;
	
	@Override
	public List<Shift> findAllShifts() throws BusinessException {
		try {
			return shiftRepository.findAll();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BusinessException();
		}
	}
}
