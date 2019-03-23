package gwtAppTest.server;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import gwtAppTest.client.file.FileService;
import gwtAppTest.shared.File;
import gwtAppTest.shared.Folder;
import gwtAppTest.shared.TmpFile;



public class FileServiceImpl extends RemoteServiceServlet implements
		FileService {
	@Override
	public boolean addFolder(Folder folder) {
		boolean saved = false;
		try{
			PersistenceManager pm =
					PMF.get().getPersistenceManager();
			pm.makePersistent(folder);
			saved = true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return saved;
	}

	@Override
	public boolean deleteFolder(Folder folder) {
		boolean deleted = false;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Folder p = (Folder)pm.getObjectById(Folder.class, folder.getId());
			pm.deletePersistent(p);
		
			deleted = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}
		return deleted;
	}
	

	@Override
	public List<Folder> getFoldersByUserId(Long parantUserId) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try{
			try {
					 Query query = pm.newQuery(Folder.class);
					 query.setFilter("parentUserId == myParentUserId");
					 query.declareParameters("String myParentUserId");
					 @SuppressWarnings("unchecked")
					 List<Folder> results = (List<Folder>)query.execute(parantUserId);
					 List<Folder> folders = new ArrayList<Folder>();
					 for(Folder p:results ){
						p.getFolderName();
						folders.add(p);
					 }
					 return folders;
				
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}
			finally{
				pm.close();
			}
			//turn null;
	}
	
	@Override
	public List<Folder> getFoldersByFolderId(Long parantFolderId) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try{
			try {
					 Query query = pm.newQuery(Folder.class);
					 query.setFilter("parentFolderId == myParentFolderId");
					 query.declareParameters("String myParentFolderId");
					 @SuppressWarnings("unchecked")
					 List<Folder> results = (List<Folder>)query.execute(parantFolderId);
					 List<Folder> folders = new ArrayList<Folder>();
					 for(Folder p:results ){
						p.getFolderName();
						folders.add(p);
					 }
					 return folders;
				
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}
			finally{
				pm.close();
			}
			//turn null;
	}
	
	
	@Override
	public boolean addFile(File file) {
		boolean saved = false;
		try{
			PersistenceManager pm =
					PMF.get().getPersistenceManager();
			pm.makePersistent(file);
			saved = true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return saved;
	}

	@Override
	public boolean deleteFile(File file) {
		boolean deleted = false;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			File p = (File)pm.getObjectById(File.class, file.getId());
			pm.deletePersistent(p);
			deleted = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}
		return deleted;
	}

	@Override
	public List<File> getFilesByUserId(Long parantUserId) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try{
			try {
					 Query query = pm.newQuery(File.class);
					 query.setFilter("parentUserId == myParentUserId");
					 query.declareParameters("String myParentUserId");
					 
					 @SuppressWarnings("unchecked")
					 List<File> results = (List<File>)query.execute(parantUserId);
					 List<File> files = new ArrayList<File>();
					 for(File p:results ){
						p.getFileName();
						files.add(p);
					 }
					 return files;
				
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}
			finally{
				pm.close();
			}
			//turn null;
	}
	
	@Override
	public List<File> getFilesByFolderId(Long parentFolderId) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try{
			try {
					 Query query = pm.newQuery(File.class);
					 query.setFilter("parentFolderId == myParentFolderId");
					 query.declareParameters("String myParentFolderId");
					 @SuppressWarnings("unchecked")
					 List<File> results = (List<File>)query.execute(parentFolderId);
					 List<File> files = new ArrayList<File>();
					 for(File p:results ){
						p.getFileName();
						files.add(p);
					 }
					 return files;
				
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}
			finally{
				pm.close();
			}
			//turn null;
	}

	@Override
	public TmpFile getTmpFile() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try{
			try {
				Query query = pm.newQuery(TmpFile.class);
				 List<TmpFile> results = (List)query.execute();
				// query.setResult("max(this.id)");
				 TmpFile theFile=results.get(results.size()-1);
				 return theFile;
				 
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		finally{
			pm.close();
		}
	}

	@Override
	public List<Folder> getFoldersByShareId(String shareName) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try{
			try {
					 Query query = pm.newQuery(Folder.class);
					 @SuppressWarnings("unchecked")
					 List<Folder> results = (List<Folder>)query.execute();
					 List<Folder> folders = new ArrayList<Folder>();
					 for(Folder p:results ){
							 p.getFolderName();
						     folders.add(p);
					 }
					 return folders;
				
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}
			finally{
				pm.close();
			}
			//turn null;
	}

	@Override
	public List<File> getFilesByShareId(String shareName) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try{
			try {
					 Query query = pm.newQuery(File.class);
					 @SuppressWarnings("unchecked")
					 List<File> results = (List<File>)query.execute();
					 List<File> files = new ArrayList<File>();
					 for(File p:results ){
							 p.getFileName();
							 files.add(p);
					 }
					 return files;
				
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}
			finally{
				pm.close();
			}
			//turn null;
	}



}
