package kh.com.kshrd.shortcourse.repositories.impl;

import java.util.List;

import kh.com.kshrd.shortcourse.models.Course;
import kh.com.kshrd.shortcourse.repositories.CourseRepository;
import kh.com.kshrd.shortcourse.utilities.Pagination;

public class CourseRepositoryImpl implements CourseRepository{

	@Override
	public List<Course> findAll(Pagination pagination) {
		String sql = "SELECT A.*, " +
					 " 		(SELECT STRING_AGG(BB.name,',') " +  
					 "		FROM course_details AA " +
					 "		INNER JOIN shifts BB ON AA.shift = BB.id " +
					 "		GROUP BY AA.course_id " + 
					 "	) AS shift " +
					 "FROM courses A ";	
		return null;
	}

}
