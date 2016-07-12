package kh.com.kshrd.shortcourse.repositories;

import java.sql.SQLException;
import java.util.List;

import kh.com.kshrd.shortcourse.models.Generation;

public interface GenerationRepository {

	public List<Generation> findAll() throws SQLException;
	public List<Generation> findAllByCourseTypeId(Long courseTypeId) throws SQLException;
	public Generation findOne(int id) throws SQLException;
	public Long save(Generation generation) throws SQLException;
	public Long update(Generation generation) throws SQLException;
	public boolean delete(int id) throws SQLException;
}
