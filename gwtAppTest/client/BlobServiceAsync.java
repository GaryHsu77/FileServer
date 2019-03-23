package gwtAppTest.client;

import gwtAppTest.client.entities.Picture;
import gwtAppTest.shared.TmpFile;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface BlobServiceAsync {

	void getBlobStoreUploadUrl(AsyncCallback<String> callback);

	void getPicture(String id, AsyncCallback<TmpFile> callback);

}
