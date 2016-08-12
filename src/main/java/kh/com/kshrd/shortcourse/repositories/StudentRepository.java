package kh.com.kshrd.shortcourse.repositories;

import java.sql.SQLException;
import java.util.List;

import kh.com.kshrd.shortcourse.filtering.StudentFilter;
import kh.com.kshrd.shortcourse.models.Student;
import kh.com.kshrd.shortcourse.models.StudentDetails;
import kh.com.kshrd.shortcourse.utilities.Pagination;

public interface StudentRepository {

	public List<StudentDetails> findAll(StudentFilter filter, Pagination pagination) throws SQLException;
	
	public StudentDetails findOne(Long id) throws SQLException;
	
	public Long count(StudentFilter filter) throws SQLException;
	
	public Long save(Student student) throws SQLException;
	
	public int[] save(List<StudentDetails> studentDetails, Long studentId);
	
	public boolean delete(Long id, Long stuId) throws SQLException;
	
	public Long update(Student student) throws SQLException;
	
}
