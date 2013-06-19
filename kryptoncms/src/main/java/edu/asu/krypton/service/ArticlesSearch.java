package edu.asu.krypton.service;

/**
 * 
 * @author Mostafa
 * malhash ay 3 lazmah
 */
//@Service
public class ArticlesSearch {

//	@Autowired
//	private static ArticleRepository articleRepository;
//
//	public ArticlesSearch() {
//		super();
//	}
//	
//	public static ArticleRepository getArticleRepository() {
//		return articleRepository;
//	}
//
//	public static void setArticleRepository(ArticleRepository articleRepository) {
//		ArticlesSearch.articleRepository = articleRepository;
//	}
//
//	public static Article searchByTitle(String title) {
//
//		try {
//			Article article = articleRepository.findArticleByTitle(title);
//			return article;
//		} catch (Exception e) {
//			return null;
//		}
//	}
//
//	public static List<Article> searchByAContainingWordOrPhrase(String phrase) {
//		List<Article> articles = null;
//		try {
//			articles = articleRepository.getAllArticles();
//			for (Article article : articles) {
//				if ((!article.getTitle().contains(phrase))
//						&& (!article.getContent().contains(phrase))
//						&& (!article.getDescription().contains(phrase)))
//					articles.remove(article);
//			}
//			return articles;
//		} catch (Exception e) {
//			return null;
//		}
//	}

	/**
	 * 
	 * @param phrase : to be searched for in all articles
	 * @param phraseExistencesReturned : an empty list of String to be filled by the method with the places where occurences of phrase in each of the articles returned:-
	 * - "InTitle" : phrase occured in article's title only
	 * - "InDescription" : phrase occured in article's content body only 
	 * - "InContent" : phrase occured in article's description only
	 * - "InTitle,InContent" : phrase occured in article's title and content only
	 * - "InContent,InDescription" : phrase occured in article's content and description only
	 * - "InTitle,InDescription" : phrase occured in article's title and description only
	 * - "InTitle,InContent,InDescription" : phrase occured in article's title, content and description
	 *  this list is in the same order with their corresponding articles that are returned in the SmartMap object
 	 * @return returns a SmartMap object that contains list of articles only that contains this phrase and their corresponding number of occurences as an Integer object 
	 *
	 */
//	public static SmartMap<Article, Integer> smartSearch(String phrase,
//			List<String> phraseExistencesReturned) {
//		try {
//			SmartMap<Article, Integer> articles = ArticleSearchStatistics
//					.applySearchStatistics(phrase, phraseExistencesReturned,
//							searchByAContainingWordOrPhrase(phrase));
//			return articles;
//		} catch (Exception e) {
//			return null;
//		}
//	}

}
