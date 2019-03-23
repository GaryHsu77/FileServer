package gwtAppTest.client;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.MenuItemSeparator;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import gwtAppTest.client.file.AddFileDialog;
import gwtAppTest.client.file.AddFolderDialog;
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
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Hidden;

public class MainList extends DialogBox implements ClickHandler {

	int selectedRow = -1; // 陰影

	List<User> users; // 用來儲存顯示清單的List
	List<Folder> folders;
	List<File> files;

	int x;

	List<Long> savedParentId; // 用來儲存回到上一頁的key

	User loginUser2; // 登入的使用者

	private Folder myFolder;
	private File myFile;

	private FlexTable flexTable;

	UserServiceAsync userService = (UserServiceAsync) GWT.create(UserService.class);
	FileServiceAsync fileService = (FileServiceAsync) GWT.create(FileService.class);

	public MainList(User loginUser) {

		this.loginUser2 = loginUser;

		DockPanel dockPanel = new DockPanel();
		setWidget(dockPanel);
		dockPanel.setSize("290px", "139px");

		flexTable = new FlexTable();
		dockPanel.add(flexTable, DockPanel.CENTER);

		flexTable.addClickHandler(this);

		MenuBar menuBar = new MenuBar(false);
		dockPanel.add(menuBar, DockPanel.NORTH);
		MenuBar menuBar_1 = new MenuBar(true);

		Command insertCommand = new Command() {

			@Override
			public void execute() {
				// TODO Auto-generated method stub
				AddUserDialog addUserDialog = new AddUserDialog();
				addUserDialog.center();
				readUsers();
			}

		};

		Command deleteCommand = new Command() {
			@Override
			public void execute() {
				final DialogBox dialog = new DialogBox();
				Button confirmButton = new Button("確認刪除");
				Button cancelButton = new Button("取消");
				confirmButton.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						userService.deleteUser(users.get(selectedRow - 2), // 可以正確取得選擇的User
								new AsyncCallback<Boolean>() {
									@Override
									public void onSuccess(Boolean result) {
										if (result)
											Window.alert("刪除成功");
										else
											Window.alert("刪除失敗");
										dialog.hide();
										readUsers();
									}

									@Override
									public void onFailure(Throwable caught) {
										Window.alert("呼叫遠端程序失敗");
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
				dialog.setText("是否刪除此用戶?");
				dialog.setWidget(hPanel);
				dialog.setSize("200px", "150px");
				dialog.center();

			}
		};

		Command modifyCommand = new Command() {

			@Override
			public void execute() {

			}

		};

		Command insertFolderCommand = new Command() {

			@Override
			public void execute() {
				if (myFolder == null) {
					AddFolderDialog addFolderDialog = new AddFolderDialog(loginUser2);
					addFolderDialog.center();
				} else {
					AddSubFolderDialog addSubFolderDialog = new AddSubFolderDialog(myFolder);
					addSubFolderDialog.center();
				}
			}

		};

		Command insertSubFolderCommand = new Command() {

			@Override
			public void execute() {
				AddSubFolderDialog addSubFolderDialog = new AddSubFolderDialog(myFolder);
				addSubFolderDialog.center();
				readFoldersByFolderId(myFolder.getId());
			}

		};

		Command deleteFolderCommand = new Command() {

			@Override
			public void execute() {
				final DialogBox dialog = new DialogBox();
				Button confirmButton = new Button("確認刪除");
				Button cancelButton = new Button("取消");
				confirmButton.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						fileService.deleteFolder(folders.get(selectedRow - 1), // 可以正確取得選擇的User
								new AsyncCallback<Boolean>() {
									@Override
									public void onSuccess(Boolean result) {
										if (result)
											Window.alert("刪除成功");
										else
											Window.alert("刪除失敗");
										dialog.hide();
										readFoldersByUserId();
										readFilesByUserId();
									}

									@Override
									public void onFailure(Throwable caught) {
										Window.alert("呼叫遠端程序失敗");
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
				dialog.setText("是否刪除此資料夾?");
				dialog.setWidget(hPanel);
				dialog.setSize("200px", "150px");
				dialog.center();

			}

		};

		Command insertFileCommand = new Command() {
			@Override
			public void execute() {

				if (myFolder == null) {
					AddFileDialog addFileDialog = new AddFileDialog(loginUser2);
					addFileDialog.center();
				} else {
					AddSubFileDialog addSubFileDialog = new AddSubFileDialog(myFolder);
					addSubFileDialog.center();
				}

			}

		};

		Command insertFileInSubCommand = new Command() {
			@Override
			public void execute() {

				AddSubFileDialog addSubFileDialog = new AddSubFileDialog(myFolder);
				addSubFileDialog.center();

			}

		};

		Command deleteFileCommand = new Command() {

			@Override
			public void execute() {
				final DialogBox dialog = new DialogBox();
				Button confirmButton = new Button("確認刪除");
				Button cancelButton = new Button("取消");
				confirmButton.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						fileService.deleteFile(files.get(selectedRow - 1 - folders.size()), new AsyncCallback<Boolean>() {
							@Override
							public void onSuccess(Boolean result) {
								if (result)
									Window.alert("檔案刪除成功");
								else
									Window.alert("刪除失敗");
								dialog.hide();
								readFoldersByUserId();
								readFilesByUserId();
							}

							@Override
							public void onFailure(Throwable caught) {
								Window.alert("呼叫遠端程序失敗");
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

		};

		Command uploadfileCommand = new Command() {

			@Override
			public void execute() {
				// TODO Auto-generated method stub
				UploadDialog2 uploadDialog = new UploadDialog2(loginUser2);
				uploadDialog.center();
			}

		};

		Command enterCommand = new Command() {
			@Override
			public void execute() {
				myFolder = folders.get(selectedRow - 1);
				readFoldersByFolderId(myFolder.getId());	
			}

		};

		Command refreshCommand = new Command() {
			@Override
			public void execute() {
				refresh();

			}

		};
		Command backCommand = new Command() {
			@Override
			public void execute() {
				int lastIndex = savedParentId.size() - 1;
				if (lastIndex == 0)
					readFoldersByUserId();
				else
					readFoldersByFolderId(savedParentId.get(lastIndex));
			}

		};

		// 宣告menu sub item
		MenuItem mntmUser = new MenuItem("User", false, menuBar_1);

		MenuItem mntmInsert = new MenuItem("insert", false, insertCommand);
		menuBar_1.addItem(mntmInsert);

		MenuItem mntmDelete = new MenuItem("delete", false, deleteCommand);
		menuBar_1.addItem(mntmDelete);

		MenuItem mntmModify = new MenuItem("modify", false, modifyCommand);
		menuBar_1.addItem(mntmModify);
		menuBar.addItem(mntmUser);
		MenuBar menuBar_2 = new MenuBar(true);

		MenuItem mntmFile = new MenuItem("File", false, menuBar_2);

		MenuItem mntmInsertAFolder = new MenuItem("insert a folder", false, insertFolderCommand);
		menuBar_2.addItem(mntmInsertAFolder);

		MenuItem mntmDeleteAFolder = new MenuItem("delete a folder", false, deleteFolderCommand);
		menuBar_2.addItem(mntmDeleteAFolder);

		MenuItem mntmChangeFolderName = new MenuItem("change folder name", false, (Command) null);
		menuBar_2.addItem(mntmChangeFolderName);

		MenuItem mntmUploadfile = new MenuItem("upload a file", false, uploadfileCommand);
		menuBar_2.addItem(mntmUploadfile);

		MenuItem mntmDeleteAfile = new MenuItem("delete afile", false, deleteFileCommand);
		mntmDeleteAfile.setHTML("delete a file");
		menuBar_2.addItem(mntmDeleteAfile);
		menuBar.addItem(mntmFile);

		MenuItem mntmEnter = new MenuItem("Enter", false, enterCommand);
		menuBar.addItem(mntmEnter);

		MenuItem mntmRefresh = new MenuItem("Refresh", false, refreshCommand);
		menuBar.addItem(mntmRefresh);

		MenuItem menuItem = new MenuItem("", false, (Command) null);
		menuBar.addItem(menuItem);

		VerticalPanel verticalPanel = new VerticalPanel();
		dockPanel.add(verticalPanel, DockPanel.SOUTH);
		verticalPanel.setSize("286px", "23px");

		AbsolutePanel absolutePanel = new AbsolutePanel();
		verticalPanel.add(absolutePanel);
		absolutePanel.setSize("239px", "46px");

		Button downloadButton = new Button("download the file");
		downloadButton.setText("download");
		downloadButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				myFile = files.get(selectedRow - 1 - folders.size());
				// Window.alert(myFile.getFileName());
				String gogogoUrl = "http://127.0.0.1:8888" + myFile.getFileURL();
				String gogogoFileName = myFile.getFileName();
				Window.open(gogogoUrl, gogogoFileName, null);
			}
		});
		absolutePanel.add(downloadButton, 93, 10);
		downloadButton.setSize("86px", "29px");

		Hidden hidden = new Hidden("Hidden name");
		absolutePanel.add(hidden, 188, 10);
		hidden.setSize("20px", "20px");

		HorizontalPanel horizontalPanel = new HorizontalPanel();
		verticalPanel.add(horizontalPanel);
		horizontalPanel.setSize("290px", "36px");

		Button btnUserManager = new Button("User List");
		btnUserManager.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				readUsers();
			}
		});
		btnUserManager.setText("User Manager");
		horizontalPanel.add(btnUserManager);
		btnUserManager.setWidth("109px");
		horizontalPanel.setCellHorizontalAlignment(btnUserManager, HasHorizontalAlignment.ALIGN_CENTER);

		Button btnFileManager = new Button("File Manager");
		btnFileManager.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				myFolder = null;
				myFile = null;
				refresh();
			}
		});
		horizontalPanel.add(btnFileManager);
		horizontalPanel.setCellHorizontalAlignment(btnFileManager, HasHorizontalAlignment.ALIGN_CENTER);
		btnFileManager.setWidth("98px");

		// readUsers();
	}

	// 用來顯示使用者清單
	private void readUsers() {

		userService.getAllUsers(new AsyncCallback<List<User>>() {

			public void onSuccess(List<User> result) {
				users = result;
				flexTable.removeAllRows();
				// flexTable.setWidget(0, 0, new Label("gooooooooooooooooood"));
				flexTable.setText(0, 0, "User Name");
				flexTable.setText(0, 1, "Password");
				flexTable.setText(0, 2, "email");
				/*
				 * //測試user用
				 * 
				 * flexTable.setText(0, 0, loginUser2.getUserName());
				 * flexTable.setText(0, 1, loginUser2.getPassword());
				 * flexTable.setText(0, 2, loginUser2.getEmail());
				 */
				int x = 1;
				for (User u : users) {
					x++;
					flexTable.setText(x, 0, u.getUserName());
					flexTable.setText(x, 1, u.getPassword());
					flexTable.setText(x, 2, u.getEmail());
				}
			}

			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Window.alert("remote call failure!");
			}
		});
	}

	private void refresh() {

		if (myFolder == null) {
			readFoldersByUserId();
			readFilesByUserId();
		} else {
			// readFoldersByFolderId(myFolder.getId());
			readFilesByFolderId(myFolder.getId());
		}

	}

	// 用來顯示檔案系統
	private void readFoldersByUserId() {
		fileService.getFoldersByUserId(loginUser2.getId(), new AsyncCallback<List<Folder>>() {

			public void onSuccess(List<Folder> result) {

				myFolder = null;
				folders = result; //
				flexTable.removeAllRows();
				flexTable.setText(0, 0, "Type");
				flexTable.setText(0, 1, "Name");
				x = 0;
				for (Folder f : folders) {
					x++;
					flexTable.setText(x, 0, "[Folder]");
					flexTable.setText(x, 1, f.getFolderName());
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
				for (File f : files) {
					x++;
					flexTable.setText(x, 0, "[File]");
					flexTable.setText(x, 1, f.getFileName());
				}

			}

			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Window.alert("remote call failure!");
			}
		});
	}

	private void readFoldersByFolderId(Long parentFolderId) {
		fileService.getFoldersByFolderId(parentFolderId, new AsyncCallback<List<Folder>>() {

			public void onSuccess(List<Folder> result) {
				folders = result; //
				flexTable.removeAllRows();
				flexTable.setText(0, 0, "Type");
				flexTable.setText(0, 1, "Name");
				x = 0;
				for (Folder f : folders) {
					x++;
					flexTable.setText(x, 0, "[Folder]");
					flexTable.setText(x, 1, f.getFolderName());
				}
			}

			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Window.alert("remote call failure!");
			}
		});
	}

	private void readFilesByFolderId(Long parentFolderId) {
		fileService.getFilesByFolderId(parentFolderId, new AsyncCallback<List<File>>() {

			public void onSuccess(List<File> result) {
				files = result;
				flexTable.removeAllRows();
				x = 0;
				for (File f : files) {
					x++;
					flexTable.setText(x, 0, "[File]");
					flexTable.setText(x, 1, f.getFileName());
				}
			}

			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Window.alert("remote call failure!");
			}
		});
	}

	@Override
	public void onClick(ClickEvent event) {
		Cell cell = flexTable.getCellForEvent(event);
		// 取得儲存格所在的列數
		int row = cell.getRowIndex();

		// 處理選擇陰影樣式
		if (selectedRow != -1) {
			flexTable.getRowFormatter().removeStyleName(selectedRow, "selectedRow");

		}
		flexTable.getRowFormatter().addStyleName(row, "selectedRow");
		selectedRow = row;

		// 利用列數來找到在之上物件的index
		// User p = users.get(row);g
		// Long tmp= users.get(selectedRow-2).getId();
		// tmp= folders.get(selectedRow-2).getId();
	}

	public void onDoubleClick(DoubleClickEvent eventz) {
		// folders.get(selectedRow-2).getId());
	}
}
