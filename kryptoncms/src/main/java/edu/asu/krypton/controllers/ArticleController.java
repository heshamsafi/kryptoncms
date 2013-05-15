package edu.asu.krypton.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

import edu.asu.krypton.model.ArticleSubmitMessage;
import edu.asu.krypton.model.message_proxies.Message;
import edu.asu.krypton.model.persist.db.Article;
import edu.asu.krypton.service.ArticleService;

@Controller
@RequestMapping(value="/article")
public class ArticleController extends edu.asu.krypton.controllers.Controller {
	
	private final String DEFAULT_VIEW = "article";
	private final String EDIT_VIEW = "article_edit";
	private final String DEFAULT_DIR = "bodies/";
	
	@Autowired(required=true)
	private ArticleService articleService;
	
	@RequestMapping(method=RequestMethod.GET,value="edit")
	public String getHome(HttpServletRequest request,Model model){
		return getHome(null, request, model);
	}
	@RequestMapping(method=RequestMethod.GET,value="edit/{id}")
	public String getHome(@PathVariable String id,HttpServletRequest request,Model model){
		if(id != null){
			Article article= articleService.findById(id);
			model.addAttribute("article", article);
		}
		return appropriateView(request, DEFAULT_DIR+EDIT_VIEW, defaultView(model,EDIT_VIEW));
	}
	
	
	@RequestMapping(value = "edit",method = RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Message saveOrUpdate(@RequestBody Article article)
	{	
		ArticleSubmitMessage message = new ArticleSubmitMessage();
		try{
			articleService.saveOrUpdate(article);	
			return message.setId(article.getId()).setSuccessful(true);
		}catch (Exception e) {
			return message.setSuccessful(false);
		}
	}
	
	
	@RequestMapping(value = "edit/{id}",method = RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Message saveOrUpdate(@PathVariable String id,@RequestBody Article article)throws Exception
	{	
		article.setId(id);
		return saveOrUpdate(article);
	}
	
	@RequestMapping(value="{id}",method=RequestMethod.GET)
	public String getArticle(@PathVariable String id,HttpServletRequest request,Model model) throws NoSuchRequestHandlingMethodException{
		Article article = articleService.findById(id);
		if(article == null) throw new NoSuchRequestHandlingMethodException(request);
//		Long nextId = articleService.findById(id+1) == null?0:id+1;
//		Long prevId = articleService.findById(id-1) == null?0:id-1;
		
		model.addAttribute("article", article)
//			 .addAttribute("nextId", nextId)
//			 .addAttribute("prevId", prevId)
			 ;
		return appropriateView(request, DEFAULT_DIR+DEFAULT_VIEW, defaultView(model,DEFAULT_VIEW));
	}
//	@RequestMapping(value="",method=RequestMethod.GET)
//	public String getDefaultArticle(HttpServletRequest request,Model model) throws NoSuchRequestHandlingMethodException{
//		return getArticle(new Long(1), request, model);
//	}
	
}
