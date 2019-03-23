package gwtAppTest.client.file;

import java.util.List;

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
import gwtAppTest.shared.User;

public class AddShareFolderDialog extends DialogBox {

	FileServiceAsync fileService = (FileServiceAsync) GWT.create(FileService.class);
	UserServiceAsync userService = (UserServiceAsync) GWT.create(UserService.class);
	List<Folder> shareFolders;
	private final User loginUser2;

	public AddShareFolderDialog(User loginUser, final File f) {

		this.loginUser2 = loginUser;

		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setStyleName("h1");
		setWidget(verticalPanel);
		verticalPanel.setSize("363px", "225px");

		Label lblCreatAFolder = new Label("Share File");
		lblCreatAFolder.setStyleName("lblGwtplaysample");
		verticalPanel.add(lblCreatAFolder);
		verticalPanel.setCellVerticalAlignment(lblCreatAFolder, HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel.setCellHorizontalAlignment(lblCreatAFolder, HasHorizontalAlignment.ALIGN_CENTER);
		lblCreatAFolder.setHeight("42px");

		HorizontalPanel horizontalPanel_2 = new HorizontalPanel();
		verticalPanel.add(horizontalPanel_2);

		Label lblInputTheShare = new Label("input the share name:");
		lblInputTheShare.setStyleName("fileTable");
		horizontalPanel_2.add(lblInputTheShare);
		lblInputTheShare.setSize("143px", "32px");

		final TextBox shareBox = new TextBox();
		horizontalPanel_2.add(shareBox);
		shareBox.setSize("208px", "25px");

		final Label errorLabel = new Label("");
		errorLabel.setStyleName("lblGwtplaysample");
		verticalPanel.add(errorLabel);

		HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
		horizontalPanel_1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.add(horizontalPanel_1);
		horizontalPanel_1.setSize("361px", "31px");

		Button btnComfirm = new Button("comfirm");
		btnComfirm.setStyleName("sdTextBox");
		btnComfirm.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// final String folderName = folderNameTextBox.getText();
				final String shareName = shareBox.getText();
				final Long parentUserId = loginUser2.getId();
				fileService.getFoldersByShareId(null, new AsyncCallback<List<Folder>>() {
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						System.out.println("aa");
					}

					@Override
					public void onSuccess(List<Folder> result) {
						// TODO Auto-generated method stub
						shareFolders = result;
						System.out.println("doit");
						int a = 0;
						for (final Folder fo : shareFolders) {
							System.out.println("doit1");
							if (fo.getFolderName() != null) {/*
								if (fo.getFolderName().equals(shareName)) {
									errorLabel.setText("Share name have already exist!");
									a = 0;
									break;
								} else {
									a = 1;
								}*/a=1;
							} else {
								a = 1;
							}

						}
						if (a == 1) {
							System.out.println("doit3");
							System.out.println();
							final Folder addedFolder = new Folder(shareName, shareName, parentUserId, null);
							fileService.addFolder(addedFolder, new AsyncCallback<Boolean>() {
								@Override
								public void onSuccess(Boolean result) {
									errorLabel.setText("insert !");
								}

								@Override
								public void onFailure(Throwable caught) {
								}
							});

							fileService.getFoldersByShareId(null, new AsyncCallback<List<Folder>>() {

								@Override
								public void onFailure(Throwable caught) {
									// TODO Auto-generated method stub
								}

								@Override
								public void onSuccess(List<Folder> result) {
									// TODO Auto-generated method stub
									for (final Folder fo2 : shareFolders) {
										if (fo2.getFolderName().equals(shareName)) {
											// addedFolder.setId(fo2.getId());
											System.out.println(fo2.getFolderName() + "11111");
											System.out.println(fo2.getId());
										}
									}
								}
							});

							System.out.println(addedFolder.getId() + "   ****");
							File sf = new File(shareName, addedFolder.getId(), f);
							fileService.addFile(sf, new AsyncCallback<Boolean>() {
								@Override
								public void onSuccess(Boolean result) {
									errorLabel.setText("insert !");
									// System.out.println("新增成功");
								}

								@Override
								public void onFailure(Throwable caught) {
								}
							});

							fileService.deleteFile(f, new AsyncCallback<Boolean>() {
								@Override
								public void onSuccess(Boolean result) {
									errorLabel.setText("delete !");
									// System.out.println("刪除成功");
									hide();
								}

								@Override
								public void onFailure(Throwable caught) {
									hide();
								}
							});
						}
					}
				});

			}
		});
		horizontalPanel_1.add(btnComfirm);
		horizontalPanel_1.setCellHorizontalAlignment(btnComfirm, HasHorizontalAlignment.ALIGN_CENTER);

		Button btnCancel = new Button("cancel");
		btnCancel.setStyleName("sdTextBox");
		btnCancel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		horizontalPanel_1.add(btnCancel);
		horizontalPanel_1.setCellHorizontalAlignment(btnCancel, HasHorizontalAlignment.ALIGN_CENTER);
	}
}
