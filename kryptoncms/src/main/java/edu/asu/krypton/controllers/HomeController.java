package edu.asu.krypton.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

import edu.asu.krypton.model.persist.db.Article;
import edu.asu.krypton.service.ArticleService;

@Controller
@RequestMapping(value = "/")
public class HomeController extends edu.asu.krypton.controllers.Controller {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	private final String DEFAULT_VIEW = "home";
	private final String DEFAULT_BODY_DIR = "bodies/";
	private final String ARTICLE_VIEW = "article";
	
	@Autowired(required=true)
	private ArticleService articleService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String defaultView(ModelMap model,HttpServletRequest request) throws IOException, NoSuchRequestHandlingMethodException {
		Article home = articleService.findHomeArticle();
		if(home==null) throw new NoSuchRequestHandlingMethodException(request);
		model.addAttribute("article", home);
		return appropriateView(request, DEFAULT_BODY_DIR+ARTICLE_VIEW, defaultView(model,ARTICLE_VIEW));
	}
	
}