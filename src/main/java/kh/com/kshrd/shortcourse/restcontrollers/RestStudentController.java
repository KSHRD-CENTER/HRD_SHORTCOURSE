package kh.com.kshrd.shortcourse.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;

import kh.com.kshrd.shortcourse.exceptions.BusinessException;
import kh.com.kshrd.shortcourse.filtering.StudentFilter;
import kh.com.kshrd.shortcourse.models.Response;
import kh.com.kshrd.shortcourse.models.ResponseList;
import kh.com.kshrd.shortcourse.models.StudentDetails;
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
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	public Response saveStudent(@RequestBody Stu)
}
