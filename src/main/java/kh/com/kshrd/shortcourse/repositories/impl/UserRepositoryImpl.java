package kh.com.kshrd.shortcourse.repositories.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import kh.com.kshrd.shortcourse.filtering.UserFilter;
import kh.com.kshrd.shortcourse.models.User;
import kh.com.kshrd.shortcourse.repositories.UserRepository;
import kh.com.kshrd.shortcourse.utilities.Pagination;

@Repository
public class UserRepositoryImpl implements UserRepository{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<User> findAll(UserFilter filter, Pagination pagination)  throws SQLException{
		pagination.setTotalCount(this.count(filter));
		String sql = "SELECT id, "
					+ "		 email, "
					+ "		 role, "
					+ "		 created_date, "
					+ "		 status "
					+ "FROM users "
					+ "WHERE status = ? "
					+ "AND email LIKE ? "
					+ "LIMIT ? "
					+ "OFFSET ?";
		return jdbcTemplate.query(
				sql,
				new Object[]{
						filter.getStatus(),
						"%"+filter.getName()+"%",
						pagination.getLimit(), 
						pagination.offset()
				},
				new RowMapper<User>(){
			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException{
				User user = new User();
				user.setId(rs.getLong("id"));
				user.setEmail(rs.getString("email"));
				user.setRole(rs.getString("role"));
				user.setCreatedDate(rs.getString("created_date"));
				user.setStatus(rs.getString("status"));
				return user;
			}
		});
	}

	private Long count(UserFilter filter){
		String sql = "SELECT COUNT(id) "
					+ "FROM users "
					+ "WHERE status = ? "
					+ "AND email LIKE ? ";
		return jdbcTemplate.queryForObject(sql,
				new Object[]{
					filter.getStatus(),
					"%"+filter.getName()+"%"
				},
				Long.class);
	}
	
	@Override
	public User findOne(String status, Long id) throws SQLException{
		String sql = "SELECT id, "
				+ "		 email, "
				+ "		 role, "
				+ "		 created_date, "
				+ "		 status "
				+ "FROM users "
				+ "WHERE status = ? "
				+ "AND id = ?";
	return jdbcTemplate.queryForObject(
			sql, 
			new Object[]{
				status,
				id
			}, new RowMapper<User>(){
				@Override
				public User mapRow(ResultSet rs, int rowNum) throws SQLException {
					User user = new User();
					user.setId(rs.getLong("id"));
					user.setEmail(rs.getString("email"));
					user.setRole(rs.getString("role"));
					user.setCreatedDate(rs.getString("created_date"));
					user.setStatus(rs.getString("status"));
					return user;
				}
			});
	}

	@Override
	public Long save(User user) throws SQLException{
		try{
			Long id = jdbcTemplate.queryForObject("SELECT nextval('generations_id_seq')",Long.class);
			String sql = "INSERT INTO users(id, "
						+ "					email, "
						+ "					password, "
						+ "					role, "
						+ "					created_date, "
						+ "					status) "
						+ "VALUES(? ,? ,? ,? ,TO_CHAR(NOW(),'YYYYMMDDHH24MISS') ,?) ";
			int result = jdbcTemplate.update(
					sql,
					new Object[]{
						id,
						user.getEmail(),
						user.getPassword(),
						user.getRole(),
						user.getStatus()
					});
			if(result > 0)
				return id;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Long update(User user) throws SQLException{
		try{
			String sql = "UPDATE users "
						+ "SET email = ?, "
						+ "	   status = ?, "
						+ "	   role = ? "
						+ "WHERE id = ?";
			int result = jdbcTemplate.update(
					sql,
					new Object[]{
						user.getEmail(),
						user.getStatus(),
						user.getRole(),
						user.getId()
					});
			if(result > 0){
				return user.getId();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean delete(int id) throws SQLException{
		try{
			String sql = "UPDATE users "
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
	public Long updatePassword(String newPassword, Long id) throws SQLException{
		try{
			String sql = "UPDATE users "
						+ "SET password = ? "
						+ "WHERE id = ?;";
			int result = jdbcTemplate.update(
					sql,
					new Object[]{
						newPassword,
						id
					});
			if(result > 0){
				return id;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
