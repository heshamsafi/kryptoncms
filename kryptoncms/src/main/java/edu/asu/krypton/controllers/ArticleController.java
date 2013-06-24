package edu.asu.krypton.controllers;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

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
import edu.asu.krypton.sourceControl.helperClass;
import edu.asu.krypton.sourceControl.difflib.DiffUtils;
import edu.asu.krypton.sourceControl.difflib.Patch;
import edu.asu.krypton.sourceControl.difflib.PatchFailedException;

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
	public String getHome(HttpServletRequest request,Model model) throws IOException, ClassNotFoundException, PatchFailedException, NoSuchRequestHandlingMethodException{
		return getHome(null,null, request, model);
	}
	
	
	@RequestMapping(method=RequestMethod.GET,value="edit/{title}/{version}")
	public String getHome(@PathVariable String title,@PathVariable String version,HttpServletRequest request,Model model) throws IOException, ClassNotFoundException, PatchFailedException, NoSuchRequestHandlingMethodException{
		if(title != null){
			
			//Article article= articleService.findById(id);
			Article article= articleService.findByTitle(title);
			ArrayList<Patch<String>>patches;
			if(article.getPatches()==null){
				model.addAttribute("article", article);
			}
			else{
				String patchesString=article.getPatches();
				patches=(ArrayList<Patch<String>>) helperClass.fromString(patchesString);
			
			
			
				StringTokenizer original=new StringTokenizer(article.getContent(),"\n");
				
				List<String>originalTokens=new ArrayList<String>();
				
				while(original.hasMoreTokens()){
					originalTokens.add(original.nextToken());
				}
				
				int counter=0;
				
				int ver=Integer.parseInt(version);
				if(patches.size()<ver){
					//el version mesh mowgowda
					throw new NoSuchRequestHandlingMethodException(request);
//					return appropriateView(request, DEFAULT_DIR+EDIT_VIEW, defaultView(model,EDIT_VIEW));
				}
				List<String>Result=originalTokens;
				for(Patch<String>patch:patches){
					if(counter==ver){
						break;
					}
					Result=DiffUtils.patch(Result, patch);
					System.out.println(patches);
					counter++;
				}
				
				String content= "";

				for (String s : Result)
				{
				    content += s + "\n";
				}
				
				article.setContent(content);	
				
				
				model.addAttribute("article", article);
			}
			
			
		}
		return appropriateView(request, DEFAULT_DIR+EDIT_VIEW, defaultView(model,EDIT_VIEW));
	}
	
	
	@RequestMapping(value = "edit",method = RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Message saveOrUpdate(@RequestBody Article article)
	{	
		ArticleSubmitMessage message = new ArticleSubmitMessage();
		try{
			Article articleOld=null;
			if(article.getId()!=null){
				articleOld=articleService.findById(article.getId());
			}
			else if(articleService.findByTitle(article.getTitle())!=null){
				articleOld=articleService.findByTitle(article.getTitle());
			}
			if(articleOld!=null)
			{
				
				
			
			
				ArrayList<Patch<String>>patches;
				if(articleOld.getPatches()==null){
					patches=new ArrayList<Patch<String>>();
				}
				else{
					String patchesString=articleOld.getPatches();
					patches=(ArrayList<Patch<String>>) helperClass.fromString(patchesString);
				}
				
				StringTokenizer original=new StringTokenizer(articleOld.getContent(),"\n");
				StringTokenizer revised=new StringTokenizer(article.getContent(),"\n");
				
				List<String>originalTokens=new ArrayList<String>();
				List<String>revisedTokens=new ArrayList<String>();
				
				while(original.hasMoreTokens()){
					originalTokens.add(original.nextToken());
				}
					
				while(revised.hasMoreTokens()){
					revisedTokens.add(revised.nextToken());
				}
				
				
				List<String>Result=originalTokens;
				for(Patch<String>patch:patches){
					Result=DiffUtils.patch(Result, patch);
					System.out.println(patches);
				}
				
				patches.add(DiffUtils.diff(Result, revisedTokens));
				article.setPatches(helperClass.toString(patches));
				
				
				
				int ver=Integer.parseInt(articleOld.getVersion());
				ver++;
				article.setVersion(new String(Integer.toString(ver)));
				
				
				article.setContent(articleOld.getContent());
				
				article.setId(articleOld.getId());
				message.setVersion(article.getVersion());
				
			}
			else{
				article.setVersion("0");
				message.setVersion(new String("0"));
			}
			article.reIndex = true;
			articleService.saveOrUpdate(article);	

//			return message.setId(article.getId()).setSuccessful(true);
			return message.setTitle(article.getTitle()).setSuccessful(true);
		}catch (Exception e) {
			logger.debug(article.toString());
			e.printStackTrace();
			return message.setSuccessful(false);
		}
	}
	
	
	@RequestMapping(value = "edit/{title}",method = RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Message saveOrUpdate(@PathVariable String title,@RequestBody Article article)throws Exception
	{	
		article.setTitle(title);
//		article.setId(article.getId());
		return saveOrUpdate(article);
	}
	
	
	
	@RequestMapping(value="{title}",method=RequestMethod.GET)
	public String getArticle(@PathVariable String title,HttpServletRequest request,Model model) throws NoSuchRequestHandlingMethodException{
		Article article = articleService.findByTitle(title);
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
			System.out.println("Searching for " + phrase + "...");
			List<BigInteger> articlesIDs = articleService.search(phrase);
			if(articlesIDs == null || articlesIDs.size()==0)
				return "<h3>No results exist for " + phrase + "</h3>";
			String content = "<h3>Search results: " + articlesIDs.size() + " articles found for \"" + phrase + "\"</h3>";
			content += "<div style=\"margin-left:25px\">";
			List<Article> articles = new ArrayList<Article>();
			for(BigInteger id : articlesIDs){
				articles.add(articleRepository.getArticleByID(id.toString(16)));
			}
			
			for(Article article : articles) {
				content += "<div>";
				content += "<a href=\"/kryptoncms/article/"+ article.getTitle() + "\">";
				content += "<h3>"+article.getTitle()+"</h3>";
				content += "</a>";
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
