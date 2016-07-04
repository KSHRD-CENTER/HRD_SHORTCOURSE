package kh.com.kshrd.shortcourse.repositories;

import java.sql.SQLException;
import java.util.List;

import kh.com.kshrd.shortcourse.models.Generation;

public interface GenerationRepository {

	public List<Generation> findAll() throws SQLException;
	
	public List<Generation> findAllByCourseTypeId(Long courseTypeId) throws SQLException;
}
