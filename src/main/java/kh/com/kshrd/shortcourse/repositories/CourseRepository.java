package kh.com.kshrd.shortcourse.repositories;

import java.sql.SQLException;
import java.util.List;

import kh.com.kshrd.shortcourse.filtering.CourseFilter;
import kh.com.kshrd.shortcourse.models.Course;
import kh.com.kshrd.shortcourse.utilities.Pagination;

public interface CourseRepository {

	public List<Course> findAll(CourseFilter filter, Pagination pagination) throws SQLException;
	public Long count(CourseFilter filter) throws SQLException;
}
