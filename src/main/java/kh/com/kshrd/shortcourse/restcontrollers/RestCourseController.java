package kh.com.kshrd.shortcourse.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;

import kh.com.kshrd.shortcourse.exceptions.BusinessException;
import kh.com.kshrd.shortcourse.filtering.CourseFilter;
import kh.com.kshrd.shortcourse.forms.CourseForm;
import kh.com.kshrd.shortcourse.models.Course;
import kh.com.kshrd.shortcourse.models.Generation;
import kh.com.kshrd.shortcourse.models.Response;
import kh.com.kshrd.shortcourse.models.ResponseList;
import kh.com.kshrd.shortcourse.models.Shift;
import kh.com.kshrd.shortcourse.models.StatusCode;
import kh.com.kshrd.shortcourse.models.User;
import kh.com.kshrd.shortcourse.services.CourseService;
import kh.com.kshrd.shortcourse.services.ShiftService;
import kh.com.kshrd.shortcourse.utilities.Pagination;

@RestController
@RequestMapping("/v1/api/admin/courses")
public class RestCourseController {
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private ShiftService shiftService;
	
	@ApiImplicitParams({
		@ApiImplicitParam(name = "course", dataType = "string", paramType = "query", defaultValue="",
	            value = "Course Title"),
	    @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", defaultValue="1",
	            value = "Results page you want to retrieve (1..N)"),
	    @ApiImplicitParam(name = "limit", dataType = "integer", paramType = "query", defaultValue="15",
	            value = "Number of records per page."),
	})
	@RequestMapping(method = RequestMethod.GET)
	public ResponseList<Course> findAllCourses(@ApiIgnore CourseFilter filter, @ApiIgnore Pagination pagination) throws BusinessException{
		System.out.println("FILTERING ==> " + filter);
		ResponseList<Course> response = new ResponseList<Course>();
		response.setCode(StatusCode.SUCCESS);
		response.setData(courseService.findAllCourses(filter, pagination));
		response.setPagination(pagination);
		return response;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public Response addNewCourse(@RequestBody CourseForm.RegisterCourseForm form) throws BusinessException{
		Response response = new Response();
		Course course = new Course();
		course.setCourse(form.getCourseName());
		course.setDescription(form.getDescription());
		course.setCost(form.getCostPrice());
		course.setDiscount(form.getDiscount());
		course.setStatus(form.getStatus());
		User user = new User();
		user.setId(1L);
		Generation generation = new Generation();
		generation.setId(form.getGeneration());
		for(Long id : form.getShifts()){
			Shift shift = new Shift();
			shift.setId(id);
			shift.setCreatedBy(user);
			course.getShifts().add(shift);
		}
		course.setGeneration(generation);
		course.setCreatedBy(user);
		if(courseService.addNewCourse(course)!=null){
			response.setCode(StatusCode.SUCCESS);
		}else{
			response.setCode(StatusCode.NOT_SUCCESS);
		}
		return response;
	}
	
	@RequestMapping(value="/{id}/shifts", method = RequestMethod.GET)
	public ResponseList<Shift> findAllCourses(@PathVariable("id") Long courseId) throws BusinessException{
		ResponseList<Shift> response = new ResponseList<Shift>();
		response.setCode(StatusCode.SUCCESS);
		response.setData(shiftService.findAllShiftsByCourseId(courseId));
		return response;
	}

}
