package tai.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import tai.domain.User;
import tai.jdbc.UserRowMapper;

public class UserDaoImpl implements UserDao{

	@Autowired
	DataSource dataSource;
	
	public void insertData(User user) {

		String sql = "INSERT INTO User "
				+ "(username, password, enabled, role) VALUES (?,?,?,?)";

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(
				sql,
				new Object[] { user.getUsername(), user.getPassword(),
						user.getEnabled(), user.getRole()});

	}
	
	public void deleteData(String username) {

		String sql = "delete from User where username = \"" + username + "\";";

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(sql);
	}
	
	public boolean exists(String username){
		List<User> userList;
		String sql = "select u.username, u.password, u.enabled, u.role "
				+ "from User as u "
				+ "where u.username = \"" + username + "\";";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		userList = jdbcTemplate.query(sql, new UserRowMapper());
		if(userList.isEmpty()) return false;
		return true;
	}

	@Override
	public User getUser(String username) {
		List<User> userList;
		String sql = "select u.username, u.password, u.enabled, u.role "
				+ "from User as u "
				+ "where u.username = \"" + username + "\";";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		userList = jdbcTemplate.query(sql, new UserRowMapper());
		
		if(userList.size()>=1)
			return userList.get(0);
		else return null;
	}
}
