package gwtAppTest.server;

import java.io.IOException;
import java.util.Map;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gwtAppTest.shared.File;
import gwtAppTest.shared.TmpFile;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;


//The FormPanel must submit to a servlet that extends HttpServlet  
//RemoteServiceServlet cannot be used
@SuppressWarnings("serial")
public class UploadServiceImpl extends HttpServlet {

  //Start Blobstore and Objectify Sessions
  BlobstoreService blobstoreService = BlobstoreServiceFactory
      .getBlobstoreService();
  PersistenceManager pm =
			PMF.get().getPersistenceManager();



  //Override the doPost method to store the Blob's meta-data
  public void doPost(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {

    Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(req);
    BlobKey blobKey = blobs.get("upload");
    /*
    //Get the paramters from the request to populate the Picture object
    TmpFile picture = new TmpFile();
   // picture.setDescription(req.getParameter("descriptionTextBox"));
    picture.setDescription(req.getParameter("hiddenPUserID"));
    picture.setTitle(req.getParameter("titleTextBox"));
    //Map the ImageURL to the blobservice servlet, which will serve the image
    picture.setImageUrl("/stevensgwtproject/blobservice?blob-key=" + blobKey.getKeyString());
	
    pm.makePersistent(picture);
    */
    File file = new File();
    file.setFileName(req.getParameter("titleTextBox"));
    file.setFileURL("/gwtapptest/blobservice?blob-key=" + blobKey.getKeyString());
    file.setParentUserId(Long.parseLong(req.getParameter("hiddenPUserID")));
    pm.makePersistent(file);
    
    //Redirect recursively to this servlet (calls doGet)
   // res.sendRedirect("/stevensgwtproject/uploadservice?id=" + picture.id);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    //Send the meta-data id back to the client in the HttpServletResponse response
    String id = req.getParameter("id");
    resp.setHeader("Content-Type", "text/html");
    resp.getWriter().println(id);

  }

}
