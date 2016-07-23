package kh.com.kshrd.shortcourse.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;

import kh.com.kshrd.shortcourse.exceptions.BusinessException;
import kh.com.kshrd.shortcourse.filtering.DashboardFilter;
import kh.com.kshrd.shortcourse.models.Balance;
import kh.com.kshrd.shortcourse.models.PaymentHistory;
import kh.com.kshrd.shortcourse.models.ResponseList;
import kh.com.kshrd.shortcourse.models.ResponseRecord;
import kh.com.kshrd.shortcourse.models.StatusCode;
import kh.com.kshrd.shortcourse.services.DashboardService;
import kh.com.kshrd.shortcourse.utilities.Pagination;

@RestController
@RequestMapping("/v1/api/admin/dashboards")
public class RestDashboardController {
	@Autowired
	private DashboardService dashboardService;
	
	//TODO: TO ADD THE IMPLICIT PARAM IN THE SWAGGER
	@ApiImplicitParams({
		@ApiImplicitParam(name = "generationId", dataType = "string", paramType = "query", defaultValue="",
	            value = "Generation Id"),
	    @ApiImplicitParam(name = "courseTypeId", dataType = "string", paramType = "query", defaultValue="",
	            value = "Course Type Id"),
	    @ApiImplicitParam(name = "courseId", dataType = "string", paramType = "query", defaultValue="",
	            value = "Course Id"),
	    @ApiImplicitParam(name = "courseName", dataType = "string", paramType = "query", defaultValue="",
	    	value = "Course Name"),
	})
	@RequestMapping(value ="/count", method = RequestMethod.GET)
	public ResponseRecord<Balance> countTotalMoney(@ApiIgnore DashboardFilter filter) throws BusinessException{
		ResponseRecord<Balance> response = new ResponseRecord<>();
		response.setCode(StatusCode.SUCCESS);
		System.out.println("FILTER ===================> " + filter);
		response.setData(dashboardService.countTotalMoney(filter));
		return response;
	}
	
	//TODO: TO ADD THE IMPLICIT PARAM IN THE SWAGGER
		@ApiImplicitParams({
			@ApiImplicitParam(name = "generationId", dataType = "string", paramType = "query", defaultValue="",
		            value = "Generation Id"),
		    @ApiImplicitParam(name = "courseTypeId", dataType = "string", paramType = "query", defaultValue="",
		            value = "Course Type Id"),
		    @ApiImplicitParam(name = "courseId", dataType = "string", paramType = "query", defaultValue="",
		            value = "Course Id"),
		    @ApiImplicitParam(name = "courseName", dataType = "string", paramType = "query", defaultValue="",
		    	value = "Course Name"),
		})
		@RequestMapping(value ="/count-money", method = RequestMethod.GET)
		public ResponseRecord<Balance> countMoney(@ApiIgnore DashboardFilter filter) throws BusinessException{
			ResponseRecord<Balance> response = new ResponseRecord<>();
			response.setCode(StatusCode.SUCCESS);
			response.setData(dashboardService.countMoney(filter));
			return response;
		}
	
	@ApiImplicitParams({
		@ApiImplicitParam(name = "generationId", dataType = "string", paramType = "query", defaultValue="",
	            value = "Generation Id"),
	    @ApiImplicitParam(name = "courseTypeId", dataType = "string", paramType = "query", defaultValue="",
	            value = "Course Type Id"),
	    @ApiImplicitParam(name = "courseId", dataType = "string", paramType = "query", defaultValue="",
	            value = "Course Id"),
	    @ApiImplicitParam(name = "courseName", dataType = "string", paramType = "query", defaultValue="",
	    	value = "Course Name"),
	    @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", defaultValue="1",
    		value = "Results page you want to retrieve (1..N)"),
	    @ApiImplicitParam(name = "limit", dataType = "integer", paramType = "query", defaultValue="15",
    		value = "Number of records per page."),
	})
	@RequestMapping(method = RequestMethod.GET)
	public ResponseList<PaymentHistory> getAllPaymentHistories(@ApiIgnore DashboardFilter filter, @ApiIgnore Pagination pagination) throws BusinessException{
		ResponseList<PaymentHistory> response = new ResponseList<>();
		response.setCode(StatusCode.SUCCESS);
		response.setData(dashboardService.getAllPaymentHistories(filter, pagination));
		response.setPagination(pagination);
		return response;
	}
}
