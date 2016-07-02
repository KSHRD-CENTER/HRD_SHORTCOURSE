package kh.com.kshrd.shortcourse.restcontrollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kh.com.kshrd.shortcourse.exceptions.BusinessException;
import kh.com.kshrd.shortcourse.filtering.CourseFilter;
import kh.com.kshrd.shortcourse.models.Course;
import kh.com.kshrd.shortcourse.models.ResponseModel;
import kh.com.kshrd.shortcourse.models.StatusCode;
import kh.com.kshrd.shortcourse.services.CourseService;
import kh.com.kshrd.shortcourse.utilities.Pagination;

@RestController
@RequestMapping("/v1/api/admin/courses")
public class RestCourseController {
	
	@Autowired
	private CourseService courseService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseModel<List<Course>> findAllCourses(CourseFilter filter, Pagination pagination) throws BusinessException{
		System.out.println("FILTERING ==> " + filter);
		ResponseModel<List<Course>> response = new ResponseModel<List<Course>>();
		response.setCode(StatusCode.SUCCESS);
		response.setData(courseService.findAllCourses(filter, pagination));
		response.setPagination(pagination);
		return response;
	}

}
