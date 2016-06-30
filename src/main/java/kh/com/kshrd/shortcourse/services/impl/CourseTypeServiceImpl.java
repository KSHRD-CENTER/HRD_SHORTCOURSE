package kh.com.kshrd.shortcourse.services.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kh.com.kshrd.shortcourse.exceptions.BusinessException;
import kh.com.kshrd.shortcourse.models.CourseType;
import kh.com.kshrd.shortcourse.repositories.CourseTypeRepository;
import kh.com.kshrd.shortcourse.services.CourseTypeService;

@Service
public class CourseTypeServiceImpl implements CourseTypeService {

	@Autowired
	private CourseTypeRepository courseTypeRepository;
	
	@Override
	public List<CourseType> findAllCourseTypes() throws BusinessException {
		try {
			return courseTypeRepository.findAll();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BusinessException();
		}
	}

}
