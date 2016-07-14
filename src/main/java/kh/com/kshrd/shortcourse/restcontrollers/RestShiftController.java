package kh.com.kshrd.shortcourse.restcontrollers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kh.com.kshrd.shortcourse.exceptions.BusinessException;
import kh.com.kshrd.shortcourse.forms.ShiftForm;
import kh.com.kshrd.shortcourse.models.Response;
import kh.com.kshrd.shortcourse.models.ResponseList;
import kh.com.kshrd.shortcourse.models.ResponseRecord;
import kh.com.kshrd.shortcourse.models.Shift;
import kh.com.kshrd.shortcourse.models.StatusCode;
import kh.com.kshrd.shortcourse.models.User;
import kh.com.kshrd.shortcourse.services.ShiftService;

@RestController
@RequestMapping("/v1/api/admin/shifts")
public class RestShiftController {
	
	@Autowired
	private ShiftService shiftService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseList<Shift> findAllCourses() throws BusinessException{
		ResponseList<Shift> response = new ResponseList<Shift>();
		response.setCode(StatusCode.SUCCESS);
		response.setData(shiftService.findAllShiftItems());
		return response;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseRecord<Shift> getShiftById(@PathVariable("id")int id){
		ResponseRecord<Shift> response = new ResponseRecord<>();
		try{
			Shift  shift = shiftService.findShiftById(id);	
			if(shift != null){
				response.setCode(StatusCode.SUCCESS);
				response.setData(shift);
			}else{
				response.setCode(StatusCode.NOT_SUCCESS);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return response;
	}

	@RequestMapping(method = RequestMethod.POST)
	public Response addShift(@Valid @RequestBody ShiftForm shiftForm){
		Response response = new Response();
		try{
			Shift shift = new Shift();
			User user = new User();
			shift.setName(shiftForm.getName());
			user.setId(1L);
			shift.setCreatedBy(user);
			shift.setStatus("1");
			if(shiftService.addShift(shift) > 0){
				response.setCode(StatusCode.SUCCESS);
			}else{
				response.setCode(StatusCode.NOT_SUCCESS);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return response;
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public Response updateShift(@Valid @RequestBody ShiftForm.UpdateShiftForm updateShiftForm){
		Response response = new Response();
		try{
			Shift shift = new Shift();
			User user = new User();
			shift.setId(updateShiftForm.getId());
			shift.setName(updateShiftForm.getName());
			shift.setStatus(updateShiftForm.getStatus());
			user.setId(1L);
			shift.setId(updateShiftForm.getId());
			shift.setCreatedBy(user);
			if(shiftService.updateShift(shift) > 0){
				response.setCode(StatusCode.SUCCESS);
			}else{
				response.setCode(StatusCode.NOT_SUCCESS);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return response;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Response deleteShift(@PathVariable("id") int id){
		Response response = new Response();
		try {
			if(shiftService.deleteShift(id)){
				response.setCode(StatusCode.SUCCESS);
			}else{
				response.setCode(StatusCode.NOT_SUCCESS);
			}
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		return response;
	}
}
