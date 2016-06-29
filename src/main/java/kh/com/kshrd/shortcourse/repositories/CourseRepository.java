package kh.com.kshrd.shortcourse.repositories;

import java.util.List;

import kh.com.kshrd.shortcourse.models.Course;
import kh.com.kshrd.shortcourse.utilities.Pagination;

public interface CourseRepository {

	public List<Course> findAll(Pagination pagination);
}
