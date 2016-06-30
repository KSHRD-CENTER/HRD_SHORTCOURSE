package kh.com.kshrd.shortcourse.services;

import java.util.List;

import kh.com.kshrd.shortcourse.exceptions.BusinessException;
import kh.com.kshrd.shortcourse.models.CourseType;

public interface CourseTypeService {
	public List<CourseType> findAllCourseTypes() throws BusinessException;
}
