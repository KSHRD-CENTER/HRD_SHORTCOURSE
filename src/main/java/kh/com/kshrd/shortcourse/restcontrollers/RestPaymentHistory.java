package kh.com.kshrd.shortcourse.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import kh.com.kshrd.shortcourse.exceptions.BusinessException;
import kh.com.kshrd.shortcourse.models.PaymentHistory;
import kh.com.kshrd.shortcourse.models.ResponseList;
import kh.com.kshrd.shortcourse.models.StatusCode;
import kh.com.kshrd.shortcourse.services.PaymentHistoryService;
import kh.com.kshrd.shortcourse.utilities.Pagination;

@RestController
@RequestMapping("/v1/api/admin/transactions")
public class RestPaymentHistory {
	@Autowired
	private PaymentHistoryService paymentHistoryService;
	
	@ApiImplicitParams({
		@ApiImplicitParam(name = "startdate", dataType = "string", paramType = "query",
	            value = "START DATE"),
		@ApiImplicitParam(name = "enddate", dataType = "string", paramType = "query",
        	value = "END DATE"),
		@ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", defaultValue="1",
    		value = "Results page you want to retrieve (1..N)"),
		@ApiImplicitParam(name = "limit", dataType = "integer", paramType = "query", defaultValue="15",
    		value = "Number of records per page."),
	})
	@RequestMapping(method = RequestMethod.GET)
	public ResponseList<PaymentHistory> getAllPaymentHistory(@ApiIgnore String startDate, @ApiIgnore String endDate, @ApiIgnore Pagination pagination) throws BusinessException{
		ResponseList<PaymentHistory> response = new ResponseList<PaymentHistory>();
		response.setData(paymentHistoryService.getAllPaymentHistory(startDate, endDate, pagination));
		response.setCode(StatusCode.SUCCESS);
		response.setPagination(pagination);
		return response;
	}
}
