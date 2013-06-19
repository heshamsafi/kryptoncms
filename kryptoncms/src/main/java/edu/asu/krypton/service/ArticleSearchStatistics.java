package edu.asu.krypton.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import edu.asu.krypton.model.persist.db.Article;
import edu.asu.krypton.utilities.SmartMap;

@Service
public class ArticleSearchStatistics {
	
	public ArticleSearchStatistics() {
		super();
	}

	public static SmartMap<Article, Integer> applySearchStatistics(
			String phrase, List<String> phraseExistencesReturned,
			List<Article> articles) {
		SmartMap<Article, Integer> articlesWithStatistics = new SmartMap<Article, Integer>();
		try {
			int numberOfOccurences = 0;
			boolean foundInTitle = false;
			boolean foundInContent = false;
			boolean foundInDescription = false;
			for (Article article : articles) {
				if (article.getTitle().contains(phrase)) {
					numberOfOccurences = StringUtils.countOccurrencesOf(article.getTitle(), phrase);
					foundInTitle = true;
				}
				if (article.getContent().contains(phrase)) {
					numberOfOccurences += StringUtils.countOccurrencesOf(article.getContent(), phrase);
					foundInContent = true;
				}
				if (article.getDescription().contains(phrase)) {
					numberOfOccurences += StringUtils.countOccurrencesOf(article.getDescription(), phrase);
					foundInDescription = true;
				}
				articlesWithStatistics.put(new Integer(numberOfOccurences),article);
				if(foundInTitle)
					phraseExistencesReturned.add("InTitle");
				else if(foundInContent)
					phraseExistencesReturned.add("InContent");
				else if(foundInDescription)
					phraseExistencesReturned.add("InDescription");
				if(foundInContent && foundInTitle)
					phraseExistencesReturned.add("InTitle,InContent");
				else if(foundInContent && foundInDescription)
					phraseExistencesReturned.add("InContent,InDescription");
				else if(foundInTitle && foundInDescription)
					phraseExistencesReturned.add("InTitle,InDescription");
				if(foundInContent && foundInTitle && foundInDescription)
					phraseExistencesReturned.add("InTitle,InContent,InDescription");
				foundInContent = false;
				foundInTitle = false;
				foundInDescription = false;
				numberOfOccurences = 0;
			}
			return articlesWithStatistics;
		} catch (Exception e) {
			return null;
		}
	}
}
