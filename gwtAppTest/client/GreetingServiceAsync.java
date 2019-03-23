package gwtAppTest.client;



import gwtAppTest.shared.User;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void greetServer(String input, AsyncCallback<String> callback) throws IllegalArgumentException;

	void addNote(String note, String pw, AsyncCallback<Void> callback);

	void getNotes(AsyncCallback<String[]> callback);

	void removeNote(String note, AsyncCallback<Void> callback);

	void getUser(String name, AsyncCallback<User> callback);
}
