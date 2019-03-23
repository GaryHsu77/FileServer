package gwtAppTest.server;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gwtAppTest.client.upload.BlobService;
import gwtAppTest.shared.TmpFile;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;


@SuppressWarnings("serial")
public class BlobServiceImpl extends RemoteServiceServlet implements
    BlobService {

  //Start a GAE BlobstoreService session and Objectify session
  BlobstoreService blobstoreService = BlobstoreServiceFactory
      .getBlobstoreService();
  PersistenceManager pm =
			PMF.get().getPersistenceManager();
  //Register the Objectify Service for the Picture entity


  //Generate a Blobstore Upload URL from the GAE BlobstoreService
  @Override
  public String getBlobStoreUploadUrl() {

    //Map the UploadURL to the uploadservice which will be called by
    //submitting the FormPanel
    return blobstoreService
        .createUploadUrl("/gwtapptest/uploadservice");
  }

  //Retrieve the Blob's meta-data from the Datastore using Objectify
  @Override
  public TmpFile getPicture(String id) {
		long l = Long.parseLong(id);
		TmpFile picture = (TmpFile)pm.getObjectById(TmpFile.class,l);
		return picture;
	}
	

  //Override doGet to serve blobs.  This will be called automatically by the Image Widget
  //in the client
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

        BlobKey blobKey = new BlobKey(req.getParameter("blob-key"));
        blobstoreService.serve(blobKey, resp);

  }
}