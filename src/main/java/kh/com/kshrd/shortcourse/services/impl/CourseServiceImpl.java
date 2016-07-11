package kh.com.kshrd.shortcourse.services.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kh.com.kshrd.shortcourse.exceptions.BusinessException;
import kh.com.kshrd.shortcourse.filtering.CourseFilter;
import kh.com.kshrd.shortcourse.models.Course;
import kh.com.kshrd.shortcourse.repositories.CourseRepository;
import kh.com.kshrd.shortcourse.services.CourseService;
import kh.com.kshrd.shortcourse.utilities.Pagination;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {
	
	@Autowired
	private CourseRepository courseRepository;
	
	@Override
	@Transactional
	public List<Course> findAllCourses(CourseFilter filter, Pagination pagination) throws BusinessException{
		try {
			return courseRepository.findAll(filter, pagination);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BusinessException();
		}
	}

	@Override
	@Transactional
	public List<Course> findAllCoursesByGenerationId(Long generationId) throws BusinessException{
		try {
			return courseRepository.findAllByGenerationId(generationId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BusinessException();
		}
	}

	@Override
	@Transactional
	public Long addNewCourse(Course course) throws BusinessException {
		try {
			Long courseId = courseRepository.save(course);
			if(courseId!=null){
				courseRepository.save(course.getCourseDetails(), courseId);
			}
			return courseId;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BusinessException();
		}
	}
	
	
	

}
