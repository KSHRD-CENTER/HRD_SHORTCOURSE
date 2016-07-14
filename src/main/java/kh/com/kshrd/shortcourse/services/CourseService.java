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
	
	//TODO: TO FIND ALL COURSES BY GENERATION ID
	public List<Course> findAllCoursesByGenerationId(Long generationId) throws BusinessException ;
	
	//TODO: TO ADD NEW COURSE
	public Long addNewCourse(Course course) throws BusinessException;
	
	//TODO: TO FIND COURSE BY ID
	public Course findCourseById(Long id) throws BusinessException;
	
	//TODO: TO UPDATE COURSE BY ID
	public Course updateCourse(Course course) throws BusinessException;
	
	//TODO: TO DELETE THE COURSE BY ID
	public boolean deleteCourse(Long id) throws BusinessException;

}
