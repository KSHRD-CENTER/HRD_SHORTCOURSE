package kh.com.kshrd.shortcourse.restcontrollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kh.com.kshrd.shortcourse.exceptions.BusinessException;
import kh.com.kshrd.shortcourse.filtering.CourseFilter;
import kh.com.kshrd.shortcourse.models.CourseType;
import kh.com.kshrd.shortcourse.models.ResponseModel;
import kh.com.kshrd.shortcourse.models.StatusCode;
import kh.com.kshrd.shortcourse.services.CourseTypeService;
import kh.com.kshrd.shortcourse.utilities.Pagination;

@RestController
@RequestMapping("/v1/api/admin/course-types")
public class RestCourseTypeController {
	
	@Autowired
	private CourseTypeService courseTypeService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseModel<List<CourseType>> findAllCourses(CourseFilter filter, Pagination pagination) throws BusinessException{
		ResponseModel<List<CourseType>> response = new ResponseModel<List<CourseType>>();
		response.setCode(StatusCode.SUCCESS);
		response.setData(courseTypeService.findAllCourseTypes());
		return response;
	}

}
