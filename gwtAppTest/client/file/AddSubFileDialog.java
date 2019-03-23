package gwtAppTest.client.file;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import gwtAppTest.client.user.UserService;
import gwtAppTest.client.user.UserServiceAsync;
import gwtAppTest.shared.File;
import gwtAppTest.shared.Folder;

public class AddSubFileDialog extends DialogBox{
	FileServiceAsync fileService = (FileServiceAsync)GWT.create(FileService.class); 
	UserServiceAsync userService = (UserServiceAsync)GWT.create(UserService.class);
	final Folder selectedFolder2;

	private final TextBox fileNameTextBox;
	public AddSubFileDialog(Folder selectedFolder) { //傳進取得的folder 來知道folder id要設成多少
		
		selectedFolder2=selectedFolder;
		
		VerticalPanel verticalPanel = new VerticalPanel();
		setWidget(verticalPanel);
		verticalPanel.setSize("361px", "331px");
		
		Label lblCreatAFolder = new Label("creat a file in subfolder");
		verticalPanel.add(lblCreatAFolder);
		verticalPanel.setCellVerticalAlignment(lblCreatAFolder, HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel.setCellHorizontalAlignment(lblCreatAFolder, HasHorizontalAlignment.ALIGN_CENTER);
		lblCreatAFolder.setHeight("27px");
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		verticalPanel.add(horizontalPanel);
		horizontalPanel.setSize("186px", "37px");
		
		Label lblFolderName = new Label("input the file name:");
		horizontalPanel.add(lblFolderName);
		horizontalPanel.setCellVerticalAlignment(lblFolderName, HasVerticalAlignment.ALIGN_MIDDLE);
		lblFolderName.setSize("143px", "32px");
		
		fileNameTextBox = new TextBox();
		horizontalPanel.add(fileNameTextBox);
		fileNameTextBox.setSize("208px", "25px");
		
		HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
		horizontalPanel_1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.add(horizontalPanel_1);
		horizontalPanel_1.setSize("361px", "31px");
		
		Button btnComfirm = new Button("comfirm");
		btnComfirm.addClickHandler(new ClickHandler() {                 
			public void onClick(ClickEvent event) 
			{	String fileName = fileNameTextBox.getText();
				
				File addedFile = new File(fileName,null
												,null
												,selectedFolder2.getId());
				fileService.addFile(addedFile, new AsyncCallback<Boolean>() {
					
					@Override
					public void onSuccess(Boolean result) {
						// TODO Auto-generated method stub
						hide();
						Window.alert(result?"新增檔案成功":"儲存檔案失敗");
					}
					
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						Window.alert("遠端呼叫失敗");
					}
				});
				
			}
		});
		horizontalPanel_1.add(btnComfirm);
		horizontalPanel_1.setCellHorizontalAlignment(btnComfirm, HasHorizontalAlignment.ALIGN_CENTER);
		
		Button btnCancel = new Button("cancel");
		btnCancel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		horizontalPanel_1.add(btnCancel);
		horizontalPanel_1.setCellHorizontalAlignment(btnCancel, HasHorizontalAlignment.ALIGN_CENTER);
	}
}
