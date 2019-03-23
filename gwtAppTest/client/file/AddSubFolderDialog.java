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
import gwtAppTest.shared.Folder;

public class AddSubFolderDialog extends DialogBox {
	
	FileServiceAsync fileService = (FileServiceAsync)GWT.create(FileService.class); 
	UserServiceAsync userService = (UserServiceAsync)GWT.create(UserService.class);
	final Folder selectedFolder2;

	private final TextBox folderNameTextBox;
	public AddSubFolderDialog(Folder selectedFolder) {
		
		selectedFolder2=selectedFolder;
		
		VerticalPanel verticalPanel = new VerticalPanel();
		setWidget(verticalPanel);
		verticalPanel.setSize("361px", "331px");
		
		Label lblCreatAFolder = new Label("creat a sub folder");
		verticalPanel.add(lblCreatAFolder);
		verticalPanel.setCellVerticalAlignment(lblCreatAFolder, HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel.setCellHorizontalAlignment(lblCreatAFolder, HasHorizontalAlignment.ALIGN_CENTER);
		lblCreatAFolder.setHeight("27px");
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		verticalPanel.add(horizontalPanel);
		horizontalPanel.setSize("186px", "37px");
		
		Label lblFolderName = new Label("input the subfolder name:");
		horizontalPanel.add(lblFolderName);
		horizontalPanel.setCellVerticalAlignment(lblFolderName, HasVerticalAlignment.ALIGN_MIDDLE);
		lblFolderName.setSize("143px", "32px");
		
		folderNameTextBox = new TextBox();
		horizontalPanel.add(folderNameTextBox);
		folderNameTextBox.setSize("208px", "25px");
		
		HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
		horizontalPanel_1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.add(horizontalPanel_1);
		horizontalPanel_1.setSize("361px", "31px");
		
		Button btnComfirm = new Button("comfirm");
		btnComfirm.addClickHandler(new ClickHandler() {                 //修改user物件 讓他新增一個folder
			public void onClick(ClickEvent event) 
			{	String folderName = folderNameTextBox.getText();
				
				Folder addedFolder = new Folder(folderName,"no",null,selectedFolder2.getId());
				fileService.addFolder(addedFolder, new AsyncCallback<Boolean>() {
					
					@Override
					public void onSuccess(Boolean result) {
						// TODO Auto-generated method stub
						hide();
						Window.alert(result?"新增資料夾成功":"儲存資料夾失敗");
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

