package kh.com.kshrd.shortcourse.restcontrollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kh.com.kshrd.shortcourse.exceptions.BusinessException;
import kh.com.kshrd.shortcourse.models.CourseType;
import kh.com.kshrd.shortcourse.models.Generation;
import kh.com.kshrd.shortcourse.models.ResponseModel;
import kh.com.kshrd.shortcourse.models.StatusCode;
import kh.com.kshrd.shortcourse.services.CourseTypeService;
import kh.com.kshrd.shortcourse.services.GenerationService;

@RestController
@RequestMapping("/v1/api/admin/course-types")
public class RestCourseTypeController {
	
	@Autowired
	private CourseTypeService courseTypeService;
	
	@Autowired
	private GenerationService generationService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseModel<List<CourseType>> findAllCourses() throws BusinessException{
		ResponseModel<List<CourseType>> response = new ResponseModel<List<CourseType>>();
		response.setCode(StatusCode.SUCCESS);
		response.setData(courseTypeService.findAllCourseTypes());
		return response;
	}
	
	@RequestMapping(value="/{id}/generations", method = RequestMethod.GET)
	public ResponseModel<List<Generation>> findAllCourses(@PathVariable("id") Long courseTypeId) throws BusinessException{
		ResponseModel<List<Generation>> response = new ResponseModel<List<Generation>>();
		response.setCode(StatusCode.SUCCESS);
		response.setData(generationService.findAllGenerations(courseTypeId));
		return response;
	}

}
