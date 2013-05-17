package edu.asu.krypton.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.krypton.exceptions.CustomRuntimeException;
import edu.asu.krypton.model.persist.db.Article;
import edu.asu.krypton.model.repository.ArticleRepository;
import edu.asu.krypton.model.repository.CommentRepository;

@Service
public class ArticleService extends edu.asu.krypton.service.CommentableService<Article> 
{

	@Override
	public Article findById(Serializable id){
		return repository.findById(id);
	}
	
	@Override
	public void saveOrUpdate(Article entity) {
		try {
			repository.saveOrUpdate(entity);
		} catch (CustomRuntimeException e) {
			e.printStackTrace();
		}
	}
	
	public List<Article> getAll(){
		return repository.getAll();
	}
	
	
	public ArticleRepository getArticleRepository() {
		return (ArticleRepository) repository;
	}

	public void setArticleRepository(ArticleRepository articleRepository) {
		this.repository = articleRepository;
	}
	
	@Autowired(required=true)
	public void setRepository(ArticleRepository repository){
		this.repository = repository;
	}
}
