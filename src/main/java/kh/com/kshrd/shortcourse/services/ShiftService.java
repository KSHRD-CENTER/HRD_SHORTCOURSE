package kh.com.kshrd.shortcourse.services;

import java.util.List;

import kh.com.kshrd.shortcourse.exceptions.BusinessException;
import kh.com.kshrd.shortcourse.models.Shift;

public interface ShiftService {

	public List<Shift> findAllShifts() throws BusinessException;
}
