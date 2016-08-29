package kh.com.kshrd.shortcourse.restcontrollers;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;

import kh.com.kshrd.shortcourse.configurations.UserSession;
import kh.com.kshrd.shortcourse.filtering.StudentFilter;
import kh.com.kshrd.shortcourse.forms.PaymentForm;
import kh.com.kshrd.shortcourse.forms.StudentForm;
import kh.com.kshrd.shortcourse.models.Course;
import kh.com.kshrd.shortcourse.models.PaymentHistory;
import kh.com.kshrd.shortcourse.models.Response;
import kh.com.kshrd.shortcourse.models.ResponseList;
import kh.com.kshrd.shortcourse.models.ResponseRecord;
import kh.com.kshrd.shortcourse.models.Shift;
import kh.com.kshrd.shortcourse.models.StatusCode;
import kh.com.kshrd.shortcourse.models.Student;
import kh.com.kshrd.shortcourse.models.StudentDetails;
import kh.com.kshrd.shortcourse.models.User;
import kh.com.kshrd.shortcourse.services.PaymentHistoryService;
import kh.com.kshrd.shortcourse.services.StudentService;
import kh.com.kshrd.shortcourse.utilities.Pagination;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;

@RestController
@RequestMapping("/v1/api/admin/students")
public class RestStudentController {
	
	@Autowired
	private StudentService studentService;
	@Autowired
	private DataSource dataSource;
	@Autowired
	private PaymentHistoryService paymentHistoryService;

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
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseRecord<StudentDetails> findOneStudent(@PathVariable("id") Long id){
		ResponseRecord<StudentDetails> response = new ResponseRecord<StudentDetails>();
		try {
			StudentDetails studentDetails = studentService.findStudent(id);
			if(studentDetails!=null){
				response.setCode("0000");
				response.setData(studentService.findStudent(id));
			}else{
				response.setCode("9999");
			}
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
			user.setId(UserSession.getUserSession().getId());
			student.setCreatedBy(user);
			
			for(StudentForm.CourseDetails courseDetails : form.getCourseDetails()){
				StudentDetails studentDetails = new StudentDetails();
				studentDetails.setCost(courseDetails.getCostPrice());
				studentDetails.setDiscount(courseDetails.getDiscount());
				studentDetails.setPaidAmount(courseDetails.getPaid());
				studentDetails.setStatus(form.getStatus());
				
				Course course = new Course();
				course.setId(courseDetails.getCourse());
				studentDetails.setCourse(course);
				
				Shift shift = new Shift();
				shift.setId(courseDetails.getShift());
				studentDetails.setShift(shift);
				
				studentDetails.setRegisteredBy(user);
				
				student.getStudentDetails().add(studentDetails);
			}
			if(studentService.saveStudent(student)!=null){
				response.setCode("0000");
			}else{
				response.setCode("9999");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return response;
	}
	
	@RequestMapping(value = "/shortcourse", method = RequestMethod.POST)
	public Response addNewShortCourseStudent(@RequestBody StudentForm.RegisterStudentForm form){
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
				studentDetails.setStatus(form.getStatus());
				
				Course course = new Course();
				course.setId(courseDetails.getCourse());
				studentDetails.setCourse(course);
				
				Shift shift = new Shift();
				shift.setId(courseDetails.getShift());
				studentDetails.setShift(shift);
				
				studentDetails.setRegisteredBy(user);
				
				student.getStudentDetails().add(studentDetails);
			}
			if(studentService.saveStudent(student)!=null){
				response.setCode("0000");
			}else{
				response.setCode("9999");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return response;
	}
	
	@RequestMapping(value="/{id}/payment-histories",method = RequestMethod.GET)
	public ResponseList<PaymentHistory> findAllPaymentByStudentDetailsId(@PathVariable("id") Long id){
		ResponseList<PaymentHistory> response = new ResponseList<PaymentHistory>();
		try {
			response.setCode("0000");
			response.setData(paymentHistoryService.getAllPaymentHistoryByStudentDetailsId(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	@RequestMapping(value="/{id}/payment-histories", method = RequestMethod.POST)
	public Response saveNewPayment(@PathVariable("id")Long id, @RequestBody PaymentForm.RegisterNewPayment form, HttpServletRequest request, HttpServletResponse res){
		ResponseRecord<String> response = new ResponseRecord<String>();
		try{
			PaymentHistory paymentHistory = new PaymentHistory();
			paymentHistory.setPaidAmount(form.getPaidAmount());
			
			StudentDetails studentDetails = new StudentDetails();
			studentDetails.setId(id);
			
			User user = new User();
			user.setId(UserSession.getUserSession().getId());
			paymentHistory.setStudentDetails(studentDetails);
			paymentHistory.setCreatedBy(user);
			paymentHistory.setPaidBy(user);
			Long paymentId = paymentHistoryService.save(paymentHistory);
			if(paymentId != null){
				response.setCode(StatusCode.SUCCESS);
				printInvoice(paymentId.intValue(), request, response);
			}else{
				response.setCode(StatusCode.NOT_SUCCESS);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return response;
	}
	
	@RequestMapping(value="/{id}/{stuId}", method = RequestMethod.PUT)
	public Response deleteStudent(@PathVariable("id")Long id, @PathVariable("stuId")Long stuId){
		Response response = new Response();
		try{
			if(studentService.deleteStudent(id, stuId)){
				response.setCode(StatusCode.SUCCESS);
			}else{
				response.setCode(StatusCode.NOT_SUCCESS);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return response;
	}
	
	@RequestMapping(value = "/certificate/{id}", method = RequestMethod.GET)
	public Response printCertificate(@PathVariable("id") Long id, HttpServletRequest request){
		Response response = new Response();
		try{
			final DefaultResourceLoader loader = new DefaultResourceLoader();
		    Resource resource = loader.getResource("classpath:static/certificate.png");
		    File myFile = resource.getFile();
			JasperReport jp = JasperCompileManager.compileReport(request.getServletContext().getRealPath("/WEB-INF/reports/certificate.jrxml"));
			Map<String, Object> param = new HashMap<String, Object>();
			String publishedDateString = dateFormat();
			param.put("id", id.intValue());
			param.put("publish_date", publishedDateString);
			param.put("bg_image", myFile.toString());
			JasperPrint print = JasperFillManager.fillReport(jp, param, dataSource.getConnection());
			if(JasperPrintManager.printReport(print, false)){
				response.setCode(StatusCode.SUCCESS);
			}else{
				response.setCode(StatusCode.NOT_SUCCESS);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return response;
	}
	
	@RequestMapping(value = "/certificate/pdf/{id}/{name}", method = RequestMethod.GET)
	public Response exportCertificateToPdf(@PathVariable("id") Long id, @PathVariable("name") String name, HttpServletRequest request){
		ResponseRecord<String> response = new ResponseRecord<String>();
		try{
			final DefaultResourceLoader loader = new DefaultResourceLoader();
		    Resource resource = loader.getResource("classpath:static/certificate.png");
		    File myFile = resource.getFile();
			JasperReport jp = JasperCompileManager.compileReport(request.getServletContext().getRealPath("/WEB-INF/reports/certificate.jrxml"));
			Map<String, Object> param = new HashMap<String, Object>();
			String publishedDateString = dateFormat();
			param.put("id", id.intValue());
			param.put("publish_date", publishedDateString);
			param.put("bg_image", myFile.toString());
			JasperPrint print = JasperFillManager.fillReport(jp, param, dataSource.getConnection());
			File file = new File("/opt/certificate/pdf/");
			if(!file.exists()){
				file.mkdirs();
			}
			String date = new Date().getTime()+"";
			String filename = "/opt/certificate/pdf/"+name + date +".pdf";
			JasperExportManager.exportReportToPdfFile(print, filename);
			response.setData("/certificate/"+ name + date +".pdf");
			response.setCode(StatusCode.SUCCESS);
		}catch(Exception e){
			e.printStackTrace();
		}
		return response;
	}
	
	private void printInvoice(int id, HttpServletRequest request, ResponseRecord<String> response) throws Exception{
		final DefaultResourceLoader loader = new DefaultResourceLoader();
	    Resource resource = loader.getResource("classpath:static/logo.png");
	    File myFile = resource.getFile();
	
		JasperReport jp = JasperCompileManager.compileReport(request.getServletContext().getRealPath("/WEB-INF/reports/receipt.jrxml"));
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		param.put("logo_image", myFile.toString());
		JasperPrint print = JasperFillManager.fillReport(jp, param, dataSource.getConnection());
		
		File file = new File("/opt/payment/pdf/");
		if(!file.exists()){
			file.mkdirs();
		}
		String date = new Date().getTime()+"";
		String filename = "/opt/payment/pdf/"+ date +".pdf";
		JasperExportManager.exportReportToPdfFile(print, filename);
		response.setData("/payment/"+ date +".pdf");
		response.setCode(StatusCode.SUCCESS);
		//JasperViewer.viewReport(print, false);
		//JasperPrintManager.printReport(print, false);	
	}
	
	private static String dateFormat(){
		try{
			Date date = new Date();
			String month = new SimpleDateFormat("MMMM").format(date);
			String day = new SimpleDateFormat("dd").format(date);
			String year = new SimpleDateFormat("yyyy").format(date);
			int d = Integer.parseInt(day);
			switch(d % 10){
				case 1:
					day += "st";
					break;
				case 2:
					day += "nd";
					break;
				case 3:
					day += "rd";
					break;
				default:
					day += "th";
			}
			String dateString = month + " " + day + ", " + year;
			return dateString;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return "";
	}
}