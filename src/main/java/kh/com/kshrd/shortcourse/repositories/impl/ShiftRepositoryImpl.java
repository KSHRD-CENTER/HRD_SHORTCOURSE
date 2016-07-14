package kh.com.kshrd.shortcourse.repositories.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import kh.com.kshrd.shortcourse.models.Shift;
import kh.com.kshrd.shortcourse.models.User;
import kh.com.kshrd.shortcourse.repositories.ShiftRepository;

@Repository
public class ShiftRepositoryImpl implements ShiftRepository{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<Shift> findAll() throws SQLException {
		String sql = "SELECT id " 
				   + "	   , name " 
				   + "FROM shifts " 
				   + "WHERE status = '1'";
		return jdbcTemplate.query(sql, new RowMapper<Shift>() {
			@Override
			public Shift mapRow(ResultSet rs, int rowNum) throws SQLException {
				Shift shift = new Shift();
				shift.setId(rs.getLong("id"));
				shift.setName(rs.getString("name"));
				return shift;
			}
		});
	}

	@Override
	public List<Shift> findAllByCourseId(Long courseId) throws SQLException {
		String sql = "SELECT B.id " 
				   + "	   , B.name " 
				   + "FROM course_details A "
				   + "INNER JOIN shifts B ON A.shift = B.id AND B.status = '1' " 
				   + "WHERE A.course_id = ? "
				   + "AND A.status = '1'";
		return jdbcTemplate.query(
				sql, 
				new Object[]{
					courseId	
				},
				new RowMapper<Shift>() {
			@Override
			public Shift mapRow(ResultSet rs, int rowNum) throws SQLException {
				Shift shift = new Shift();
				shift.setId(rs.getLong("id"));
				shift.setName(rs.getString("name"));
				return shift;
			}
		});
	}

	@Override
	public Long save(Shift shift) {
		try{
			Long id = jdbcTemplate.queryForObject("SELECT nextval('shifts_id_seq')",Long.class);
			String sql = "INSERT INTO shifts(id, "
						+ "						  name, "
						+ "						  created_date, "
						+ "						  status, "
						+ "						  created_by, "
						+ "VALUES(? ,? ,TO_CHAR(NOW(),'YYYYMMDDHH24MISS') ,?, ?) ";
			int result = jdbcTemplate.update(
					sql,
					new Object[]{
						id,
						shift.getName(),
						shift.getStatus(),
						shift.getCreatedBy().getId(),
					});
			if(result > 0)
				return id;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Long update(Shift shift) {
		try{
			String sql = "UPDATE shifts "
						+ "SET name = ?, "
						+ "		status = ?, "
						+ "		created_by = ? "
						+ "WHERE id = ?";
			int result = jdbcTemplate.update(
					sql,
					new Object[]{
							shift.getName(),
							shift.getStatus(),
							shift.getCreatedBy().getId(),
							shift.getId()
					});
			if(result > 0){
				return shift.getId();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean delete(int id) {
		try{
			String sql = "UPDATE shifts "
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

	@Override
	public Shift findOne(int id) {
		String sql = "SELECT S.id, "
				+ "		 S.name AS g_name, "
				+ "		 S.status, "
				+ "		 S.created_date,"
				+ "		 U.id AS u_id, "
				+ "		 U.name AS u_name "
				+ "FROM shifts S "
				+ "LEFT JOIN users U "
				+ "ON S.created_by = U.id "
				+ "WHERE S.id = ?";
	return jdbcTemplate.queryForObject(
			sql,
			new Object[]{
				id
			},
			new RowMapper<Shift>(){
				@Override
				public Shift mapRow(ResultSet rs, int numRow) throws SQLException {
					Shift shift = new Shift();
					User user = new User();
					shift.setId(rs.getLong("id"));
					shift.setName(rs.getString("name"));
					user.setId(rs.getLong("U_id"));
					shift.setCreatedBy(user);
					shift.setStatus(rs.getString("status"));
					shift.setCreatedDate(rs.getString("created_date"));
					return shift;
				}
			});
	}
}
