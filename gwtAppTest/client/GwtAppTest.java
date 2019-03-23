package gwtAppTest.client;

import java.util.ArrayList;

import gwtAppTest.client.user.AddUserDialog;
import gwtAppTest.client.user.UserService;
import gwtAppTest.client.user.UserServiceAsync;
import gwtAppTest.shared.FieldVerifier;
import gwtAppTest.shared.User;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.Window;
import com.google.gwt.event.dom.client.ClickHandler;
/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GwtAppTest implements EntryPoint {

	private static final String SERVER_ERROR = "An error occurred while " + "attempting to contact the server. Please check your network " + "connection and try again.";
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
	UserServiceAsync userService = (UserServiceAsync)GWT.create(UserService.class);
	final Button sendButton = new Button("Loggin");
	final TextBox nameField = new TextBox();
	final PasswordTextBox passwordField = new PasswordTextBox();
	final Label errorLabel = new Label();
	// private ArrayList<String> NotesNames = new ArrayList<String>();
	private Label lblGwtPlaySample;

	private DialogBox dialogBox;

	private Button closeButton;

	public void onModuleLoad() {
		AbsolutePanel absolutePanel = new AbsolutePanel();
		absolutePanel.setStyleName("null");
		absolutePanel.setSize("744px", "530px");
		lblGwtPlaySample = new Label("Kangroo Tech.");
		absolutePanel.add(lblGwtPlaySample);
		lblGwtPlaySample.setStyleName("lblGwtplaysample");
		lblGwtPlaySample.setSize("734px", "46px");
		absolutePanel.add(nameField, 554, 353);
		nameField.setSize("149px", "18px");
		nameField.setText("UserName");
		nameField.setFocus(true);
		absolutePanel.add(passwordField, 555, 422);
		passwordField.setSize("148px", "16px");
		passwordField.setText("Password");
		passwordField.setFocus(true);
		absolutePanel.add(sendButton, 645, 456);
		sendButton.setSize("68px", "30px");
		sendButton.addStyleName("sendButton");
		absolutePanel.add(errorLabel, 517, 57);
		errorLabel.setStyleName("h1");
		errorLabel.setSize("196px", "242px");
		Image image = new Image("http://upload.wikimedia.org/wikipedia/zh/b/b3/Ttumark.jpg");
		absolutePanel.add(image, 10, 77);
		image.setSize("501px", "431px");
		RootPanel.get("Container").add(absolutePanel);
		nameField.setFocus(true);
		passwordField.setFocus(true);

		Button regButton = new Button("Register");
		regButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				//UserServiceAsync userService = (UserServiceAsync)GWT.create(UserService.class); 
				userService.login(nameField.getText(),
						passwordField.getText(),
						new AsyncCallback<User>() {
					
							@Override
							public void onSuccess(User loginUser) {
								// TODO Auto-generated method stub
								
								if (loginUser!=null) {
									Window.alert("Login success!");
									//hide();
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
		absolutePanel.add(regButton, 554, 456);
		
		Label lblName = new Label("NAME");
		lblName.setStyleName("f");
		absolutePanel.add(lblName, 554, 319);
		lblName.setSize("159px", "28px");
		
		Label lblPassword = new Label("PASSWORD");
		lblPassword.setStyleName("f");
		absolutePanel.add(lblPassword, 554, 388);
		lblPassword.setSize("159px", "28px");

		passwordField.selectAll();
		nameField.selectAll();

		// Create a handler for the sendButton and nameField
		class MyHandler implements ClickHandler, KeyUpHandler {
			public void onClick(ClickEvent event) {
				if(nameField.getText().contains("root")){
					AddUserDialog addUserDialog = new AddUserDialog();
					addUserDialog.setVisible(true);
					addUserDialog.center();
				}else{
					sendNameToServer();
				}
			}

			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					sendNameToServer();
				}
			}

			private void sendNameToServer() {
				// First, we validate the input.
				errorLabel.setText("");

				String textToServer = nameField.getText();
				//UserServiceAsync userService = (UserServiceAsync)GWT.create(UserService.class); 
				userService.login(nameField.getText(),
						passwordField.getText(),
						new AsyncCallback<User>() {
					
							@Override
							public void onSuccess(User loginUser) {
								// TODO Auto-generated method stub
								final LogginInfoPage lp = new LogginInfoPage(loginUser);
								if (loginUser!=null) {
									Window.alert("Login success!");
									RootPanel.get("Container").clear();
									RootPanel.get("Container").add(lp);
									
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
		}

		// Add a handler to send the name to the server
		MyHandler handler = new MyHandler();
		sendButton.addClickHandler(handler);
		nameField.addKeyUpHandler(handler);
	}
}
