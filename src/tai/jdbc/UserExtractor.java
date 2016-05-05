package tai.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import tai.domain.User;

public class UserExtractor implements ResultSetExtractor<User>{

	@Override
	public User extractData(ResultSet resultSet) throws SQLException,
			DataAccessException {

		User user = new User();
		user.setUsername(resultSet.getString(1));
		user.setPassword(resultSet.getString(2));
		user.setEnabled(resultSet.getShort(3));
		user.setRole(resultSet.getString(4));
		return user;
	}

}
