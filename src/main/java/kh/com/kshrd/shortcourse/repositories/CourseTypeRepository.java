package kh.com.kshrd.shortcourse.repositories;

import java.sql.SQLException;
import java.util.List;

import kh.com.kshrd.shortcourse.models.CourseType;

public interface CourseTypeRepository {

	public List<CourseType> findAll() throws SQLException;
}
