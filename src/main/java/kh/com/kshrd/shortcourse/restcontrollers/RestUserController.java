package kh.com.kshrd.shortcourse.restcontrollers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import kh.com.kshrd.shortcourse.exceptions.BusinessException;
import kh.com.kshrd.shortcourse.filtering.UserFilter;
import kh.com.kshrd.shortcourse.forms.UserForm;
import kh.com.kshrd.shortcourse.models.Response;
import kh.com.kshrd.shortcourse.models.ResponseList;
import kh.com.kshrd.shortcourse.models.ResponseRecord;
import kh.com.kshrd.shortcourse.models.StatusCode;
import kh.com.kshrd.shortcourse.models.User;
import kh.com.kshrd.shortcourse.services.UserService;
import kh.com.kshrd.shortcourse.utilities.Pagination;

@RestController
@RequestMapping("/v1/api/admin/users")
public class RestUserController {
	@Autowired
	private UserService userService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@ApiImplicitParams({
		@ApiImplicitParam(name = "name", dataType = "string", paramType = "query",
	            value = "STUDENT NAME"),
		@ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", defaultValue="1",
        	value = "Results page you want to retrieve (1..N)"),
		@ApiImplicitParam(name = "limit", dataType = "integer", paramType = "query", defaultValue="15",
        	value = "Number of records per page."),
	})
	@RequestMapping(method = RequestMethod.GET)
	public ResponseList<User> getAllUsers(@ApiIgnore UserFilter filter, @ApiIgnore Pagination pagination) throws BusinessException{
		ResponseList<User> response = new ResponseList<>();
		response.setCode(StatusCode.SUCCESS);
		filter.setStatus("1");
		response.setData(userService.getAllUsers(filter, pagination));
		response.setPagination(pagination);
		return response;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseRecord<User> getUserById(@PathVariable("id") Long id) throws BusinessException{
		ResponseRecord<User> response = new ResponseRecord<>();
		response.setCode(StatusCode.SUCCESS);
		String status = "1";
		response.setData(userService.getUserById(status, id));
		return response;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public Response addUser(@Valid @RequestBody UserForm userForm){
		Response response = new Response();
		try{
			User user = new User();
			user.setEmail(userForm.getEmail());
			user.setStatus("1");
			user.setRole(userForm.getRole());
			user.setPassword(passwordEncoder.encode(userForm.getPassword()));
			if(userService.addUser(user) > 0){
				response.setCode(StatusCode.SUCCESS);
			}else{
				response.setCode(StatusCode.NOT_SUCCESS);
			}
		}catch(BusinessException e){
			e.printStackTrace();
		}
		return response;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Response deleteUser(@PathVariable("id") int id){
		Response response = new Response();
		try{
			if(userService.deleteUser(id)){
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
	public Response updateUser(@Valid @RequestBody UserForm.UpdateUserForm updateUserForm){
		Response response = new Response();
		try{
			User user = new User();
			user.setEmail(updateUserForm.getEmail());
			user.setStatus(updateUserForm.getStatus());
			user.setRole(updateUserForm.getRole());
			user.setId(updateUserForm.getId());
			if(userService.updateUser(user) > 0){
				response.setCode(StatusCode.SUCCESS);
			}else{
				response.setCode(StatusCode.NOT_SUCCESS);
			}
		}catch(BusinessException e){
			e.printStackTrace();
		}
		return response;
	}
	
	@RequestMapping(value = "/change-password", method = RequestMethod.PUT)
	public Response changePassword(@Valid @RequestBody UserForm.ChangePasswordForm changePasswordForm){
		Response response = new Response();
		try{
			if(userService.changePassword(changePasswordForm.getOldPassword(), 
										  passwordEncoder.encode(changePasswordForm.getNewPassword()), 
										  changePasswordForm.getId()) > 0){
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
