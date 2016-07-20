package kh.com.kshrd.shortcourse.services.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kh.com.kshrd.shortcourse.exceptions.BusinessException;
import kh.com.kshrd.shortcourse.filtering.ShiftFilter;
import kh.com.kshrd.shortcourse.models.Shift;
import kh.com.kshrd.shortcourse.repositories.ShiftRepository;
import kh.com.kshrd.shortcourse.services.ShiftService;

@Service
public class ShiftServiceImpl implements ShiftService {
	
	@Autowired
	private ShiftRepository shiftRepository;
	
	@Override
	public List<Shift> findAllShiftItems() throws BusinessException {
		try {
			return shiftRepository.findAllItems();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BusinessException();
		}
	}

	@Override
	public List<Shift> findAllShiftsByCourseId(Long courseId) throws BusinessException {
		try {
			return shiftRepository.findAllByCourseId(courseId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BusinessException();
		}
	}

	@Override
	public Shift findShiftById(int id) throws BusinessException{
		try{
			return shiftRepository.findOne(id);
		}catch(SQLException e){
			e.printStackTrace();
			throw new BusinessException();
		}
	}

	@Override
	public Long addShift(Shift shift) throws BusinessException{
		try{
			Long shiftId = shiftRepository.save(shift);
			if(shiftId != null)
				return shiftId;
		}catch(SQLException e){
			e.printStackTrace();
			throw new BusinessException();
		}
		return null;
	}

	@Override
	public Long updateShift(Shift shift) throws BusinessException{
		try{
			Long shiftId = shiftRepository.update(shift);
			if(shiftId != null){
				return shiftId;
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw new BusinessException();
		}
		return null;
	}

	@Override
	public boolean deleteShift(int id) throws BusinessException{
		try{
			if(shiftRepository.delete(id))
				return true;
			else
				return false;
		}catch(SQLException e){
			e.printStackTrace();
			throw new BusinessException();
		}
	}

	@Override
	public List<Shift> findAllShifts(ShiftFilter filter) throws BusinessException{
		try{
			return shiftRepository.findAll(filter);
		}catch(SQLException e){
			e.printStackTrace();
			throw new BusinessException();
		}
	}
}
