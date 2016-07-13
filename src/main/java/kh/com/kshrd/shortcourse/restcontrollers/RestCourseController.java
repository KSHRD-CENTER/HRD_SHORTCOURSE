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
import kh.com.kshrd.shortcourse.forms.CourseForm.CourseDetailsForm;
import kh.com.kshrd.shortcourse.models.Course;
import kh.com.kshrd.shortcourse.models.CourseDetails;
import kh.com.kshrd.shortcourse.models.Generation;
import kh.com.kshrd.shortcourse.models.Response;
import kh.com.kshrd.shortcourse.models.ResponseList;
import kh.com.kshrd.shortcourse.models.ResponseRecord;
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
	
	//TODO: TO ADD THE IMPLICIT PARAM IN THE SWAGGER
	@ApiImplicitParams({
		@ApiImplicitParam(name = "course", dataType = "string", paramType = "query", defaultValue="",
	            value = "Course Title"),
	    @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", defaultValue="1",
	            value = "Results page you want to retrieve (1..N)"),
	    @ApiImplicitParam(name = "limit", dataType = "integer", paramType = "query", defaultValue="15",
	            value = "Number of records per page."),
	})
	//TODO: TO FIND ALL COURSES WITH FILTERING AND PAGINATION
	@RequestMapping(method = RequestMethod.GET)
	public ResponseList<Course> findAllCourses(@ApiIgnore CourseFilter filter, @ApiIgnore Pagination pagination) throws BusinessException{
		System.out.println("FILTERING ==> " + filter);
		ResponseList<Course> response = new ResponseList<Course>();
		response.setCode(StatusCode.SUCCESS);
		response.setData(courseService.findAllCourses(filter, pagination));
		response.setPagination(pagination);
		return response;
	}
	
	//TODO: TO REGISTER A NEW COURSE
	@RequestMapping(method = RequestMethod.POST)
	public Response addNewCourse(@RequestBody CourseForm.RegisterCourseForm form) throws BusinessException{
		
		System.out.println("FORMING ==> " + form);
		Response response = new Response();
		Course course = new Course();
		course.setCourse(form.getCourseName());
		course.setDescription(form.getDescription());
		course.setCost(form.getCostPrice());
		course.setDiscount(form.getDiscount());
		course.setStatus(form.getStatus());
		course.setTotalHour(form.getTotalHour());
		User user = new User();
		user.setId(1L);
		Generation generation = new Generation();
		generation.setId(form.getGeneration());
		for(CourseDetailsForm courseDetailsForm : form.getCourseDetails()){
			Shift shift = new Shift();
			shift.setId(courseDetailsForm.getShift());
			shift.setCreatedBy(user);
			course.getShifts().add(shift);
			CourseDetails courseDetails = new CourseDetails();
			courseDetails.setShift(shift);
			courseDetails.setCreatedBy(user);
			courseDetails.setStartDate(courseDetailsForm.getStartDate());
			course.getCourseDetails().add(courseDetails);
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
	
	//TODO: TO FIND ALL SHIFTS BY COURSE ID
	@RequestMapping(value="/{id}/shifts", method = RequestMethod.GET)
	public ResponseList<Shift> findAllCourses(@PathVariable("id") Long courseId) throws BusinessException{
		ResponseList<Shift> response = new ResponseList<Shift>();
		response.setCode(StatusCode.SUCCESS);
		response.setData(shiftService.findAllShiftsByCourseId(courseId));
		return response;
	}
	
	//TODO: TO FIND COURSE BY ID
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseRecord<Course> findCourseById(@PathVariable("id") Long id) throws BusinessException{
		ResponseRecord<Course> response = new ResponseRecord<Course>();
		Course course = courseService.findCourseById(id);
		if(course!=null){
			response.setCode(StatusCode.SUCCESS);
			response.setData(course);
		}else{
			response.setCode(StatusCode.NOT_SUCCESS);
		}
		return response;
	}
	
	//TODO: TO UPDATE THE COURSE BY ID
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
		public ResponseRecord<Course> updateCourse(@PathVariable("id") Long id, @RequestBody CourseForm.UpdateCourseForm form) throws BusinessException{
			ResponseRecord<Course> response = new ResponseRecord<Course>();
			Course course = new Course();
			course.setId(id);
			course.setCourse(form.getCourseName());
			course.setDescription(form.getDescription());
			course.setCost(form.getCostPrice());
			course.setDiscount(form.getDiscount());
			course.setStatus(form.getStatus());
			course.setTotalHour(form.getTotalHour());
			User user = new User();
			user.setId(1L);
			Generation generation = new Generation();
			generation.setId(form.getGeneration());
			course.setUpdatedBy(user);
			for(CourseDetailsForm courseDetailsForm : form.getCourseDetails()){
				Shift shift = new Shift();
				shift.setId(courseDetailsForm.getShift());
				shift.setCreatedBy(user);
				course.getShifts().add(shift);
				CourseDetails courseDetails = new CourseDetails();
				courseDetails.setShift(shift);
				courseDetails.setCreatedBy(user);
				courseDetails.setStartDate(courseDetailsForm.getStartDate());
				course.getCourseDetails().add(courseDetails);
			}
			course.setGeneration(generation);
			
			course = courseService.updateCourse(course);
			if(course!=null){
				response.setCode(StatusCode.SUCCESS);
				response.setData(course);
			}else{
				response.setCode(StatusCode.NOT_SUCCESS);
			}
			return response;
		}

	//TODO: TO DELETE THE COURSE BY ID
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public Response deleteCourse(@PathVariable("id") Long id) throws BusinessException{
		Response response = new Response();
		if(courseService.deleteCourse(id)){
			response.setCode(StatusCode.SUCCESS);			
		}else{
			response.setCode(StatusCode.NOT_SUCCESS);
		}
		return response;
	}

}
