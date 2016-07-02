package kh.com.kshrd.shortcourse.repositories;

import java.sql.SQLException;
import java.util.List;

import kh.com.kshrd.shortcourse.models.Shift;

public interface ShiftRepository {

	public List<Shift> findAll() throws SQLException;
}
