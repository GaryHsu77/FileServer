package gwtAppTest.client;

import gwtAppTest.client.file.AddFileDialog;
import gwtAppTest.client.file.AddFolderDialog;
import gwtAppTest.client.file.AddShareFolderDialog;
import gwtAppTest.client.file.AddSubFileDialog;
import gwtAppTest.client.file.AddSubFolderDialog;
import gwtAppTest.client.file.FileService;
import gwtAppTest.client.file.FileServiceAsync;
import gwtAppTest.client.upload.UploadDialog2;
import gwtAppTest.client.user.AddUserDialog;
import gwtAppTest.client.user.UserService;
import gwtAppTest.client.user.UserServiceAsync;
import gwtAppTest.shared.File;
import gwtAppTest.shared.Folder;
import gwtAppTest.shared.User;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.ValueBoxBase.TextAlignment;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;

public class LogginInfoPage extends Composite {
	private FlexTable Table;
	private FlexTable fileTable;
	private FlexTable shareTable;
	private TextBox ShareNametb;
	private ArrayList<String> memData = new ArrayList<String>();
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
	UserServiceAsync userService = (UserServiceAsync) GWT.create(UserService.class);
	FileServiceAsync fileService = (FileServiceAsync) GWT.create(FileService.class);
	private Label errorLabel;
	List<User> users;
	List<Folder> folders;
	List<Folder> shareFolders;
	List<File> files;
	List<File> shareFiles;
	Folder delFolder;
	File delFile;
	User delUser;
	User loginUser2;
	User sharer;
	int x;
	int y = 0;
	int s;
	int z;
	Folder myFolder;
	private File myFile;
	String share = null;

	public LogginInfoPage(User loginUser) {
		this.loginUser2 = loginUser;
		VerticalPanel mainPanel = new VerticalPanel();
		mainPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		mainPanel.setStyleName("null");
		MenuBar menuBar = new MenuBar(false);
		menuBar.setStyleName("fileTable");
		mainPanel.add(menuBar);
		menuBar.setHeight("26px");
		MenuBar menuBar_1 = new MenuBar(true);

		initWidget(mainPanel);
		mainPanel.setSize("732px", "700px");

		Label lblWebDrive = new Label("WEB DRIVE");
		lblWebDrive.setStyleName("lblGwtplaysample");
		mainPanel.add(lblWebDrive);

		HorizontalPanel horizontalPanel_2 = new HorizontalPanel();
		horizontalPanel_2.setStyleName("menu");
		mainPanel.add(horizontalPanel_2);
		horizontalPanel_2.setWidth("695px");
		VerticalPanel sonPanel = new VerticalPanel();//
		horizontalPanel_2.add(sonPanel);
		sonPanel.setStyleName("null");
		Table = new FlexTable();

		sonPanel.add(Table);
		sonPanel.setSize("327px", "357px");
		Table.setStyleName("Table");
		Table.setWidth("322px");

		Label label_6 = new Label("");
		sonPanel.add(label_6);

		fileTable = new FlexTable();
		fileTable.setStyleName("fileTable");
		fileTable.setWidth("322px");
		sonPanel.add(fileTable);

		Label label_5 = new Label("");
		horizontalPanel_2.add(label_5);
		label_5.setSize("14px", "357px");

		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setStyleName("menu");
		horizontalPanel_2.add(verticalPanel);
		verticalPanel.setSize("377px", "351px");

		VerticalPanel verticalPanel_1 = new VerticalPanel();
		verticalPanel_1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.add(verticalPanel_1);
		verticalPanel_1.setWidth("389px");

		Label lblUserCortroll = new Label("User Cortroll");
		lblUserCortroll.setStyleName("lblGwtplaysample");
		verticalPanel_1.add(lblUserCortroll);
		lblUserCortroll.setSize("318px", "57px");

		Label label_4 = new Label("");
		verticalPanel_1.add(label_4);
		HorizontalPanel addPanel = new HorizontalPanel();
		verticalPanel_1.add(addPanel);

		Button btnInsertuser = new Button("insertUser");
		btnInsertuser.setStyleName("sdTextBox");
		btnInsertuser.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				addNote();
			}
		});
		addPanel.add(btnInsertuser);
		btnInsertuser.setSize("106px", "43px");

		Button btnrefresh = new Button("re");
		btnrefresh.setStyleName("sdTextBox");
		btnrefresh.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				autoPrintUser();
			}
		});

		Label lblNewLabel = new Label("     ");
		addPanel.add(lblNewLabel);
		lblNewLabel.setSize("32px", "31px");
		addPanel.add(btnrefresh);
		btnrefresh.setText("userRefresh");
		btnrefresh.setSize("115px", "42px");

		VerticalPanel verticalPanel_2 = new VerticalPanel();
		verticalPanel.add(verticalPanel_2);

		Label lblFileCortroll = new Label("File Cortroll");
		lblFileCortroll.setStyleName("lblGwtplaysample");
		verticalPanel_2.add(lblFileCortroll);

		Label lblNewLabel_1 = new Label("");
		verticalPanel_2.add(lblNewLabel_1);

		HorizontalPanel horizontalPanel = new HorizontalPanel();
		verticalPanel_2.add(horizontalPanel);

		Button btnUpload = new Button("upload");
		btnUpload.setStyleName("sdTextBox");
		btnUpload.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				autoPrintFile();
				UploadDialog2 uploadDialog = new UploadDialog2(loginUser2);
				uploadDialog.center();
			}
		});
		btnUpload.setText("uploadfile");
		horizontalPanel.add(btnUpload);
		btnUpload.setWidth("107px");

		Label label = new Label("     ");
		horizontalPanel.add(label);
		label.setSize("30px", "31px");

		Button btnRemovefile = new Button("removeFile");
		btnRemovefile.setStyleName("sdTextBox");
		horizontalPanel.add(btnRemovefile);
		btnRemovefile.setWidth("111px");

		Label label_1 = new Label("     ");
		horizontalPanel.add(label_1);
		label_1.setSize("32px", "31px");

		Button btnFilerefresh = new Button("fileRefresh");
		btnFilerefresh.setStyleName("sdTextBox");
		btnFilerefresh.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				fileTable.removeAllRows();
				myFolder = null;
				myFile = null;
				x = 0;
				y = 0;
				System.out.println("null");
				refresh();
			}
		});
		horizontalPanel.add(btnFilerefresh);

		HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
		verticalPanel_2.add(horizontalPanel_1);

		Button btnCreatfolder = new Button("creatFolder");
		btnCreatfolder.setStyleName("sdTextBox");
		btnCreatfolder.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (myFolder == null) {
					AddFolderDialog addFolderDialog = new AddFolderDialog(loginUser2);
					addFolderDialog.center();
				} else {
					AddSubFolderDialog addSubFolderDialog = new AddSubFolderDialog(myFolder);
					addSubFolderDialog.center();
				}
			}
		});
		horizontalPanel_1.add(btnCreatfolder);
		btnCreatfolder.setWidth("107px");

		Label label_2 = new Label("     ");
		horizontalPanel_1.add(label_2);
		label_2.setSize("30px", "31px");

		Button btnDeletefolder = new Button("deleteFolder");
		btnDeletefolder.setStyleName("sdTextBox");
		horizontalPanel_1.add(btnDeletefolder);
		btnDeletefolder.setWidth("111px");

		Label label_3 = new Label("     ");
		horizontalPanel_1.add(label_3);
		label_3.setSize("32px", "31px");

		Button btnAddfile = new Button("addFile");
		btnAddfile.setStyleName("sdTextBox");
		btnAddfile.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (myFolder == null) {
					System.out.println("==");
					AddFileDialog addFileDialog = new AddFileDialog(loginUser2);
					addFileDialog.center();
				} else {
					System.out.println("sub");
					AddSubFileDialog addSubFileDialog = new AddSubFileDialog(myFolder);
					addSubFileDialog.center();
				}
			}
		});
		horizontalPanel_1.add(btnAddfile);
		btnAddfile.setWidth("109px");

		VerticalPanel verticalPanel_3 = new VerticalPanel();
		verticalPanel_3.setStyleName("h1");
		verticalPanel_3.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		mainPanel.add(verticalPanel_3);
		verticalPanel_3.setSize("715px", "96px");

		Label lblShareDrive = new Label("Share Drive");
		lblShareDrive.setStyleName("lblGwtplaysample");
		verticalPanel_3.add(lblShareDrive);

		ShareNametb = new TextBox();
		verticalPanel_3.add(ShareNametb);
		ShareNametb.setAlignment(TextAlignment.CENTER);
		ShareNametb.setStyleName("null");
		ShareNametb.setText("share Name");
		ShareNametb.setWidth("262px");

		Button btnNewButton = new Button("New button");
		btnNewButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				share = ShareNametb.getText();
				s = 0;
				z = 0;
				shareRefresh();
			}
		});
		btnNewButton.setStyleName("sdTextBox");
		verticalPanel_3.add(btnNewButton);
		btnNewButton.setText("Enter");
		btnNewButton.setWidth("265px");

		shareTable = new FlexTable();
		shareTable.setStyleName("lblGwtplaysample");

		verticalPanel_3.add(shareTable);

		errorLabel = new Label("");
		mainPanel.add(errorLabel);
		errorLabel.setSize("62px", "34px");

		setStyleName("tablePanel");
		autoPrintUser();
		refresh();
	}

	public void autoPrintUser() {
		Table.removeAllRows();
		Table.setText(0, 0, "Name(ID)");
		Table.setText(0, 1, "Password");
		Table.setText(0, 2, "email");
		Table.setText(0, 3, "Remove");
		userService.getAllUsers(new AsyncCallback<List<User>>() {
			public void onSuccess(List<User> result) {
				users = result;
				// Table.removeAllRows();
				int x = 1;
				for (User u : users) {
					x++;
					displayNote(u.getUserName(), u.getPassword());
				}
			}

			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Window.alert("remote call failure!");
			}
		});
	}

	public void autoPrintFile() {
		fileTable.removeAllRows();
		fileTable.setText(0, 0, "Name(ID)");
		fileTable.setText(0, 1, "Password");
		fileTable.setText(0, 2, "email");
		fileTable.setText(0, 3, "Remove");
	}

	public void addNote() {
		AddUserDialog addUserDialog = new AddUserDialog();
		addUserDialog.setVisible(true);
		addUserDialog.center();
		autoPrintUser();
	}

	private void displayNote(final String name, final String pw) {
		// Add the Note to the table.
		int row = Table.getRowCount();
		memData.add(name);
		Table.setText(row, 0, name);
		Table.setText(row, 1, pw);

		Button removeNoteButton = new Button("x");
		removeNoteButton.addStyleDependentName("remove");
		removeNoteButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				removeNote(name);
			}
		});
		Table.setWidget(row, 3, removeNoteButton);
	}

	private void removeNote(final String note) {
		final DialogBox dialog = new DialogBox();

		Button confirmButton = new Button("sure dele?");
		Button cancelButton = new Button("close");
		for (int i = 0; i < Table.getRowCount() - 1; i++) {
			if (users.get(i).getUserName() == note) {
				delUser = users.get(i);
				System.out.println(delUser);
				break;
			}
		}
		confirmButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				userService.deleteUser(delUser, // 可以正確取得選擇的User
						new AsyncCallback<Boolean>() {
							@Override
							public void onSuccess(Boolean result) {
								dialog.hide();
								Window.alert(result ? "delsuss" : "warry");
								autoPrintUser();
							}

							@Override
							public void onFailure(Throwable caught) {
								dialog.hide();
							}
						});
			}
		});
		cancelButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				dialog.hide();
			}
		});
		HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.add(confirmButton);
		hPanel.add(cancelButton);
		dialog.setText("sure dele?");
		dialog.setWidget(hPanel);
		dialog.setSize("200px", "150px");
		dialog.center();

	}

	private void undisplayNote(String note) {
		int removedIndex = memData.indexOf(note);
		memData.remove(removedIndex);
		Table.removeRow(removedIndex + 1);
	}

	private void refresh() {
		if (myFolder == null) {
			readFoldersByUserId();
			readFilesByUserId();
		} else {
			readFoldersByFolderId(myFolder.getId());
			readFilesByFolderId(myFolder.getId());
		}
	}

	private void shareRefresh() {
		if (share == null) {
			shareTable.removeAllRows();
		} else {
			readFoldersByShareId();
			readFilesByShareId();
		}

	}

	private void readFoldersByUserId() {
		fileService.getFoldersByUserId(loginUser2.getId(), new AsyncCallback<List<Folder>>() {

			public void onSuccess(List<Folder> result) {
				myFolder = null;
				folders = result; //
				fileTable.removeAllRows();
				fileTable.setText(0, 0, "Type");
				fileTable.setText(0, 1, "Name");
				fileTable.setText(0, 2, "Download");
				fileTable.setText(0, 3, "Remove");
				fileTable.setText(0, 4, "Enter");
				fileTable.setText(0, 5, "Share");

				for (final Folder f : folders) {
					if (f.getShareName() == null) {
						x++;
						fileTable.setText(x, 0, "[Folder]");
						fileTable.setText(x, 1, f.getFolderName());
						Button removeFoldButton = new Button("x");
						removeFoldButton.addStyleDependentName("remove");
						removeFoldButton.addClickHandler(new ClickHandler() {
							public void onClick(ClickEvent event) {
								// removeNote(name);
							}
						});
						Button entFoldButton = new Button("E");
						entFoldButton.addStyleDependentName("remove");
						entFoldButton.addClickHandler(new ClickHandler() {
							public void onClick(ClickEvent event) {
								enterFolder(f.getFolderName());
							}
						});

						fileTable.setWidget(y + 1, 3, removeFoldButton);

						fileTable.setWidget(y + 1, 4, entFoldButton);
						y++;
					}
				}

			}

			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Window.alert("remote call failure!");
			}
		});
	}

	private void readFoldersByShareId() {
		fileService.getFoldersByShareId("", new AsyncCallback<List<Folder>>() {

			public void onSuccess(List<Folder> result) {
				shareFolders = null;
				shareFolders = result; //
				shareTable.removeAllRows();
				shareTable.setText(0, 0, "Type");
				shareTable.setText(0, 1, "Name");
				shareTable.setText(0, 2, "Download");
				shareTable.setText(0, 3, "Remove");
				shareTable.setText(0, 4, "Enter");
				shareTable.setText(0, 5, "Share");
				/*
				 * for (final Folder f : shareFolders) { if (f.getShareName() ==
				 * null) { x++; shareTable.setText(x, 0, "[Folder]");
				 * shareTable.setText(x, 1, f.getFolderName()); Button
				 * removeFoldButton = new Button("x");
				 * removeFoldButton.addStyleDependentName("remove");
				 * removeFoldButton.addClickHandler(new ClickHandler() { public
				 * void onClick(ClickEvent event) { // removeNote(name); } });
				 * Button entFoldButton = new Button("E");
				 * entFoldButton.addStyleDependentName("remove");
				 * entFoldButton.addClickHandler(new ClickHandler() { public
				 * void onClick(ClickEvent event) {
				 * enterFolder(f.getFolderName()); } });
				 * 
				 * shareTable.setWidget(y + 1, 3, removeFoldButton);
				 * 
				 * shareTable.setWidget(y + 1, 4, entFoldButton); y++; }
				 * 
				 * }
				 */

			}

			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Window.alert("remote call failure!");
			}
		});
	}

	private void readFilesByShareId() {
		fileService.getFilesByShareId("", new AsyncCallback<List<File>>() {

			public void onSuccess(List<File> result) {
				shareFiles = result; //
				// x++;
				for (final File f : shareFiles) {
					if (f.getFileShare() != null) {
						if (f.getFileShare().equals(share)) {
							x++;
							shareTable.setText(x, 0, "[File]");
							shareTable.setText(x, 1, f.getFileName());
							Button removeFoldButton = new Button("x");
							removeFoldButton.addStyleDependentName("remove");
							removeFoldButton.addClickHandler(new ClickHandler() {
								public void onClick(ClickEvent event) {
									removefile(f.getFileName());
								}
							});
							shareTable.setWidget(y + 1, 3, removeFoldButton);

							Button downloadFoldButton = new Button("Download");
							downloadFoldButton.addStyleDependentName("Download");
							downloadFoldButton.addClickHandler(new ClickHandler() {
								public void onClick(ClickEvent event) {
									downloadShareFile(f.getFileName());
								}
							});
							shareTable.setWidget(y + 1, 2, downloadFoldButton);

							Button shareFoldButton = new Button("share");
							shareFoldButton.addStyleDependentName("share");
							shareFoldButton.addClickHandler(new ClickHandler() {
								public void onClick(ClickEvent event) {
									AddShareFolderDialog shareFloder = new AddShareFolderDialog(loginUser2, f);
									shareFloder.setVisible(true);
									shareFloder.center();
								}
							});
							shareTable.setWidget(y + 1, 5, shareFoldButton);
							y++;
						} else
							continue;
					}

				}

			}

			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Window.alert("remote call failure!");
			}
		});
	}

	private void readFilesByUserId() {
		fileService.getFilesByUserId(loginUser2.getId(), new AsyncCallback<List<File>>() {

			public void onSuccess(List<File> result) {
				files = result; //
				// x++;
				for (final File f : files) {
					x++;
					fileTable.setText(x, 0, "[File]");
					fileTable.setText(x, 1, f.getFileName());
					Button removeFoldButton = new Button("x");
					removeFoldButton.addStyleDependentName("remove");
					removeFoldButton.addClickHandler(new ClickHandler() {
						public void onClick(ClickEvent event) {
							removefile(f.getFileName());
						}
					});
					fileTable.setWidget(y + 1, 3, removeFoldButton);

					Button downloadFoldButton = new Button("Download");
					downloadFoldButton.addStyleDependentName("Download");
					downloadFoldButton.addClickHandler(new ClickHandler() {
						public void onClick(ClickEvent event) {
							downloadFile(f.getFileName());
						}
					});
					fileTable.setWidget(y + 1, 2, downloadFoldButton);

					Button shareFoldButton = new Button("share");
					shareFoldButton.addStyleDependentName("share");
					shareFoldButton.addClickHandler(new ClickHandler() {
						public void onClick(ClickEvent event) {
							AddShareFolderDialog shareFloder = new AddShareFolderDialog(loginUser2, f);
							shareFloder.setVisible(true);
							shareFloder.center();
						}
					});
					fileTable.setWidget(y + 1, 5, shareFoldButton);
					y++;
				}

			}

			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Window.alert("remote call failure!");
			}
		});
	}

	private void removefile(final String note) {

		final DialogBox dialog = new DialogBox();
		Button confirmButton = new Button("確認刪除");
		Button cancelButton = new Button("取消");
		for (int i = 0; i < fileTable.getRowCount() - 1; i++) {
			if (files.get(i).getFileName() == note) {
				delFile = files.get(i);
				System.out.println(delFile);
				break;
			}
		}
		confirmButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				fileService.deleteFile(delFile, new AsyncCallback<Boolean>() {
					@Override
					public void onSuccess(Boolean result) {
						dialog.hide();
						readFoldersByUserId();
						readFilesByUserId();
					}

					@Override
					public void onFailure(Throwable caught) {
						dialog.hide();
					}
				});
			}
		});
		cancelButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				dialog.hide();
			}
		});
		HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.add(confirmButton);
		hPanel.add(cancelButton);
		dialog.setText("是否刪除此檔案?");
		dialog.setWidget(hPanel);
		dialog.setSize("200px", "150px");
		dialog.center();

	}

	private void downloadFile(final String note) {
		for (int i = 0; i < fileTable.getRowCount() - 1; i++) {
			if (files.get(i).getFileName() == note) {
				delFile = files.get(i);
				System.out.println(delFile);
				break;
			}
		}
		// String gogogoUrl = "http://127.0.0.1:8888" + delFile.getFileURL();
		String gogogoUrl = "http://gwtplaytest2.appspot.com/" + delFile.getFileURL();
		String gogogoFileName = delFile.getFileName();
		Window.open(gogogoUrl, gogogoFileName, null);
	}

	private void downloadShareFile(final String note) {
		System.out.println(shareTable.getRowCount());
		for (int i = 0; i < shareTable.getRowCount(); i++) {
			System.out.println(shareFiles.get(i).getFileName());
			System.out.println(note);
			if (shareFiles.get(i).getFileName() == note) {
				delFile = shareFiles.get(i);
				System.out.println(delFile);
				break;
			}
		}
		// String gogogoUrl = "http://127.0.0.1:8888" + delFile.getFileURL();
		String gogogoUrl = "http://gwtplaytest2.appspot.com/" + delFile.getFileURL();
		String gogogoFileName = delFile.getFileName();
		Window.open(gogogoUrl, gogogoFileName, null);
	}

	private void enterFolder(final String note) {
		for (int i = 0; i < fileTable.getRowCount() - 1; i++) {
			if (folders.get(i).getFolderName() == note) {
				delFolder = folders.get(i);

				break;
			}
		}
		myFolder = delFolder;
		System.out.println(delFolder.getFolderName());
		readFoldersByFolderId(delFolder.getId());
		readFilesByFolderId(delFolder.getId());
	}

	private void readFoldersByFolderId(Long parentFolderId) {
		fileService.getFoldersByFolderId(parentFolderId, new AsyncCallback<List<Folder>>() {

			public void onSuccess(List<Folder> result) {
				System.out.println("fd");
				folders = result; //
				fileTable.removeAllRows();
				fileTable.setText(0, 0, "Type");
				fileTable.setText(0, 1, "Name");
				fileTable.setText(0, 2, "Download");
				fileTable.setText(0, 3, "Remove");
				fileTable.setText(0, 4, "Enter");
				x = 0;
				y = 0;
				for (final Folder f : folders) {
					x++;
					fileTable.setText(x, 0, "[Folder]");
					fileTable.setText(x, 1, f.getFolderName());
					Button removeFoldButton = new Button("x");
					removeFoldButton.addStyleDependentName("remove");
					removeFoldButton.addClickHandler(new ClickHandler() {
						public void onClick(ClickEvent event) {
							// removeNote(name);
						}
					});
					Button entFoldButton = new Button("E");
					entFoldButton.addStyleDependentName("remove");
					entFoldButton.addClickHandler(new ClickHandler() {
						public void onClick(ClickEvent event) {
							enterFolder(f.getFolderName());
						}
					});
					fileTable.setWidget(y + 1, 3, removeFoldButton);

					fileTable.setWidget(y + 1, 4, entFoldButton);
					y++;
				}

			}

			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Window.alert("remote call failure!");
			}
		});
	}

	private void readFilesByFolderId(Long parentFolderId) {
		System.out.println(parentFolderId);
		fileService.getFilesByFolderId(parentFolderId, new AsyncCallback<List<File>>() {
			public void onSuccess(List<File> result) {
				System.out.println(result.size());
				for (int i = 0; i < result.size(); i++) {
					System.out.println(result.get(i).getFileName());
				}
				files = result; //
				// fileTable.removeAllRows();

				for (final File f : files) {
					System.out.println(f.getFileName());
					x++;
					fileTable.setText(x, 0, "[File]");
					fileTable.setText(x, 1, f.getFileName());
					Button removeFoldButton = new Button("x");
					removeFoldButton.addStyleDependentName("remove");
					removeFoldButton.addClickHandler(new ClickHandler() {
						public void onClick(ClickEvent event) {
							removefile(f.getFileName());
						}
					});
					fileTable.setWidget(y + 1, 3, removeFoldButton);

					Button downloadFoldButton = new Button("Download");
					downloadFoldButton.addStyleDependentName("Download");
					downloadFoldButton.addClickHandler(new ClickHandler() {
						public void onClick(ClickEvent event) {
							downloadFile(f.getFileName());
						}
					});
					fileTable.setWidget(y + 1, 2, downloadFoldButton);
					y++;
				}

			}

			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Window.alert("remote call failure!");
			}
		});
	}
}
