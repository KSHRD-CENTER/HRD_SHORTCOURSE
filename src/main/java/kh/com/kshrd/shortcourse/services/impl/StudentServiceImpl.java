package kh.com.kshrd.shortcourse.services.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kh.com.kshrd.shortcourse.exceptions.BusinessException;
import kh.com.kshrd.shortcourse.filtering.StudentFilter;
import kh.com.kshrd.shortcourse.models.Student;
import kh.com.kshrd.shortcourse.models.StudentDetails;
import kh.com.kshrd.shortcourse.repositories.StudentRepository;
import kh.com.kshrd.shortcourse.services.StudentService;
import kh.com.kshrd.shortcourse.utilities.Pagination;

@Service
@Transactional
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
	public StudentDetails findStudent(Long id) throws BusinessException {
		try {
			return studentRepository.findOne(id);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BusinessException();
		}
	}

	@Override
	@Transactional
	public Long saveStudent(Student student) throws BusinessException {
		try{
			Long studentId = studentRepository.save(student);
			student.setId(studentId);
			studentRepository.save(student.getStudentDetails(), studentId);
			return studentId;
		}catch(SQLException e){
			e.printStackTrace();
			throw new BusinessException();
		}
	}

	@Override
	@Transactional
	public boolean deleteStudent(Long id, Long stuId) throws BusinessException {
		try{
			return studentRepository.delete(id, stuId);
		}catch(SQLException e){
			e.printStackTrace();
			throw new BusinessException();
		}
	}

}
