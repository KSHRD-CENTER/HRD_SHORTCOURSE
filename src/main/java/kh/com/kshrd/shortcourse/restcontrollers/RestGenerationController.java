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
import kh.com.kshrd.shortcourse.models.ResponseList;
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
	public ResponseList<Generation> findAllCourses() throws BusinessException{
		ResponseList<Generation> response = new ResponseList<Generation>();
		response.setCode(StatusCode.SUCCESS);
		response.setData(generationService.findAllGenerations());
		return response;
	}
	
	@RequestMapping(value="/{id}/courses", method = RequestMethod.GET)
	public ResponseList<Course> findAllCoursesByGenerations(@PathVariable("id")Long generationId) throws BusinessException{
		ResponseList<Course> response = new ResponseList<Course>();
		response.setCode(StatusCode.SUCCESS);
		response.setData(courseService.findAllCoursesByGenerationId(generationId));
		return response;
	}

}
