package tai.dao;

import tai.domain.User;

public interface UserDao {

	public void insertData(User user);
	
	public User getUser(String username);
	
	public void deleteData(String username);
	
	public boolean exists(String username);
}
