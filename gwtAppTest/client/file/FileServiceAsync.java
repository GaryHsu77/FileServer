package gwtAppTest.client.file;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import gwtAppTest.shared.File;
import gwtAppTest.shared.Folder;
import gwtAppTest.shared.TmpFile;



public interface FileServiceAsync {

	void addFolder(Folder folder, AsyncCallback<Boolean> callback);

	void deleteFile(File file, AsyncCallback<Boolean> callback);

	void deleteFolder(Folder folder, AsyncCallback<Boolean> callback);

	void addFile(File file, AsyncCallback<Boolean> callback);

	void getFoldersByUserId(Long parantUserId,
			AsyncCallback<List<Folder>> callback);

	void getFoldersByFolderId(Long parantFolderId,
			AsyncCallback<List<Folder>> callback);

	void getFilesByUserId(Long parantUserId, AsyncCallback<List<File>> callback);

	void getFilesByFolderId(Long parantFolderId,
			AsyncCallback<List<File>> callback);

	void getTmpFile(AsyncCallback<TmpFile> callback);

	void getFoldersByShareId(String shareName, AsyncCallback<List<Folder>> callback);

	void getFilesByShareId(String shareName, AsyncCallback<List<File>> callback);

	
	
}
