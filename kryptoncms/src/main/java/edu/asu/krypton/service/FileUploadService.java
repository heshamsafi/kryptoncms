package edu.asu.krypton.service;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import edu.asu.krypton.model.message_proxies.FileUploadBean;
import edu.asu.krypton.model.message_proxies.UploadedFileDetails;

@Service
public class FileUploadService {
	private final String FILE_UPLOAD_PATH = "/files";
	private final String THUMBNAIL_PATH = FILE_UPLOAD_PATH+"/thumbnail";
	
	private final static Logger logger = LoggerFactory.getLogger(FileUploadService.class);
	
	public UploadedFileDetails saveImage(HttpServletRequest request,FileUploadBean uploadItem) throws IOException{
		CommonsMultipartFile file=uploadItem.getUpload();
		String url= buildUrl(request);
	    String savingImageDirectory =request.getSession().getServletContext().getRealPath(FILE_UPLOAD_PATH)+"/";
	    String savingImageThumbnailDirectory=request.getSession().getServletContext().getRealPath(THUMBNAIL_PATH)+"/";
	    logger.debug(savingImageDirectory);
	    File imageDirectory = new File(savingImageDirectory);
	    if(!imageDirectory.exists()) {
	    	imageDirectory.mkdirs();
	    }
		
	    File thumbnailDirectory = new File(savingImageThumbnailDirectory);
	    if(!thumbnailDirectory.exists()) {
	    	thumbnailDirectory.mkdirs();
	    }
  		String thumbnailPath=savingImageThumbnailDirectory+file.getOriginalFilename();
  		System.out.println(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.')+1));
  		ImageIO.write(createThumbnail(file), 
  				//"jpg"
  				file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.')+1)
  				, new File(thumbnailPath));
  		file.transferTo(new File(savingImageDirectory+ file.getOriginalFilename()));
		return new UploadedFileDetails(file.getOriginalFilename(), new Integer((int)file.getSize()), url+file.getOriginalFilename(), url+"thumbnail/"+file.getOriginalFilename(), url+file.getOriginalFilename(), "DELETE");
	}
	public void deleteFile(HttpServletRequest request,String fileName){
	    String savingImageDirectory =request.getSession().getServletContext().getRealPath(FILE_UPLOAD_PATH)+"/"+fileName;
	    String savingImageThumbnailDirectory=request.getSession().getServletContext().getRealPath(THUMBNAIL_PATH)+"/"+fileName;
	    File imageFile 			= new File(savingImageDirectory);
	    File imageThumbnailFile = new File(savingImageThumbnailDirectory);
		if(imageFile.delete()){
			logger.debug(imageFile.getName() + " is deleted!");
		}else{
			logger.debug("Delete operation is failed.");
		}
		if(imageThumbnailFile.delete()){
			logger.debug(imageThumbnailFile.getName() + " is deleted!");
		}else{
			logger.debug("Delete operation is failed.");
		}
	}
	public File getImage(HttpServletRequest request,String fileName){
	    String savingImageDirectory =request.getSession().getServletContext().getRealPath(FILE_UPLOAD_PATH)+"/";
	    return new File(savingImageDirectory+fileName); 
	}
	public File getThumbnail(HttpServletRequest request,String fileName){
	    String savingImageThumbnailDirectory=request.getSession().getServletContext().getRealPath(THUMBNAIL_PATH)+"/";
	    return new File(savingImageThumbnailDirectory+fileName);
	}
	
	private BufferedImage createThumbnail(CommonsMultipartFile file) throws IOException{
  		BufferedImage img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
  		img.createGraphics().drawImage(ImageIO.read(file.getInputStream()).getScaledInstance(100, 100, Image.SCALE_SMOOTH),0,0,null);
  		return img;
	}
	
	
	
	private String buildUrl(HttpServletRequest request){
		return new StringBuffer("")	
			  .append(request.getScheme())
			  .append("://")
			  .append(request.getLocalName())
			  .append(":")
			  .append(request.getServerPort())
			  .append(request.getContextPath())
			  .append("/")
			  .append("getfiles/").toString();
	}
}
