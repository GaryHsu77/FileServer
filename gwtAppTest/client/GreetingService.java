package gwtAppTest.client;



import gwtAppTest.shared.User;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	String greetServer(String name) throws IllegalArgumentException;

	public void addNote(String note,String pw);

	public void removeNote(String note);

	public String[] getNotes();
	public User getUser(String name);
}
