package kh.com.kshrd.shortcourse.repositories;

import java.sql.SQLException;
import java.util.List;

import kh.com.kshrd.shortcourse.filtering.CourseFilter;
import kh.com.kshrd.shortcourse.models.Course;
import kh.com.kshrd.shortcourse.models.CourseDetails;
import kh.com.kshrd.shortcourse.utilities.Pagination;

public interface CourseRepository {

	//TODO: TO FIND ALL COURSES
	public List<Course> findAll(CourseFilter filter, Pagination pagination) throws SQLException;
	
	//TODO: TO FIND ALL COURSES BY GENERATION ID
	public List<Course> findAllByGenerationId(Long generationId) throws SQLException;
	
	//TODO: TO COUNT BY COURSE FILTER FOR PAGINATION
	public Long count(CourseFilter filter) throws SQLException;
	
	//TODO: TO SAVE COURSE
	public Long save(Course course) throws SQLException;
	
	//TODO: TO SAVE COURSE DETAILS BATCH
	public int[] save(List<CourseDetails> courseDetails, Long courseId) throws SQLException;
	
	//TODO: TO FIND ONE
	public Course findOne(Long id) throws SQLException;
	
	//TODO: TO UPDATE COURSE
	public Long update(Course course) throws SQLException;
	
	//TODO: TO DELETE THE COURSE
	public Long delete(Long id) throws SQLException;
	
	//TODO: TO FIND ALL COURSE DETAILS BY COURSE ID
	public List<CourseDetails> findAllCourseDetailsByCourseId(Long id) throws SQLException;
	
}
