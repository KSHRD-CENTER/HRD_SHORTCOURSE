package kh.com.kshrd.shortcourse.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kh.com.kshrd.shortcourse.exceptions.BusinessException;
import kh.com.kshrd.shortcourse.models.ResponseList;
import kh.com.kshrd.shortcourse.models.Shift;
import kh.com.kshrd.shortcourse.models.StatusCode;
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
		response.setData(shiftService.findAllShifts());
		return response;
	}
	
	

}
