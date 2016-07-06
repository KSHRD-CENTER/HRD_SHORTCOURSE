package kh.com.kshrd.shortcourse.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;

import kh.com.kshrd.shortcourse.filtering.StudentFilter;
import kh.com.kshrd.shortcourse.forms.StudentForm;
import kh.com.kshrd.shortcourse.models.Course;
import kh.com.kshrd.shortcourse.models.Generation;
import kh.com.kshrd.shortcourse.models.Response;
import kh.com.kshrd.shortcourse.models.ResponseList;
import kh.com.kshrd.shortcourse.models.Shift;
import kh.com.kshrd.shortcourse.models.Student;
import kh.com.kshrd.shortcourse.models.StudentDetails;
import kh.com.kshrd.shortcourse.models.User;
import kh.com.kshrd.shortcourse.services.StudentService;
import kh.com.kshrd.shortcourse.utilities.Pagination;

@RestController
@RequestMapping("/v1/api/admin/students")
public class RestStudentController {
	
	@Autowired
	private StudentService studentService;

	@ApiImplicitParams({
	    @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", defaultValue="1",
	            value = "Results page you want to retrieve (1..N)"),
	    @ApiImplicitParam(name = "limit", dataType = "integer", paramType = "query", defaultValue="15",
	            value = "Number of records per page."),
	})
	@RequestMapping(method = RequestMethod.GET)
	public ResponseList<StudentDetails> findAllStudents(@ApiIgnore StudentFilter filter, @ApiIgnore Pagination pagination){
		ResponseList<StudentDetails> response = new ResponseList<StudentDetails>();
		try {
			response.setCode("0000");
			response.setData(studentService.findAllStudents(filter, pagination));
			response.setPagination(pagination);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public Response addNewStudent(@RequestBody StudentForm.RegisterStudentForm form){
		Response response = new Response();
		try{
			Student student = new Student();
			student.setName(form.getStudentName());
			student.setGender(form.getGender());
			student.setEmail(form.getEmail());
			student.setTelephone(form.getTelephone());
			student.setAddress(form.getAddress());
			student.setUniversity(form.getUniversity());
			student.setYear(form.getYear());
			student.setStatus(form.getStatus());
			
			User user = new User();
			user.setId(1L);
			student.setCreatedBy(user);
			
			for(StudentForm.CourseDetails courseDetails : form.getCourseDetails()){
				StudentDetails studentDetails = new StudentDetails();
				studentDetails.setCost(courseDetails.getCostPrice());
				studentDetails.setDiscount(courseDetails.getDiscount());
				studentDetails.setPaidAmount(courseDetails.getPaid());
				
				Course course = new Course();
				course.setId(courseDetails.getCourse());
				studentDetails.setCourse(course);
				
				Shift shift = new Shift();
				shift.setId(courseDetails.getShift());
				studentDetails.setShift(shift);
				
				studentDetails.setRegisteredBy(user);
				
				student.getStudentDetails().add(studentDetails);
			}
			if(studentService.save(student)!=null){
				response.setCode("0000");
			}else{
				response.setCode("9999");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return response;
	}
	
}
