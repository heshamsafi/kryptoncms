package edu.asu.krypton.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.krypton.exceptions.CustomRuntimeException;
import edu.asu.krypton.model.persist.db.Article;
import edu.asu.krypton.model.repository.ArticleRepository;

@Service
public class ArticleService extends edu.asu.krypton.service.CommentableService<Article> 
{
	
	@Autowired(required=true)
	private ArticleRepository articleRepository;

	@SessionDependant
	@Override
	public Article findById(Serializable id){
		return articleRepository.findById(id);
	}
	
	@Override
	@SessionDependant
	public void saveOrUpdate(Article entity) {
		try {
			articleRepository.saveOrUpdate(entity);
		} catch (CustomRuntimeException e) {
			e.printStackTrace();
		}
	}
	
	@SessionDependant
	public List<Article> getAll(){
		return articleRepository.getAll();
	}
	
	
	public ArticleRepository getArticleRepository() {
		return articleRepository;
	}

	public void setArticleRepository(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}
}
