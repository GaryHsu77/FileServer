package gwtAppTest.client.upload;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.FlexTable;
import gwtAppTest.client.file.FileService;
import gwtAppTest.client.file.FileServiceAsync;
import gwtAppTest.shared.File;
import gwtAppTest.shared.TmpFile;
import gwtAppTest.shared.User;
import com.sun.java.swing.plaf.windows.resources.windows;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;

public class UploadDialog2 extends DialogBox {
	
	  
	  TmpFile lastUploadFile ;
	  String stringTitle;
	  final FormPanel uploadForm = new FormPanel();
	  
	  // Use an RPC call to the Blob Service to get the blobstore upload url
	  BlobServiceAsync blobService = GWT.create(BlobService.class);
	  FileServiceAsync fileService=GWT.create(FileService.class);
	  
	  VerticalPanel mainVerticalPanel = new VerticalPanel();
	  HorizontalPanel hp1 = new HorizontalPanel();
	  HorizontalPanel hp2 = new HorizontalPanel();
	  HTML titleLabel = new HTML("File Name");
	  HTML descriptionLabel = new HTML("Description");
	  TextBox titleTextBox = new TextBox();
	  TextBox descriptionTextBox = new TextBox();
	  FileUpload upload = new FileUpload();
	  Button submitButton = new Button("Submit");
	  private final Button btnCancel = new Button("Cancel");
	  private final HorizontalPanel horizontalPanel = new HorizontalPanel();
	  private final Hidden hiddenPUserID = new Hidden("hiddenPUserID");
	  
	public UploadDialog2(final User loginUser) {
		uploadForm.addSubmitCompleteHandler(new SubmitCompleteHandler() {
			public void onSubmitComplete(SubmitCompleteEvent event) {
			
				// getPicture(event.getResults().trim());
				
			}
		});
		
		//uploadForm = new FormPanel();
		setWidget(uploadForm);
		uploadForm.setSize("322px", "204px");
		
		mainVerticalPanel = new VerticalPanel();
		mainVerticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		mainVerticalPanel.setStyleName("null");
		uploadForm.setWidget(mainVerticalPanel);
		mainVerticalPanel.setSize("280px", "204px");
		
		hp1 = new HorizontalPanel();
		mainVerticalPanel.add(hp1);
		hp1.setSize("220px", "45px");
		titleLabel.setStyleName("fileTable");
		
		
		hp1.add(titleLabel);
		hp1.setCellVerticalAlignment(titleLabel, HasVerticalAlignment.ALIGN_MIDDLE);
		titleLabel.setSize("96px", "44px");
		
		
		hp1.add(titleTextBox);
		titleTextBox.setSize("215px", "19px");
		//////////////////////////////////////////////////
		
		
		hp2 = new HorizontalPanel();
		mainVerticalPanel.add(hp2);
		hp2.setSize("277px", "42px");
		descriptionLabel.setStyleName("fileTable");
		
		hp2.add(descriptionLabel);
		descriptionLabel.setSize("96px", "31px");
		
		
		hp2.add(descriptionTextBox);
		descriptionTextBox.setSize("215px", "19px");
		
		upload = new FileUpload();
		upload.setStyleName("sdTextBox");
	
		
		mainVerticalPanel.add(upload);
		upload.setWidth("319px");
		
		/////////////////////////////////////////////////////
	    // The upload form, when submitted, will trigger an HTTP call to the
	    // servlet.  The following parameters must be set
	    uploadForm.setEncoding(FormPanel.ENCODING_MULTIPART);
	    uploadForm.setMethod(FormPanel.METHOD_POST);

	    // Set Names for the text boxes so that they can be retrieved from the
	    // HTTP call as parameters
	    titleTextBox.setName("titleTextBox");
	    descriptionTextBox.setName("descriptionTextBox");
	    upload.setName("upload");
	    
	    mainVerticalPanel.add(horizontalPanel);
	    horizontalPanel.setSize("272px", "37px");
	    
	    submitButton = new Button("submit");
	    submitButton.setStyleName("sdTextBox");
	    horizontalPanel.add(submitButton);
	    horizontalPanel.setCellHorizontalAlignment(submitButton, HasHorizontalAlignment.ALIGN_CENTER);
	    submitButton.setSize("122px", "44px");
	    submitButton.addClickHandler(new ClickHandler() {
	    	public void onClick(ClickEvent event) {
	    		
	    	//	stringTitle = titleTextBox.getText();
	    		
	    		upload.getFilename();
				String[] filePath;
				String file;
				int fileLocation;
				file = upload.getFilename();
				System.out.println("\\\\");
				filePath=file.split("\\\\");
				//fileLocation = filePath.length;
	    		titleTextBox.setText(filePath[2]);
	    		blobService
	            .getBlobStoreUploadUrl(new AsyncCallback<String>() {
	    			
	    			@Override
	    			public void onSuccess(String result) {
	                    // Set the form action to the newly created
	                    // blobstore upload URL
	                    uploadForm.setAction(result.toString());

	                    // Submit the form to complete the upload
	                    uploadForm.submit();
	                    uploadForm.reset();
	                   // Window.alert("Upload success");
	                   // hide();
	    			}
	    			
	    			@Override
	    			public void onFailure(Throwable caught) {
	    				// TODO Auto-generated method stub
	    				caught.printStackTrace();
	    			}
	    		});
	    		hide();
	    		/*
	    		Timer timer = new Timer(){
	    			public void run(){
	    				getTmpFile();
	    			}
	    		};
	    		timer.schedule(10000);
				*/
	    	}	
	    });
	    
	    
	    submitButton.setText("Submit");
	    btnCancel.setStyleName("sdTextBox");
	    horizontalPanel.add(btnCancel);
	    horizontalPanel.setCellHorizontalAlignment(btnCancel, HasHorizontalAlignment.ALIGN_CENTER);
	    btnCancel.setSize("126px", "43px");
	    ///hidden///
	    ///////////////////////////////////////////////////////////////////////////////
	    mainVerticalPanel.add(hiddenPUserID);
	    //loginUser.getId()
	   
	   hiddenPUserID.setValue(Long.toString(loginUser.getId())); //submit PUserID
	   ///////////////////////////////////////////////////////////////////////////////////
	    btnCancel.addClickHandler(new ClickHandler() {
	    	public void onClick(ClickEvent event) {
	    		hide();
	    	}
	    });
		

	    
	}
	
	private void getTmpFile(){
	    fileService.getTmpFile(new AsyncCallback<TmpFile>() {
			
			@Override
			public void onSuccess(TmpFile result) {
				// TODO Auto-generated method stub
				lastUploadFile =result;
				
				Window.alert("upload file name="+lastUploadFile.getTitle());
		        /*
		        Image image = new Image();
		        image.setUrl(lastUploadFile.getImageUrl());
		        
		        //Use Getters from the Picture object to load the FlexTable
		        
		        
		        resultTable.setWidget(0, 0, image);
		        resultTable.setText(1, 0, lastUploadFile.getTitle());
		        resultTable.setText(2, 0, lastUploadFile.getDescription());
				*/
		        ShowPic showPic = new ShowPic(lastUploadFile);
		        showPic.center();
				
				/*
				if (lastUploadFile.getTitle() == stringTitle){
					Window.alert("upload success");
				}
				else{
					Window.alert("upload failed");
				}
				*/
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});

	}
	


}