package edu.asu.krypton.model.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import edu.asu.krypton.model.persist.db.Article;

@Repository
public class ArticleRepository extends edu.asu.krypton.model.repository.Repository<Article> {

	public ArticleRepository(){
		setPersistentClass(Article.class);
	}
	
	public boolean insertOrUpdateArticle(Article article){
		try{
			mongoTemplate.save(article);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	public boolean deleteArticle(Article article){
		try{
			mongoTemplate.remove(article);
			return true;
		}catch(Exception e){
			return false;
		}
	}
		
	public Article findArticleByTitle(String title) {
		Query query = new Query();
		query.addCriteria(Criteria.where("title").is(title));
		return mongoTemplate.findOne(query, Article.class);
	}
	
	public List<Article> getAllArticles() {
		return mongoTemplate.findAll(Article.class);
	}
	

	public List<Article> getObsoleteArticles(){
		Query query = new Query();
		query.addCriteria(Criteria.where("obsolete").is(true));
		return mongoTemplate.find(query, Article.class);
	}

	public Article getArticleByID(String id){
		return mongoTemplate.findById(id, Article.class);
	}
	
	public int getArticleLength(BigInteger articleID){
		Article article = getArticleByID(articleID.toString(16));
		System.out.println("-------------------");
		System.out.println(articleID);
		System.out.println("-------------------");
		return article.getContent().length() + article.getTitle().length() + article.getDescription().length();
	}

	public Article findHomeArticle() {
		Query query = new Query();
		query.addCriteria(Criteria.where("home").is(true));
		return mongoTemplate.findOne(query, getPersistentClass());
	}
}
