package gwtAppTest.client.user;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import gwtAppTest.shared.User;


@RemoteServiceRelativePath("userServ")
public interface UserService extends RemoteService {
	
	
	public boolean saveUser(User user);  

	public boolean updateUser(User user);
	
	List<User> getAllUsers();
	List<User> getUsers();
	
	public boolean deleteUser(User user);
	
	User login(String loginId, String password);
	
}
