package kh.com.kshrd.shortcourse.repositories.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import kh.com.kshrd.shortcourse.models.CourseType;
import kh.com.kshrd.shortcourse.models.Generation;
import kh.com.kshrd.shortcourse.models.User;
import kh.com.kshrd.shortcourse.repositories.GenerationRepository;

@Repository
public class GenerationRepositoryImpl implements GenerationRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Generation> findAll() throws SQLException {
		String sql = "SELECT A.id " 
				   + "	   , A.name "
				   + "	   , B.name AS course_type " 
				   + "FROM generations A " 
				   + "INNER JOIN course_types B ON B.id = A.course_type AND B.status ='1' " 
				   + "WHERE A.status = '1' "
				   + "ORDER BY B.id ";

		return jdbcTemplate.query(sql, new RowMapper<Generation>() {
			@Override
			public Generation mapRow(ResultSet rs, int rowNum) throws SQLException {
				Generation generation = new Generation();
				generation.setId(rs.getLong("id"));
				generation.setName(rs.getString("name"));
				
				CourseType courseType = new CourseType();
				courseType.setName(rs.getString("course_type"));
				generation.setCourseType(courseType);
				return generation;
			}
		});
	}

	@Override
	public List<Generation> findAllByCourseTypeId(Long courseTypeId) throws SQLException {
		String sql = "SELECT id " 
				   + "	   , name " 
				   + "FROM generations " 
				   + "WHERE course_type = ? "
				   + "AND status = '1'";

		return jdbcTemplate.query(
				sql,
				new Object[]{
					courseTypeId	
				},
				new RowMapper<Generation>() {
			@Override
			public Generation mapRow(ResultSet rs, int rowNum) throws SQLException {
				Generation generation = new Generation();
				generation.setId(rs.getLong("id"));
				generation.setName(rs.getString("name"));
				return generation;
			}
		});
	}

	@Override
	public Generation findOne(int id) {
		String sql = "SELECT G.id, "
					+ "		 G.name AS g_name, "
					+ "		 G.status, "
					+ "		 G.created_date, "
					+ "		 U.email, "
					+ "		 CT.name AS ct_name "
					+ "FROM generations G "
					+ "LEFT JOIN users U "
					+ "ON G.created_by = U.id "
					+ "LEFT JOIN course_types CT "
					+ "ON G.course_type = CT.id "
					+ "WHERE G.id = ?";
		return jdbcTemplate.queryForObject(
				sql, 
				new Object[]{
						id
				}, new RowMapper<Generation>(){
					@Override
					public Generation mapRow(ResultSet rs, int rowNum) throws SQLException {
						Generation generation = new Generation();
						User user = new User();
						CourseType courseType = new CourseType();
						generation.setId(rs.getLong("id"));
						user.setEmail(rs.getString("email"));
						generation.setCreatedBy(user);
						courseType.setName(rs.getString("ct_name"));
						generation.setCourseType(courseType);
						generation.setName(rs.getString("g_name"));
						generation.setCreatedDate(rs.getString("created_date"));
						generation.setStatus(rs.getString("status"));
						return generation;
					}
				});
	}

	@Override
	public Long save(Generation generation) {
		try{
			Long id = jdbcTemplate.queryForObject("SELECT nextval('generations_id_seq')",Long.class);
			String sql = "INSERT INTO generations(id, "
						+ "						  name, "
						+ "						  status, "
						+ "						  is_default, "
						+ "						  created_date, "
						+ "						  created_by, "
						+ "						  course_type) "
						+ "VALUES(? ,? ,? ,? ,TO_CHAR(NOW(),'YYYYMMDDHH24MISS') ,?, ?) ";
			int result = jdbcTemplate.update(
					sql,
					new Object[]{
						id,
						generation.getName(),
						generation.getStatus(),
						generation.getIsDefault(),
						generation.getCreatedBy().getId(),
						generation.getCourseType().getId()
					});
			if(result > 0)
				return id;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Long update(Generation generation) {
		try{
			String sql = "UPDATE generations "
						+ "SET name = ?, "
						+ "		status = ?, "
						+ "		is_default = ?, "
						+ "		course_type = ? "
						+ "WHERE id = ?";
			int result = jdbcTemplate.update(
					sql,
					new Object[]{
							generation.getName(),
							generation.getStatus(),
							generation.getIsDefault(),
							generation.getCourseType().getId(),
							generation.getId()
					});
			if(result > 0)
				return generation.getId();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean delete(int id) {
		try{
			String sql = "UPDATE generations "
						+ "SET status = 0 "
						+ "WHERE id = ?";
			int result = jdbcTemplate.update(
					sql,
					new Object[]{
						id
					});
			if(result > 0)
				return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
}
