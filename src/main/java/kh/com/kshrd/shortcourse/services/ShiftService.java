package kh.com.kshrd.shortcourse.services;

import java.util.List;

import kh.com.kshrd.shortcourse.exceptions.BusinessException;
import kh.com.kshrd.shortcourse.models.Shift;

public interface ShiftService {
	public List<Shift> findAllShifts() throws BusinessException;
	public List<Shift> findAllShiftsByCourseId(Long courseId) throws BusinessException;
	public Shift findShiftById(int id) throws BusinessException;
	public Long addShift(Shift shift) throws BusinessException;
	public Long updateShift(Shift shift) throws BusinessException;
	public boolean deleteShift(int id) throws BusinessException;
}