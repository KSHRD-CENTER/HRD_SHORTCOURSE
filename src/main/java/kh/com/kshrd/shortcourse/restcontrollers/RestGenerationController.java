package kh.com.kshrd.shortcourse.restcontrollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kh.com.kshrd.shortcourse.exceptions.BusinessException;
import kh.com.kshrd.shortcourse.models.Course;
import kh.com.kshrd.shortcourse.models.Generation;
import kh.com.kshrd.shortcourse.models.ResponseModel;
import kh.com.kshrd.shortcourse.models.StatusCode;
import kh.com.kshrd.shortcourse.services.CourseService;
import kh.com.kshrd.shortcourse.services.GenerationService;

@RestController
@RequestMapping("/v1/api/admin/generations")
public class RestGenerationController {
	
	@Autowired
	private GenerationService generationService;
	
	@Autowired
	private CourseService courseService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseModel<List<Generation>> findAllCourses() throws BusinessException{
		ResponseModel<List<Generation>> response = new ResponseModel<List<Generation>>();
		response.setCode(StatusCode.SUCCESS);
		response.setData(generationService.findAllGenerations());
		return response;
	}
	
	@RequestMapping(value="/{id}/courses", method = RequestMethod.GET)
	public ResponseModel<List<Course>> findAllCoursesByGenerations(@PathVariable("id")Long generationId) throws BusinessException{
		ResponseModel<List<Course>> response = new ResponseModel<List<Course>>();
		response.setCode(StatusCode.SUCCESS);
		response.setData(courseService.findAllCoursesByGenerationId(generationId));
		return response;
	}

}
