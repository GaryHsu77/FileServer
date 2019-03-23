package gwtAppTest.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import gwtAppTest.client.user.UserService;
import gwtAppTest.client.user.UserServiceAsync;
import gwtAppTest.shared.User;

public class LoginDialog extends DialogBox {
	
	DockPanel dockPanel;
	public LoginDialog() {
		
		dockPanel = new DockPanel();
		setWidget(dockPanel);
		dockPanel.setSize("183px", "151px");
		
		//DockPanel dockPanel = new DockPanel();
		
		FlexTable flexTable = new FlexTable();
		dockPanel.add(flexTable, DockPanel.CENTER);
		flexTable.setSize("378px", "82px");
		
		Label lblUserName = new Label("user name");
		flexTable.setWidget(0, 0, lblUserName);
		
		final TextBox usernameTextBox = new TextBox();
		flexTable.setWidget(0, 1, usernameTextBox);
		usernameTextBox.setWidth("141px");
		
		Label lblPassword = new Label("password");
		flexTable.setWidget(1, 0, lblPassword);
		
		final TextBox passwordTextBox = new TextBox();
		flexTable.setWidget(1, 1, passwordTextBox);
		passwordTextBox.setWidth("181px");
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		dockPanel.add(horizontalPanel, DockPanel.SOUTH);
		horizontalPanel.setSize("376px", "30px");
		
		Button btnLogin = new Button("login");
		btnLogin.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				UserServiceAsync userService = (UserServiceAsync)GWT.create(UserService.class); 
				userService.login(usernameTextBox.getText(),
						passwordTextBox.getText(),
						new AsyncCallback<User>() {
					
							@Override
							public void onSuccess(User loginUser) {
								// TODO Auto-generated method stub
								
								if (loginUser!=null) {
									Window.alert("Login success!");
									hide();
									MainList userList = new MainList(loginUser);
									//userList.setPopupPosition(200, 200);
									userList.center();
									//dockPanel.clear();
									
								} else {
									Window.alert("Login Fail ID or Password Error!");
								}
							}
							
							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub
								Window.alert("login fail");
							}
						});
			}
		});
		horizontalPanel.add(btnLogin);
		horizontalPanel.setCellHorizontalAlignment(btnLogin, HasHorizontalAlignment.ALIGN_RIGHT);
		
		Button btnCancel = new Button("cancel");
		horizontalPanel.add(btnCancel);
		//UserList userList = new UserList();

	
}

	
	
}
