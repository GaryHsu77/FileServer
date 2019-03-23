package gwtAppTest.client.file;

import gwtAppTest.shared.File;
import gwtAppTest.shared.Folder;
import gwtAppTest.shared.TmpFile;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


@RemoteServiceRelativePath("fileServ")
public interface FileService extends RemoteService {

	
	public boolean addFolder(Folder folder);
	public boolean deleteFolder(Folder folder);
	List<Folder> getFoldersByUserId(Long parantUserId);
	List<Folder> getFoldersByFolderId(Long parantFolderId);
	List<Folder> getFoldersByShareId(String shareName);
	public boolean addFile(File file);
	public boolean deleteFile(File file);
	List<File> getFilesByUserId(Long parantUserId);      //
	List<File> getFilesByFolderId(Long parantFolderId);  //
	List<File> getFilesByShareId(String shareName);
	TmpFile getTmpFile();
}
