package edu.asu.krypton.model.repository;

import org.springframework.stereotype.Repository;

import edu.asu.krypton.model.persist.db.Article;

@Repository
public class ArticleRepository extends edu.asu.krypton.model.repository.Repository<Article> {

	public ArticleRepository(){
		setPersistentClass(Article.class);
	}
	
//	
//	@Autowired(required=true)
//	private DataAccessObject<Article> dao;
//	private static final Logger logger = LoggerFactory.getLogger(ArticleRepository.class);
//	public boolean insertOrUpdateArticle(Article article){
//		try{
//			dao.getSession().saveOrUpdate(article);
//			return true;
//		}catch(TransactionException ex){
//			dao.getSession().getTransaction().rollback();
//			return true;
//		}catch(HibernateException ex){
//			return false;
//		}
//	}
//	
//	public boolean deleteArticle(Article article){
//		try{
//			dao.getSession().delete(article);
//			return true;
//		}catch(TransactionException ex){
//			dao.getSession().getTransaction().rollback();
//			return true;
//		}catch(HibernateException ex){
//			return false;
//		}
//	}
//	
//	public Article findSimillarArticle(Article exampleArticle) {
//		Article article = null;
//		try{
//			Criteria criteria = dao.getSession().createCriteria(Article.class);
//			criteria.add(Example.create(exampleArticle)).setMaxResults(1);
//			article = (Article) criteria.uniqueResult();
//		}catch(HibernateException ex){
//			logger.error("Hibernate Exception was thrown "+ex.getMessage());
//		}
//		return article;
//	}
//	
//	public Article findArticleByTitle(String title) {
//		Article article = null;
//		try{
//			Criteria criteria = dao.getSession().createCriteria(Article.class);
//			criteria.add(Restrictions.eq("title", title));
//			article = (Article) criteria.list();
//		}catch(HibernateException ex){
//			logger.error("Hibernate Exception was thrown "+ex.getMessage());
//		}
//		return article;
//	}
//	
//	@SuppressWarnings("unchecked")
//	public List<Article> getAllArticles() {
//		List<Article> allArticles = null;
//		try
//		{
//			Criteria criteria = dao.getSession().createCriteria(Article.class);
//			allArticles = (List<Article>)criteria.list();
//		}
//		catch(HibernateException e) {
//			logger.error("Hibernate Exception was thrown "+e.getMessage());
//		}
//		return allArticles;
//	}
//
//	@SuppressWarnings("unchecked")
//	public List<Comment> getArticleComments(Article article) {
//		List<Comment> comments = null;
//		try
//		{
//			Criteria criteria = dao.getSession().createCriteria(Comment.class);
//			criteria.add(Restrictions.eq("article", article));
//			comments = (List<Comment>)criteria.list();
//		}
//		catch(HibernateException e) {
//			logger.error("Hibernate Exception was thrown "+e.getMessage());
//		}
//		return comments;
//	}

}
