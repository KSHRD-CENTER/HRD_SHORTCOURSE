package kh.com.kshrd.shortcourse.services.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kh.com.kshrd.shortcourse.exceptions.BusinessException;
import kh.com.kshrd.shortcourse.filtering.StudentFilter;
import kh.com.kshrd.shortcourse.models.Student;
import kh.com.kshrd.shortcourse.models.StudentDetails;
import kh.com.kshrd.shortcourse.repositories.StudentRepository;
import kh.com.kshrd.shortcourse.services.StudentService;
import kh.com.kshrd.shortcourse.utilities.Pagination;

@Service
public class StudentServiceImpl implements StudentService{

	@Autowired
	private StudentRepository studentRepository;
	
	@Override
	public List<StudentDetails> findAllStudents(StudentFilter filter, Pagination pagination) throws BusinessException {
		try {
			return studentRepository.findAll(filter, pagination);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BusinessException();
		}
	}

	@Override
	public Long save(Student student) throws BusinessException {
		try{
			return studentRepository.save(student);
		}catch(SQLException e){
			e.printStackTrace();
			throw new BusinessException();
		}
	}
	
}
