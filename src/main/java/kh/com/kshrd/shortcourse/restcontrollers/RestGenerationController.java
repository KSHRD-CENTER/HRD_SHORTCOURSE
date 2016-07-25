package kh.com.kshrd.shortcourse.restcontrollers;

import javax.validation.Valid;

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
import kh.com.kshrd.shortcourse.filtering.GenerationFilter;
import kh.com.kshrd.shortcourse.forms.GenerationForm;
import kh.com.kshrd.shortcourse.models.Course;
import kh.com.kshrd.shortcourse.models.CourseType;
import kh.com.kshrd.shortcourse.models.Generation;
import kh.com.kshrd.shortcourse.models.Response;
import kh.com.kshrd.shortcourse.models.ResponseList;
import kh.com.kshrd.shortcourse.models.ResponseRecord;
import kh.com.kshrd.shortcourse.models.StatusCode;
import kh.com.kshrd.shortcourse.models.User;
import kh.com.kshrd.shortcourse.services.CourseService;
import kh.com.kshrd.shortcourse.services.GenerationService;
import kh.com.kshrd.shortcourse.utilities.Pagination;

@RestController
@RequestMapping("/v1/api/admin/generations")
public class RestGenerationController {
	
	@Autowired
	private GenerationService generationService;
	
	@Autowired
	private CourseService courseService;
	
	@ApiImplicitParams({
		@ApiImplicitParam(name = "name", dataType = "string", paramType = "query",
	            value = "GENERATION NAME"),
		@ApiImplicitParam(name = "courseId", dataType = "integer", paramType = "query",
        	value = "COURSE TYPE ID"),
		@ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", defaultValue="1",
        	value = "Results page you want to retrieve (1..N)"),
		@ApiImplicitParam(name = "limit", dataType = "integer", paramType = "query", defaultValue="15",
        	value = "Number of records per page."),
	})
	@RequestMapping(method = RequestMethod.GET)
	public ResponseList<Generation> findAllGenerations(@ApiIgnore GenerationFilter filter, @ApiIgnore Pagination pagination) throws BusinessException{
		ResponseList<Generation> response = new ResponseList<Generation>();
		response.setCode(StatusCode.SUCCESS);
		response.setData(generationService.findAllGenerations(filter, pagination));
		response.setPagination(pagination);
		return response;
	}
	
	@RequestMapping(value="/{id}/courses", method = RequestMethod.GET)
	public ResponseList<Course> findAllCoursesByGenerations(@PathVariable("id")Long generationId) throws BusinessException{
		ResponseList<Course> response = new ResponseList<Course>();
		response.setCode(StatusCode.SUCCESS);
		response.setData(courseService.findAllCoursesByGenerationId(generationId));
		return response;
	}
		
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseRecord<Generation> findGeneration(@PathVariable("id") int id){
		ResponseRecord<Generation> response = new ResponseRecord<>();
		try {
			Generation generation = generationService.findGeneration(id);
			if(generation != null){
				response.setCode(StatusCode.SUCCESS);
				response.setData(generation);
			}else{
				response.setCode(StatusCode.NOT_SUCCESS);
			}
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public Response addGeneration(@Valid @RequestBody GenerationForm generationForm){
		Response response = new Response();
		try{
			Generation generation = new Generation();
			CourseType courseType = new CourseType();
			User user = new User();
			courseType.setId(generationForm.getCourseType());
			user.setId(1L);
			generation.setCourseType(courseType);
			generation.setCreatedBy(user);
			generation.setStatus("1");
			generation.setName(generationForm.getName());
			generation.setIsDefault(generationForm.getIsDefault());
			if(generationService.addGeneration(generation) > 0){
				response.setCode(StatusCode.SUCCESS);
				response.setMessage("GENERATION HAS BEEN INSERTED SUCCESSFULLY.");
			}else{
				response.setCode(StatusCode.NOT_SUCCESS);
				response.setMessage("GENERATION HAS NOT BEAN INSERT.");
			}
		}catch(BusinessException e){
			e.printStackTrace();
		}
		return response;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Response deleteGeneration(@PathVariable("id") int id){
		Response response = new Response();
		try{
			if(generationService.deleteGeneration(id)){
				response.setCode(StatusCode.SUCCESS);
			}else{
				response.setCode(StatusCode.NOT_SUCCESS);
			}
		}catch(BusinessException e){
			e.printStackTrace();
		}
		return response;
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public Response updateGeneration(@Valid @RequestBody GenerationForm.UpdateGenerationForm updateGenerationForm){
		Response response = new Response();
		try{
			Generation generation = new Generation();
			CourseType courseType = new CourseType();
			courseType.setId(updateGenerationForm.getCourseType());
			generation.setCourseType(courseType);
			generation.setStatus(updateGenerationForm.getStatus());
			generation.setName(updateGenerationForm.getName());
			generation.setIsDefault(updateGenerationForm.getIsDefault());
			generation.setId(updateGenerationForm.getId());
			if(generationService.updateGeneration(generation) > 0){
				response.setCode(StatusCode.SUCCESS);
			}else{
				response.setCode(StatusCode.NOT_SUCCESS);
			}
		}catch(BusinessException e){
			e.printStackTrace();
		}
		return response;
	}
	
}