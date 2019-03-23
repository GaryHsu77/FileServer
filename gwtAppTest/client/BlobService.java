package gwtAppTest.client;

import gwtAppTest.client.entities.Picture;
import gwtAppTest.shared.TmpFile;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("blobservice")
public interface BlobService extends RemoteService {

  String getBlobStoreUploadUrl();

  TmpFile getPicture(String id);

}