package gwtAppTest.shared;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class File implements Serializable {
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	Long id;
	
	@Persistent
	Long parentUserId;
	@Persistent
	Long parentFolderId;
	
	@Persistent
	String FileName;
	@Persistent
	String FileURL;
	@Persistent
	String FileShare;
	
	
	
	public File(){
		
	}
	
	public File(String fileName,String fileURL,Long parentUserId,Long parentFolderId){
		super();
		this.FileName = fileName;
		this.parentUserId= parentUserId;
		this.parentFolderId=parentFolderId;
		this.FileURL=fileURL;
	}
	public File(String FileShare,Long parentFolderId1,File f){
		super();
		this.FileName = f.getFileName();
		this.parentUserId=f.getParentUserId();
		this.parentFolderId=parentFolderId1;
		this.FileURL=f.getFileURL();
		this.FileShare=FileShare;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileName() {
		return FileName;
	}

	public void setFileName(String fileName) {
		FileName = fileName;
	}

	public String getFileURL() {
		return FileURL;
	}
	
	public String getFileShare() {
		return FileShare;
	}

	public void setFileURL(String fileURL) {
		FileURL = fileURL;
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

}
