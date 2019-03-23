package gwtAppTest.client.user;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import gwtAppTest.shared.User;

public class AddUserDialog extends DialogBox {
	public AddUserDialog(){
		
		DockPanel dockPanel = new DockPanel();
		setWidget(dockPanel);
		dockPanel.setSize("387px", "286px");
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		dockPanel.add(horizontalPanel, DockPanel.NORTH);
		horizontalPanel.setSize("364px", "25px");
		
		Label lblCreatANew = new Label("Creat a new account");
		horizontalPanel.add(lblCreatANew);
		lblCreatANew.setSize("364px", "38px");
		horizontalPanel.setCellHorizontalAlignment(lblCreatANew, HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel.setCellVerticalAlignment(lblCreatANew, HasVerticalAlignment.ALIGN_MIDDLE);
		
		HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
		dockPanel.add(horizontalPanel_1, DockPanel.SOUTH);
		horizontalPanel_1.setWidth("362px");
		

		
		FlexTable flexTable = new FlexTable();
		dockPanel.add(flexTable, DockPanel.SOUTH);
		flexTable.setSize("364px", "150px");
		
		Label lblUsername = new Label("user name");
		flexTable.setWidget(0, 0, lblUsername);
		
		final TextBox userNameTextBox = new TextBox();
		flexTable.setWidget(0, 1, userNameTextBox);
		
		Label lblPassword = new Label("password");
		flexTable.setWidget(1, 0, lblPassword);
		
		final TextBox passwordTextBox = new TextBox();
		flexTable.setWidget(1, 1, passwordTextBox);
		
		Label lblEmail = new Label("email");
		flexTable.setWidget(2, 0, lblEmail);
		
		final TextBox emailTextBox = new TextBox();
		flexTable.setWidget(2, 1, emailTextBox);
		
		//bottom-�啣�
		Button btnAddUser = new Button("New button");
		btnAddUser.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				String userName = userNameTextBox.getText();
				String password = passwordTextBox.getText();
				String email = emailTextBox.getText();
				User addedUser = new User(userName,password,email);
				/////////////////////////////////////////////////////////
				/*
				List<Folder> tmpFolderList = new ArrayList<Folder>();
				Folder tmpFolder = new Folder(addedUser,userNameTextBox.getText()+"_folder");
				tmpFolderList.add(tmpFolder);
				addedUser.setFolders(tmpFolderList);
				*/
				
				UserServiceAsync userService = (UserServiceAsync)GWT.create(UserService.class); 
					userService.saveUser(addedUser, new AsyncCallback<Boolean>() {
						
						@Override
						public void onSuccess(Boolean result) {
							// TODO Auto-generated method stub
							hide();
							Window.alert(result?"新增成功":"儲存失敗");
						}
						
						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							Window.alert("遠端呼叫失敗");
							
						}
					});
				
				
			}
		});
		btnAddUser.setText("insert");
		horizontalPanel_1.add(btnAddUser);
		horizontalPanel_1.setCellHorizontalAlignment(btnAddUser, HasHorizontalAlignment.ALIGN_RIGHT);
		
		//bottom
		Button btnCancel = new Button("New button");
		btnCancel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		btnCancel.setText("cancel");
		horizontalPanel_1.add(btnCancel);
		
	}
}
