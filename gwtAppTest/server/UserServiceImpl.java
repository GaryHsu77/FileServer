package gwtAppTest.server;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;


import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import gwtAppTest.client.user.UserService;
import gwtAppTest.shared.User;



@RemoteServiceRelativePath("userServ")
public class UserServiceImpl extends RemoteServiceServlet implements
		UserService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
	public boolean saveUser(User user) {
		boolean saved = false;
		try{

			
			PersistenceManager pm =
					PMF.get().getPersistenceManager();
			pm.makePersistent(user);
			saved = true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return saved;
	}

	@Override
	public boolean updateUser(User user) {
		boolean updated = false;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		User p = (User)pm.getObjectById(User.class, user.getId());
		try {
			p.setUserName(user.getUserName());
			p.setPassword(user.getPassword());
			p.setEmail(user.getEmail());
			//p.setFolders(user.getFolders());			
			updated = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}
		return updated;
	}
	//not used
	@Override
	public List<User> getAllUsers() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String query = "select from "+User.class.getName();            	
		List<User> result = (List<User>) pm.newQuery(query).execute();	
		List<User> users = new ArrayList<User>();
		for(User p:result ){
			p.getUserName();
			users.add(p);
		}
		return users;
	}

	
	@Override
	public boolean deleteUser(User user) {
		
		boolean deleted = false;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			User p = (User)pm.getObjectById(User.class, user.getId());
			pm.deletePersistent(p);
			deleted = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}
		return deleted;
		
		
	}
	/*
	@Override
	public boolean daleteUser(User user) {
		boolean deleted = false;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Query query = pm.newQuery(User.class);
			query.setFilter("loginId == idParam");
			query.declareParameters("String idParam");
			query.deletePersistentAll(user.getUserName());
			deleted = true;
		} finally {
			pm.close();
		}
		System.out.println("Delete user " + user.getUserName() + " success!!");
		return deleted;
	}*/

	@Override
	public User login(String username, String password) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try{
			try {
				 Query query = pm.newQuery(User.class);
				 query.setFilter("userName == userNameParam");
				 query.declareParameters("String userNameParam");
				 @SuppressWarnings("unchecked")
				List<User> tmpUser = (List<User>)query.execute(username);
				 //User tmpUser = pm.getObjectById(User.class, username);
				 User loginUser = tmpUser.get(0);
				 if(loginUser.getPassword().equals(password)){
					//HttpSession session = this.getThreadLocalRequest().getSession();
					//session.setAttribute("loginId", username);
					return loginUser;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		finally{
			pm.close();
		}
		return null;
	}
/*
	@Override
	public String[] getUsers() {                                      
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String query = "select from "+User.class.getName();            	
		List<User> results = (List<User>) pm.newQuery(query).execute();		
		User e;		
		String[] rtn = new String[results.size()];
		for (int i=0;i<results.size();i++) {
        	User user = results.get(i);
        	e = pm.getObjectById(User.class, user.getId());    //use key to get user   
        	System.out.println("getname:"+e);
        	rtn[i] = e.getUserName()+","+e.getPassword()+","+e.getEmail();
        }
		return rtn;
	}
*/
	@Override
	public List<User> getUsers() {                                      
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String query = "select from "+User.class.getName();            	
		List<User> results = (List<User>) pm.newQuery(query).execute();		

		return results;
	}
	
}
