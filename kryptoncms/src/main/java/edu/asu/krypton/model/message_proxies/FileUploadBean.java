package edu.asu.krypton.model.message_proxies;



import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * Represents file uploaded from extjs form
 * 
 * @author Loiane Groner
 * http://loiane.com
 * http://loianegroner.com
 */
public class FileUploadBean {

	private CommonsMultipartFile upload;
	public CommonsMultipartFile getUpload() {
		return upload;
	}
	public void setUpload(CommonsMultipartFile upload) {
		this.upload = upload;
	}
}
