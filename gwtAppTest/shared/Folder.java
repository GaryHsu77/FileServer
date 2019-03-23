package gwtAppTest.shared;

import java.io.Serializable;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Key;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Folder implements Serializable {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	Long id;

	@Persistent
	Long parentUserId;
	@Persistent
	Long parentFolderId;

	@Persistent
	String folderName;

	@Persistent
	String shareName;

	public Folder() {

	}

	public Folder(String folderName, String shareName, Long parentUserId, Long parentFolderId) {
		super();
		this.folderName = folderName;
		this.parentUserId = parentUserId;
		this.parentFolderId = parentFolderId;
		this.shareName = shareName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParentUserId() {
		return parentUserId;
	}

	public void setParentUserId(Long parentUserId) {
		this.parentUserId = parentUserId;
	}

	public Long getParentFolderId() {
		return parentFolderId;
	}

	public void setParentFolderId(Long parentFolderId) {
		this.parentFolderId = parentFolderId;
	}

	public String getFolderName() {
		return folderName;
	}

	public String getShareName() {
		return shareName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

}
