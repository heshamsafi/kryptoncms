package edu.asu.krypton.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class BootstrapStyleDownloader {
	
	@Test
	public void test() throws ClientProtocolException, IOException{
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("http://bootstrap.herokuapp.com/");
		httppost.setHeaders(new Header[]{
			  new BasicHeader("Connection", "Connection")
			 ,new BasicHeader("Content-Type","application/x-www-form-urlencoded")
			 ,new BasicHeader("Accept-Encoding","gzip,deflate,sdch")
			 ,new BasicHeader("Accept-Charset","ISO-8859-1,utf-8;q=0.7,*;q=0.3")
			 ,new BasicHeader("Accept-Language","en-US,en;q=0.8")
			 ,new BasicHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
//			 ,new BasicHeader("Content-Length","1351")
		});
		// Request parameters and other properties.
		List<NameValuePair> params = new ArrayList<NameValuePair>(4);
		params.add(new BasicNameValuePair("css", "[\"reset.less\",\"scaffolding.less\",\"grid.less\",\"layouts.less\",\"type.less\",\"code.less\",\"labels-badges.less\",\"tables.less\",\"forms.less\",\"buttons.less\",\"sprites.less\",\"button-groups.less\",\"navs.less\",\"navbar.less\",\"breadcrumbs.less\",\"pagination.less\",\"pager.less\",\"thumbnails.less\",\"alerts.less\",\"progress-bars.less\",\"hero-unit.less\",\"media.less\",\"tooltip.less\",\"popovers.less\",\"modals.less\",\"dropdowns.less\",\"accordion.less\",\"carousel.less\",\"wells.less\",\"close.less\",\"utilities.less\",\"component-animations.less\",\"responsive-utilities.less\",\"responsive-767px-max.less\",\"responsive-768px-979px.less\",\"responsive-1200px-min.less\",\"responsive-navbar.less\"]"));
		params.add(new BasicNameValuePair("js", "[\"reset.less\",\"scaffolding.less\",\"grid.less\",\"layouts.less\",\"type.less\",\"code.less\",\"labels-badges.less\",\"tables.less\",\"forms.less\",\"buttons.less\",\"sprites.less\",\"button-groups.less\",\"navs.less\",\"navbar.less\",\"breadcrumbs.less\",\"pagination.less\",\"pager.less\",\"thumbnails.less\",\"alerts.less\",\"progress-bars.less\",\"hero-unit.less\",\"media.less\",\"wells.less\",\"close.less\",\"utilities.less\",\"component-animations.less\",\"responsive-utilities.less\",\"responsive-767px-max.less\",\"responsive-768px-979px.less\",\"responsive-1200px-min.less\",\"responsive-navbar.less\"]"));
		params.add(new BasicNameValuePair("vars", "{}"));
		params.add(new BasicNameValuePair("img", "[\"glyphicons-halflings.png\",\"glyphicons-halflings-white.png\"]"));
		httppost.setEntity(new UrlEncodedFormEntity(params));
		//Execute and get the response.
		HttpResponse response = httpclient.execute(httppost);
		
		HttpEntity entity = response.getEntity();

		if (entity != null) {
		    byte[] buffer = new byte[1024];
		    
		    try {
		    	ZipInputStream zis = new ZipInputStream( entity.getContent() );
		    	//get the zipped file list entry
		    	ZipEntry ze = zis.getNextEntry(); 
		 
		    	while(ze!=null){
		 
		    	   String fileName = ze.getName();
		    	   if(!fileName.matches(".*bootstrap\\.css")) {
		    		   ze = zis.getNextEntry();
		    		   continue;
		    	   }
		    	   String[] segments = fileName.split(File.separator);
		    	   fileName = segments[segments.length-1];
		           File newFile = new File("/home/hesham/Desktop/tst" + File.separator + fileName);
		 
		           System.out.println("file unzip : "+ newFile);
		 
		            FileOutputStream fos = new FileOutputStream(newFile);             
		 
		            int len;
		            while ((len = zis.read(buffer)) > 0) fos.write(buffer, 0, len);
		            
		            fos.close();   
		            ze = zis.getNextEntry();
		    	} 
		 
		        zis.closeEntry();
		    	zis.close();
		 
		    	System.out.println("Done");
		 
		    }catch(IOException ex){
		       ex.printStackTrace(); 
		    } 
		}

	}
}
