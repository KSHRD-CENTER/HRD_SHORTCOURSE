package kh.com.kshrd.shortcourse.services;

import java.util.List;

import kh.com.kshrd.shortcourse.exceptions.BusinessException;
import kh.com.kshrd.shortcourse.filtering.CourseFilter;
import kh.com.kshrd.shortcourse.models.Course;
import kh.com.kshrd.shortcourse.utilities.Pagination;

//TODO: COURSE SERVICE INTERFACE
public interface CourseService {
	
	//TODO: TO FIND ALL COURSES FILTERING BY CourseFilter AND Pagination 
	public List<Course> findAllCourses(CourseFilter filter, Pagination pagination) throws BusinessException ;

}
