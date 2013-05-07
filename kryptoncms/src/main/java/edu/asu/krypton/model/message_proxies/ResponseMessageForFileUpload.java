package edu.asu.krypton.model.message_proxies;

import java.util.ArrayList;

public class ResponseMessageForFileUpload {
	public ResponseMessageForFileUpload(UploadedFileDetails  fileDetails){
		this.files.add(fileDetails);
	}
	ArrayList<UploadedFileDetails>files=new ArrayList<UploadedFileDetails>();

	public ArrayList<UploadedFileDetails> getFiles() {
		return files;
	}

	public void setFiles(ArrayList<UploadedFileDetails> files) {
		this.files = files;
	}
	
}
