package kh.com.kshrd.shortcourse.services;

import java.util.List;

import kh.com.kshrd.shortcourse.exceptions.BusinessException;
import kh.com.kshrd.shortcourse.filtering.StudentFilter;
import kh.com.kshrd.shortcourse.models.PaymentHistory;
import kh.com.kshrd.shortcourse.models.Student;
import kh.com.kshrd.shortcourse.models.StudentDetails;
import kh.com.kshrd.shortcourse.utilities.Pagination;

public interface StudentService {
	
	public List<StudentDetails> findAllStudents(StudentFilter filter, Pagination pagination) throws BusinessException;
	
	public Long save(Student student) throws BusinessException;
	
}
