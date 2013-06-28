package edu.asu.krypton.controllers;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.asu.krypton.model.message_proxies.FileUploadBean;
import edu.asu.krypton.model.message_proxies.ResponseMessageForFileUpload;
import edu.asu.krypton.model.message_proxies.UploadedFileDetails;
import edu.asu.krypton.service.FileUploadService;

/**
/**
 * Controller - Spring
 * 
 * @author Loiane Groner
 * http://loiane.com
 * http://loianegroner.com
 */
@Controller
//@ControllerAdvice
public class FileUploadController extends edu.asu.krypton.controllers.Controller
{
	private final String DEFAULT_VIEW = "file_upload";
	private final String DEFAULT_DIR = "bodies/";
	
	@Autowired(required=true)
	private FileUploadService fileUploadService;
	
	@RequestMapping(method=RequestMethod.GET,value="/file/upload-demo")
	public String getHome(HttpServletRequest request,Model model){
		//return "fileUpload";
		return appropriateView(request, DEFAULT_DIR+DEFAULT_VIEW, defaultView(model,DEFAULT_VIEW));
	}
	
	@RequestMapping(method=RequestMethod.DELETE,value="/getfiles/{fileName:.+}")
	public @ResponseBody String deleteHandler(HttpServletRequest request,@PathVariable String fileName){
		fileUploadService.deleteFile(request, fileName);
		return "";
	}
	@RequestMapping(value="/getfiles/thumbnail/{fileName:.+}",method=RequestMethod.GET)
	public @ResponseBody byte[] getThumbnail(HttpServletRequest request,@PathVariable String fileName) throws Exception {   
	    return IOUtils.toByteArray(
	    		new BufferedInputStream(
    					new FileInputStream(fileUploadService.getThumbnail(request, fileName))
    			)
	    );
	}
	
	@RequestMapping(value="/getfiles/{fileName:.+}",method=RequestMethod.GET)
	public @ResponseBody byte[] getImage(HttpServletRequest request,@PathVariable String fileName) throws Exception {
	    return IOUtils.toByteArray(
	    		new BufferedInputStream(
	    				new FileInputStream(fileUploadService.getImage(request, fileName))
	    		)
	    );
	}
	
	@RequestMapping(value = "/file/upload.Action",method = RequestMethod.POST)
	public  @ResponseBody String create(@RequestParam("CKEditor") String CKEditor,@RequestParam("CKEditorFuncNum") String CKEditorFuncNum,@RequestParam("langCode") String langCode,FileUploadBean uploadItem, BindingResult result,HttpServletRequest request)throws Exception{
//		if (result.hasErrors()){
//			for(ObjectError error : result.getAllErrors()){
//				System.err.println("Error: " + error.getCode() +  " - " + error.getDefaultMessage());
//			}
//			//set extjs return - error
//			FormResult.setSuccess(false);
//			return FormResult.toString();
//		}
		UploadedFileDetails uploadedFileDetails=fileUploadService.saveImage(request, uploadItem);
		String error="";
		String url="'"+uploadedFileDetails.getUrl()+"'";
		String response="<script type=\"text/javascript\">window.parent.CKEDITOR.tools.callFunction("+CKEditorFuncNum+","+ url+","+"'"+error+"'" +");</script>";
		return response;
	}

	@RequestMapping(value = "/file/upload.action",method = RequestMethod.POST)
	public @ResponseBody ResponseMessageForFileUpload create(FileUploadBean uploadItem, BindingResult result,HttpServletRequest request)throws Exception{
//		if (result.hasErrors()){
//			for(ObjectError error : result.getAllErrors()){
//				System.err.println("Error: " + error.getCode() +  " - " + error.getDefaultMessage());
//			}
//			//set extjs return - error
//			FormResult.setSuccess(false);
//			return FormResult.toString();
//		}
		return new ResponseMessageForFileUpload(fileUploadService.saveImage(request, uploadItem));
	}
	
	@RequestMapping(value="/file/upload.action",method= RequestMethod.HEAD)
	public void head(){}
//	@ExceptionHandler(Throwable.class)
//	public @ResponseBody String handleException(Throwable e,HttpServletRequest request) throws IOException
//	{
//		
//		return "{\"success\" : false}";
//	}
	
}