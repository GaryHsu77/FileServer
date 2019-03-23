package gwtAppTest.client.user;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import gwtAppTest.shared.User;



public interface UserServiceAsync {

	void saveUser(User user, AsyncCallback<Boolean> callback);

	void updateUser(User user, AsyncCallback<Boolean> callback);

	void getAllUsers(AsyncCallback<List<User>> callback);

	void login(String loginId, String password, AsyncCallback<User> callback);

	void getUsers(AsyncCallback<List<User>> callback);

	void deleteUser(User user, AsyncCallback<Boolean> callback);

}
