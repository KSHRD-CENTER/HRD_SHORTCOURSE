package kh.com.kshrd.shortcourse.repositories;

import java.sql.SQLException;
import java.util.List;

import kh.com.kshrd.shortcourse.models.Shift;

public interface ShiftRepository {
	public List<Shift> findAll() throws SQLException;
	public List<Shift> findAllByCourseId(Long courseId) throws SQLException;
	public Shift findOne(int id) throws SQLException;
	public Long save(Shift shift) throws SQLException;
	public Long update(Shift shift) throws SQLException;
	public boolean delete(int id) throws SQLException;
}