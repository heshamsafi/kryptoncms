package edu.asu.krypton.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

import edu.asu.krypton.model.ArticleSubmitMessage;
import edu.asu.krypton.model.message_proxies.Message;
import edu.asu.krypton.model.persist.db.Article;
import edu.asu.krypton.model.repository.ArticleRepository;
import edu.asu.krypton.service.ArticleService;

@Controller
@RequestMapping(value="/article")
public class ArticleController extends edu.asu.krypton.controllers.Controller {
	
	private final String DEFAULT_VIEW = "article";
	private final String EDIT_VIEW = "article_edit";
	private final String DEFAULT_DIR = "bodies/";
	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ArticleController.class);
	
	@Autowired(required=true)
	private ArticleService articleService;
	
	@Autowired(required=true)
	private ArticleRepository articleRepository;
	
	@Autowired(required=true)
	private MongoTemplate mongoTemplate;
	
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
			logger.debug(article.toString());
			e.printStackTrace();
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
		Article nextArticle = mongoTemplate.findOne(new Query().addCriteria(Criteria.where("date").gt(article.getDate())), Article.class);
		Article prevArticle = mongoTemplate.findOne(new Query().addCriteria(Criteria.where("date").lt(article.getDate())), Article.class);
		String nextId = nextArticle == null?null:nextArticle.getId();
		String prevId = prevArticle == null?null:prevArticle.getId();
		;
		model.addAttribute("article", article)
			 .addAttribute("nextId", nextId)
			 .addAttribute("prevId", prevId)
			 ;
		return appropriateView(request, DEFAULT_DIR+DEFAULT_VIEW, defaultView(model,DEFAULT_VIEW));
	}
	@RequestMapping(value="",method=RequestMethod.GET)
	public String getDefaultArticle(HttpServletRequest request,Model model) throws NoSuchRequestHandlingMethodException{
		return getArticle(mongoTemplate.findOne(new Query(),Article.class).getId(), request, model);
	}
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public @ResponseBody String getCertainArticle(@RequestParam("phrase") String phrase) throws Exception {
		try{
			List<Long> articlesIDs = articleService.search(phrase);
			if(articlesIDs == null || articlesIDs.size()==0)
				return "<h3>No results exist for " + phrase + "</h3>";
			String content = "<h3>Search results: " + articlesIDs.size() + " articles found for \"" + phrase + "\"</h3>";
			content += "<div style=\"margin-left:25px\">";
			List<Article> articles = new ArrayList<Article>();
			for(Long id : articlesIDs){
				articles.add(articleRepository.getArticleByID(id));
			}
			for(Article article : articles) {
				content += "<div>";
				content += "<h3>"+article.getTitle()+"</h3>";
				content += article.getContent();
				content += "</div>";
			}
			content += "</div><br>";
			return content;
		}
		catch(Exception e) {
			e.printStackTrace();
			return "<h3>Not finished yet</h3>";
		}
	}
	
}
