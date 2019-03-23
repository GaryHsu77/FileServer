package gwtAppTest.server;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import gwtAppTest.client.GreetingService;
import gwtAppTest.shared.FieldVerifier;
import gwtAppTest.shared.User;

import com.google.apphosting.api.ApiProxy.LogRecord.Level;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements GreetingService {

	private static final PersistenceManagerFactory PMF = JDOHelper.getPersistenceManagerFactory("transactions-optional");
	private static final Logger LOG = Logger.getLogger(GreetingServiceImpl.class.getName());

	public String greetServer(String input) throws IllegalArgumentException {
		// Verify that the input is valid.
		if (!FieldVerifier.isValidName(input)) {
			// If the input is not valid, throw an IllegalArgumentException back
			// to
			// the client.
			throw new IllegalArgumentException("Name must be at least 4 characters long");
		}

		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script
		// vulnerabilities.
		input = escapeHtml(input);
		userAgent = escapeHtml(userAgent);

		return "Hello, " + input + "!<br><br>I am running " + serverInfo + ".<br><br>It looks like you are using:<br>" + userAgent;
	}

	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}

	public void addNote(String name, String pw,String email) {
		PersistenceManager pm = getPersistenceManager();
		try {
			pm.makePersistent(new User(name, pw,email));
		} finally {
			pm.close();
		}
	}

	private PersistenceManager getPersistenceManager() {
		return PMF.getPersistenceManager();
	}

	public void removeNote(String note) {
		PersistenceManager pm = getPersistenceManager();
		try {
			long deleteCount = 0;
			String query = "select from " + User.class.getName();
			List<User> Notes = (List<User>) pm.newQuery(query).execute();
			for (User Note : Notes) {
				if (note.equals(Note.getUserName())) {
					deleteCount++;
					pm.deletePersistent(Note);
				}
			}
			if (deleteCount != 1) {
				// LOG.log(Level.WARNING, "removeNote deleted " + deleteCount
				// + " Notes");
			}
		} finally {
			pm.close();
		}
	}

	public String[] getNotes() {

		PersistenceManager pm = getPersistenceManager();
		List<String> symbols = new ArrayList<String>();

		try {
			String query = "select from " + User.class.getName();
			List<User> Notes = (List<User>) pm.newQuery(query).execute();

			for (User Note : Notes) {
				symbols.add(Note.getUserName() + " " + Note.getPassword());
			}
		} finally {
			pm.close();
		}

		String[] ret = new String[symbols.size()];

		int i = 0;
		for (String s : symbols) {
			ret[i] = s;
			i++;
		}

		return ret;
	}

	@Override
	public void addNote(String note, String pw) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User getUser(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
