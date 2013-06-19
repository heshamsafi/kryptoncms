package edu.asu.krypton.service;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.krypton.exceptions.CustomRuntimeException;
import edu.asu.krypton.model.persist.db.Article;
import edu.asu.krypton.model.persist.db.IndexArticleStatistics;
import edu.asu.krypton.model.persist.db.IndexInArticleContentPlaces;
import edu.asu.krypton.model.persist.db.IndexInArticleDescriptionPlaces;
import edu.asu.krypton.model.persist.db.IndexInArticleTitlePlaces;
import edu.asu.krypton.model.persist.db.Indices;
import edu.asu.krypton.model.repository.ArticleRepository;
import edu.asu.krypton.model.repository.IndexRepository;
import edu.asu.krypton.utilities.SmartMap;

@Service
public class ArticleService extends edu.asu.krypton.service.CommentableService<Article> 
{
	@Autowired
	private ArticleRepository articleRepository;
	@Autowired
	private IndexRepository indexRepository;

	public ArticleService() {
		super();
	}
	
	public IndexRepository getIndexRepository() {
		return indexRepository;
	}

	public void setIndexRepository(IndexRepository indexRepository) {
		this.indexRepository = indexRepository;
	}
	
	@Override
	public Article findById(Serializable id){
		return repository.findById(id);
	}
	
	@Override
	public void saveOrUpdate(Article entity) {
			repository.saveOrUpdate(entity);
//			if(! entity.isObsolete())
//				refreshIndexTable(entity);
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
	
	private String extractValidSearchTextfromContent(String content) {
		char[] chars = content.toCharArray();
		int length = content.length();
		char[] returnChars = new char[length];
		for (int i = 0; i < length; i++)
			returnChars[i] = '<';
		for (int i = 0; i < length - 1; i++) {
			if (chars[i] == '<') {
				while (chars[i] != '>') {
					if (chars[i] == ' ' || chars[i] == '\n' || chars[i] == '\t'
							|| chars[i] == '\r' || chars[i] == '\f')
						returnChars[i] = chars[i];
					i++;
				}
				continue;
			}
			returnChars[i] = chars[i];
		}
		return new String(returnChars);
	}
	
	private void refreshIndexTable(Article article) {
		// parsing
		List<String> titleWords = new ArrayList<String>();
		StringTokenizer tokenizer = new StringTokenizer(article.getTitle());
		while (tokenizer.hasMoreTokens())
			titleWords.add(tokenizer.nextToken());
		List<String> descriptionWords = new ArrayList<String>();
		tokenizer = new StringTokenizer(article.getDescription());
		while (tokenizer.hasMoreTokens())
			descriptionWords.add(tokenizer.nextToken());
		List<String> contentWords = new ArrayList<String>();
		tokenizer = new StringTokenizer(
				extractValidSearchTextfromContent(article.getContent()),"< \n\t\f\r");
		while (tokenizer.hasMoreTokens())
			contentWords.add(tokenizer.nextToken());
		// indexing
		for (String word : titleWords) {
			Indices index = indexRepository.getIndexByWord(word);
			if (index == null) {
				// A new index , add it in the index table and calculate its
				// statistics
				int numberOfOccurencesInTitle = 1;
				index = new Indices();
				index.setWord(word);
				indexRepository.insertOrUpdateIndex(index);
				IndexArticleStatistics indexStatistics = new IndexArticleStatistics();
				// indexStatistics.setArticle(article);
				indexStatistics.setArticleNumber(toByteArray(new Long(article.getId())));
				indexStatistics.setIndex(index);
				indexStatistics
						.setNumberOfOccurencesInTitle(toByteArray(new Long(
								numberOfOccurencesInTitle)));
				IndexInArticleTitlePlaces indexInArticleTitlePlaces = new IndexInArticleTitlePlaces();
				indexInArticleTitlePlaces.setPlace(toByteArray(new Long(
						titleWords.indexOf(word))));
				indexInArticleTitlePlaces
						.setIndexArticleStatistics(indexStatistics);
				// saveOrUpdate indexStatistics
				indexRepository.insertOrUpdateIndexArticleStatistics(
						indexStatistics, false);
				// saveOrUpdate indexInArticleTitlePlaces
				indexRepository
						.insertOrUpdateIndexInArticleTitlePlaces(indexInArticleTitlePlaces);
			} else {
				// the index already exists , so only refresh its statistics
				// get the indexArticleStatistics record whose article = this
				// article and whose index = this index
				IndexArticleStatistics indexStatistics = indexRepository
						.findIndexArticleStatisticsRecordByArticleAndIndex(
								article, index);
				if (indexStatistics == null) {
					// if index exists but has no information yet about this
					// article
					indexStatistics = new IndexArticleStatistics();
					// indexStatistics.setArticle(article);
					indexStatistics.setArticleNumber(toByteArray(new Long(article
							.getId())));
					indexStatistics.setIndex(index);
					indexStatistics
							.setNumberOfOccurencesInTitle(toByteArray(new Long(
									1)));
					IndexInArticleTitlePlaces indexInArticleTitlePlaces = new IndexInArticleTitlePlaces();
					indexInArticleTitlePlaces
							.setIndexArticleStatistics(indexStatistics);
					indexInArticleTitlePlaces.setPlace(toByteArray(new Long(
							titleWords.indexOf(word))));
					// saveOrUpdate the record
					indexRepository.insertOrUpdateIndexArticleStatistics(
							indexStatistics, true);
					indexRepository
							.insertOrUpdateIndexInArticleTitlePlaces(indexInArticleTitlePlaces);
				} else {
					// update its numberOfOccyrencesInTitle and its places in
					// title also
					indexStatistics
							.setNumberOfOccurencesInTitle(toByteArray(new Long(
									toLong(indexStatistics
											.getNumberOfOccurencesInTitle()) + 1)));
					IndexInArticleTitlePlaces indexInArticleTitlePlaces = new IndexInArticleTitlePlaces();
					indexInArticleTitlePlaces.setPlace(toByteArray(new Long(
							titleWords.indexOf(word))));
					indexInArticleTitlePlaces
							.setIndexArticleStatistics(indexStatistics);
					// saveOrUpdate the record
					indexRepository.insertOrUpdateIndexArticleStatistics(
							indexStatistics, true);
					indexRepository
							.insertOrUpdateIndexInArticleTitlePlaces(indexInArticleTitlePlaces);
				}
			}
		}

		for (String word : contentWords) {
			Indices index = indexRepository.getIndexByWord(word);
			if (index == null) {
				// A new index , add it in the index table and calculate its
				// statistics
				int numberOfOccurencesInContent = 1;
				index = new Indices();
				index.setWord(word);
				indexRepository.insertOrUpdateIndex(index);
				IndexArticleStatistics indexStatistics = new IndexArticleStatistics();
				// indexStatistics.setArticle(article);
				indexStatistics.setArticleNumber(toByteArray(new Long(article.getId())));
				indexStatistics.setIndex(index);
				indexStatistics
						.setNumberOfOccurencesInContent(toByteArray(new Long(
								numberOfOccurencesInContent)));
				IndexInArticleContentPlaces indexInArticleContentPlaces = new IndexInArticleContentPlaces();
				indexInArticleContentPlaces.setPlace(toByteArray(new Long(
						contentWords.indexOf(word))));
				indexInArticleContentPlaces
						.setIndexArticleStatistics(indexStatistics);
				// saveOrUpdate indexStatistics
				indexRepository.insertOrUpdateIndexArticleStatistics(
						indexStatistics, false);
				// saveOrUpdate indexInArticleContentPlaces
				indexRepository
						.insertOrUpdateIndexInArticleContentPlaces(indexInArticleContentPlaces);
				//System.out.println(word);
			} else {
				// the index already exists , so only refresh its statistics
				// get the indexArticleStatistics record whose article = this
				// article and whose index = this index
				IndexArticleStatistics indexStatistics = indexRepository
						.findIndexArticleStatisticsRecordByArticleAndIndex(
								article, index);
				if (indexStatistics == null) {
					System.out.println(index.getWord());
					System.out.println(new Long(article.getId()).longValue());
					// if index exists but has no information yet about this
					// article
					indexStatistics = new IndexArticleStatistics();
					// indexStatistics.setArticle(article);
					indexStatistics.setArticleNumber(toByteArray(new Long(article
							.getId())));
					indexStatistics.setIndex(index);
					indexStatistics
							.setNumberOfOccurencesInContent(toByteArray(new Long(
									1)));
					IndexInArticleContentPlaces indexInArticleContentPlaces = new IndexInArticleContentPlaces();
					indexInArticleContentPlaces
							.setIndexArticleStatistics(indexStatistics);
					indexInArticleContentPlaces.setPlace(toByteArray(new Long(
							contentWords.indexOf(word))));
					// saveOrUpdate the record
					indexRepository.insertOrUpdateIndexArticleStatistics(
							indexStatistics, true);
					System.out.println(toLong(indexStatistics.getArticleNumber()));
					indexRepository
							.insertOrUpdateIndexInArticleContentPlaces(indexInArticleContentPlaces);
				} else {
					// update its numberOfOccyrencesInContent and its places in
					// content also
					indexStatistics
							.setNumberOfOccurencesInContent(toByteArray(new Long(
									toLong(indexStatistics
											.getNumberOfOccurencesInContent()) + 1)));
					IndexInArticleContentPlaces indexInArticleContentPlaces = new IndexInArticleContentPlaces();
					indexInArticleContentPlaces.setPlace(toByteArray(new Long(
							contentWords.indexOf(word))));
					indexInArticleContentPlaces
							.setIndexArticleStatistics(indexStatistics);
					// saveOrUpdate the record
					indexRepository.insertOrUpdateIndexArticleStatistics(
							indexStatistics, true);
					indexRepository
							.insertOrUpdateIndexInArticleContentPlaces(indexInArticleContentPlaces);
				}
			}

		}
		for (String word : descriptionWords) {
			Indices index = indexRepository.getIndexByWord(word);
			if (index == null) {
				// A new index , add it in the index table and calculate its
				// statistics
				int numberOfOccurencesInDescription = 1;
				index = new Indices();
				index.setWord(word);
				indexRepository.insertOrUpdateIndex(index);
				IndexArticleStatistics indexStatistics = new IndexArticleStatistics();
				// indexStatistics.setArticle(article);
				indexStatistics.setArticleNumber(toByteArray(new Long(article.getId())));
				indexStatistics.setIndex(index);
				indexStatistics
						.setNumberOfOccurencesInDescription(toByteArray(new Long(
								numberOfOccurencesInDescription)));
				IndexInArticleDescriptionPlaces indexInArticleDescriptionPlaces = new IndexInArticleDescriptionPlaces();
				indexInArticleDescriptionPlaces.setPlace(toByteArray(new Long(
						descriptionWords.indexOf(word))));
				indexInArticleDescriptionPlaces
						.setIndexArticleStatistics(indexStatistics);
				// saveOrUpdate indexStatistics
				indexRepository.insertOrUpdateIndexArticleStatistics(
						indexStatistics, false);
				// saveOrUpdate indexInArticleDescriptionPlaces
				indexRepository
						.insertOrUpdateIndexInArticleDescriptionPlaces(indexInArticleDescriptionPlaces);
			} else {
				// the index already exists , so only refresh its statistics
				// get the indexArticleStatistics record whose article = this
				// article and whose index = this index
				IndexArticleStatistics indexStatistics = indexRepository
						.findIndexArticleStatisticsRecordByArticleAndIndex(
								article, index);
				if (indexStatistics == null) {
					// if index exists but has no information yet about this
					// article
					indexStatistics = new IndexArticleStatistics();
					// indexStatistics.setArticle(article);
					indexStatistics.setArticleNumber(toByteArray(new Long(article
							.getId())));
					indexStatistics.setIndex(index);
					indexStatistics
							.setNumberOfOccurencesInDescription(toByteArray(new Long(
									1)));
					IndexInArticleDescriptionPlaces indexInArticleDescriptionPlaces = new IndexInArticleDescriptionPlaces();
					indexInArticleDescriptionPlaces
							.setIndexArticleStatistics(indexStatistics);
					indexInArticleDescriptionPlaces
							.setPlace(toByteArray(new Long(descriptionWords
									.indexOf(word))));
					// saveOrUpdate the record
					indexRepository.insertOrUpdateIndexArticleStatistics(
							indexStatistics, true);
					indexRepository
							.insertOrUpdateIndexInArticleDescriptionPlaces(indexInArticleDescriptionPlaces);
				} else {
					// update its numberOfOccyrencesInDescription and its places
					// in
					// description also
					indexStatistics
							.setNumberOfOccurencesInDescription(toByteArray(new Long(
									toLong(indexStatistics
											.getNumberOfOccurencesInDescription()) + 1)));
					IndexInArticleDescriptionPlaces indexInArticleDescriptionPlaces = new IndexInArticleDescriptionPlaces();
					indexInArticleDescriptionPlaces
							.setPlace(toByteArray(new Long(descriptionWords
									.indexOf(word))));
					indexInArticleDescriptionPlaces
							.setIndexArticleStatistics(indexStatistics);
					// saveOrUpdate the record
					indexRepository.insertOrUpdateIndexArticleStatistics(
							indexStatistics, true);
					indexRepository
							.insertOrUpdateIndexInArticleDescriptionPlaces(indexInArticleDescriptionPlaces);
				}
			}
		}
		// set score
		for (Indices index : indexRepository.getAllIndices()) {
			List<IndexArticleStatistics> indexStatisticsList = indexRepository
					.findIndexArticleStatisticsRecordsByIndex(index);
			//System.out.println(index.getWord());
			int numberOfOccurenceInAllArticle = indexStatisticsList.size();
			int numberOfAllArticles = articleRepository.getAllArticles().size();
			for (IndexArticleStatistics indexStatistics : indexStatisticsList) {
				double score = Math
						.log10(1
								+ toLong(indexStatistics
										.getNumberOfOccurencesInContent())
								* (4.0 / 12.0)
								+ toLong(indexStatistics
										.getNumberOfOccurencesInDescription())
								* (3.0 / 12.0)
								+ toLong(indexStatistics
										.getNumberOfOccurencesInTitle())
								* (5.0 / 12.0))
						* Math.log10( 1 + numberOfAllArticles / numberOfOccurenceInAllArticle);
				System.out.println(index.getWord());
				System.out.println(Math.log10(1+ toLong(indexStatistics.getNumberOfOccurencesInContent())* (4.0 / 12.0)+ toLong(indexStatistics
										.getNumberOfOccurencesInDescription())* (3.0 / 12.0)+ toLong(indexStatistics.getNumberOfOccurencesInTitle())* (5.0 / 12.0)));
				System.out.println(Math.log10( 1 + numberOfAllArticles / numberOfOccurenceInAllArticle));
				System.out.println(numberOfOccurenceInAllArticle);
				System.out.println(toLong(indexStatistics.getNumberOfOccurencesInTitle()));
				System.out.println(toLong(indexStatistics.getNumberOfOccurencesInContent()));
				System.out.println(toLong(indexStatistics.getNumberOfOccurencesInDescription()));
				System.out.println(numberOfAllArticles);
				System.out.println(score);
				
				indexStatistics.setScore(toByteArray(score));
				if(!indexRepository.insertOrUpdateIndexArticleStatistics(indexStatistics, false))
					System.out.println("not saved");
				//System.out.println(index.getWord());
			}
		}

	}
	
	public List<Long> rankArticles(List<String> searchWords) {
		// TODO: traverse over the searchWord and get the corresponds indexes if
		// exists
		List<Indices> indices = new ArrayList<Indices>();
		List<String> notExistingWords = new ArrayList<String>();
		for(String word : searchWords){
			Indices index = indexRepository.getIndexByWord(word);
			if(index!=null)
				indices.add(index);
			else{
				List<Indices> phraseLikeIndices = indexRepository.getIndexByPhrase(word);
				if(phraseLikeIndices == null || phraseLikeIndices.size()==0)
					notExistingWords.add(word);
				else{
					for(Indices ind : phraseLikeIndices)
						indices.add(ind);
				}
			}
		}
		// TODO: calculate the score of each indices articles over all
		// searchWords
		SmartMap <Long, Double> articlesScoreMap = new SmartMap<Long, Double>();
		//for(Indices index : indices)
			//System.out.println(index.getWord());
		for(Indices index : indices){
			//System.out.println(index.getWord());
			List<Long> articleIDs = indexRepository.getRealArticleNumbers(index);
			//System.out.println("----------------------");
			//for(Long long1 : articleIDs)
				//System.out.println(long1.longValue());
			//System.out.println("----------------------");
			List<IndexArticleStatistics> indexStatisticsList = (List<IndexArticleStatistics>)indexRepository.findIndexArticleStatisticsRecordsByIndex(index);
			for(int i=0 ; i<indexStatisticsList.size() ; i++){
				//System.out.println("reached");
				articlesScoreMap.put(new Double(toDouble(indexStatisticsList.get(i).getScore())/articleRepository.getArticleLength(articleIDs.get(i))), articleIDs.get(i));
				//System.out.println(toDouble(indexStatisticsList.get(i).getScore()));
				//System.out.println(toDouble(indexStatisticsList.get(i).getScore())/articleRepository.getArticleLength(articleIDs.get(i)) + " , " +  articleIDs.get(i));
			}
		}
		//System.out.println(articlesScoreMap.size());
		// TODO: find repeated articles and then add their scores , and then eliminate the repeated ones
		for(int i=0 ; i<articlesScoreMap.size()-1 ; i++){
			for(int j=i+1 ; j<articlesScoreMap.size() ; j++){
				if(articlesScoreMap.getKey(i).longValue()==articlesScoreMap.getKey(j).longValue()){
					//System.out.println(articlesScoreMap.getKey(i).longValue());
					//System.out.println(articlesScoreMap.getValue(i));
					//System.out.println(articlesScoreMap.getKey(j).longValue());
					//System.out.println(articlesScoreMap.getValue(j));
					//System.out.println("reached");
					//System.out.println(articlesScoreMap.getValue(i) + articlesScoreMap.getValue(j));
					articlesScoreMap.setValue(articlesScoreMap.getKey(i), articlesScoreMap.getValue(i) + articlesScoreMap.getValue(j));
					//System.out.println(articlesScoreMap.getKey(i).longValue());
					//System.out.println(articlesScoreMap.getValue(i));
					articlesScoreMap.deleteByIndex(j);
					j--;
					//System.out.println(articlesScoreMap.getKey(i).longValue());
					//System.out.println(articlesScoreMap.getValue(i));
				}
			}
		}
		//System.out.println(articlesScoreMap.size());
		//for(int k=0 ; k<articlesScoreMap.size() ; k++)
			//System.out.println(articlesScoreMap.getKey(k).longValue() + " , " + articlesScoreMap.getValue(k).doubleValue());
		
		// TODO: give the articles with the right words positions a higher score
		/*try{
		List<Long> articleNumbers = articlesScoreMap.getKeys();
		for(int i=0 ; i<articleNumbers.size() ; i++){
			double additionalScore = 0;
			additionalScore = getAdditionalPositionMatchingScore(articleNumbers.get(i), indices);
			articlesScoreMap.setValue(i, new Double(articlesScoreMap.getValue(i).doubleValue() + additionalScore));
		}
		}
		catch(Exception e){
			e.printStackTrace();
		}*/
		
		// TODO: sort the articles and return it sorted in the articles ArraList
		//Bubble sort
		int size = articlesScoreMap.size();
		for(int i = size-1 ; i>1 ; i--){
			for(int j = 0 ; j<i ; j++){
				if(articlesScoreMap.getValue(j).doubleValue() < articlesScoreMap.getValue(j+1).doubleValue()){
					//swapping
					long first_long = articlesScoreMap.getKey(j).longValue();
					double first_double = articlesScoreMap.getValue(j).doubleValue();
					long second_long = articlesScoreMap.getKey(j+1).longValue();
					double second_double = articlesScoreMap.getValue(j+1).doubleValue();
					long temp_long = first_long;
					double temp_double = first_double;
					first_long = second_long;
					first_double = second_double;
					second_long = temp_long;
					second_double = temp_double;
					Long firstLong = new Long(first_long);
					Double firstDouble = new Double(first_double);
					Long secondLong = new Long(second_long);
					Double secondDouble = new Double(second_double);
					articlesScoreMap.setValue(articlesScoreMap.getKey(j), firstDouble);
					articlesScoreMap.setKey(firstDouble, firstLong);
					articlesScoreMap.setValue(articlesScoreMap.getKey(j+1), secondDouble);
					articlesScoreMap.setKey(secondDouble, secondLong);
				}
			}
		}
		return articlesScoreMap.getKeys();
	}

	/*private double getAdditionalPositionMatchingScore(Long articleID, List<Indices> indices){
		double additionalScore = 0;
		for(int i=0 ; i<indices.size()-1 ; i++){
			IndexArticleStatistics firstIndexStatistics = indexRepository.findIndexArticleStatisticsRecordByArticleIDAndIndex(articleID, indices.get(i));
			if(firstIndexStatistics == null)
				continue;
			List<IndexInArticleTitlePlaces> titlePlaces1 = indexRepository.findIndexInArticleTitlePlacesByIndexArticleStatistics(firstIndexStatistics);
			List<IndexInArticleContentPlaces> contentPlaces1 = indexRepository.findIndexInArticleContentPlacesByIndexArticleStatistics(firstIndexStatistics);
			List<IndexInArticleDescriptionPlaces> descriptionPlaces1 = indexRepository.findIndexInArticleDescriptionPlacesByIndexArticleStatistics(firstIndexStatistics);
			IndexArticleStatistics secondIndexStatistics = indexRepository.findIndexArticleStatisticsRecordByArticleIDAndIndex(articleID, indices.get(i+1));
			if(secondIndexStatistics == null)
				continue;
			List<IndexInArticleTitlePlaces> titlePlaces2 = indexRepository.findIndexInArticleTitlePlacesByIndexArticleStatistics(secondIndexStatistics);
			List<IndexInArticleContentPlaces> contentPlaces2 = indexRepository.findIndexInArticleContentPlacesByIndexArticleStatistics(secondIndexStatistics);
			List<IndexInArticleDescriptionPlaces> descriptionPlaces2 = indexRepository.findIndexInArticleDescriptionPlacesByIndexArticleStatistics(secondIndexStatistics);
			
			long startPlace1 = 0;
			long startPlace2 = 0;
			if (titlePlaces1 !=null && titlePlaces1.size()>0 && titlePlaces2 !=null && titlePlaces2.size()>0) {
				startPlace1 = toLong(titlePlaces1.get(0).getPlace());
				startPlace2 = toLong(titlePlaces2.get(0).getPlace());
				if (startPlace2 == startPlace1 + 1)
					additionalScore++;
				for (int index1 = 1; index1 < titlePlaces1.size(); index1++) {
					for (int index2 = 1; index2 < titlePlaces2.size(); index2++) {
						if (toLong(titlePlaces1.get(index1).getPlace()) + 1
								+ startPlace1 == toLong(titlePlaces2
								.get(index2).getPlace()) + startPlace2) {
							additionalScore++;
							break;
						}
						startPlace2 += toLong(titlePlaces2.get(index2)
								.getPlace());
					}
					startPlace1 += toLong(titlePlaces1.get(index1).getPlace());
				}
			}
			if (contentPlaces1 !=null && contentPlaces1.size()>0 && contentPlaces2 !=null && contentPlaces2.size()>0) {
				startPlace1 = toLong(contentPlaces1.get(0).getPlace());
				startPlace2 = toLong(contentPlaces2.get(0).getPlace());
				if (startPlace2 == startPlace1 + 1)
					additionalScore++;
				for (int index1 = 1; index1 < contentPlaces1.size(); index1++) {
					for (int index2 = 1; index2 < contentPlaces2.size(); index2++) {
						if (toLong(contentPlaces1.get(index1).getPlace()) + 1
								+ startPlace1 == toLong(contentPlaces2.get(
								index2).getPlace())
								+ startPlace2) {
							additionalScore++;
							break;
						}
						startPlace2 += toLong(contentPlaces2.get(index2)
								.getPlace());
					}
					startPlace1 += toLong(contentPlaces1.get(index1).getPlace());
				}
			}
			if (descriptionPlaces1 !=null && descriptionPlaces1.size()>0 && descriptionPlaces2 !=null && descriptionPlaces2.size()>0) {
				startPlace1 = toLong(descriptionPlaces1.get(0).getPlace());
				startPlace2 = toLong(descriptionPlaces2.get(0).getPlace());
				if (startPlace1 == startPlace2 + 1)
					additionalScore++;
				for (int index1 = 1; index1 < descriptionPlaces1.size(); index1++) {
					for (int index2 = 1; index2 < descriptionPlaces2.size(); index2++) {
						if (toLong(descriptionPlaces1.get(index1).getPlace())
								+ 1 + startPlace1 == toLong(descriptionPlaces2
								.get(index2).getPlace()) + startPlace2) {
							additionalScore++;
							break;
						}
						startPlace2 += toLong(descriptionPlaces2.get(index2)
								.getPlace());
					}
					startPlace1 += toLong(descriptionPlaces1.get(index1)
							.getPlace());
				}
			}
		}
		return additionalScore;
	}
	*/
	
	public List<Long> search(String sentence) {
		List<String> searchWords = new ArrayList<String>();
		StringTokenizer tokenizer = new StringTokenizer(sentence);
		while (tokenizer.hasMoreTokens()){
			String word = tokenizer.nextToken();
			searchWords.add(word);
			//System.out.println(word);
		}
		return rankArticles(searchWords);
	}

	private byte[] toByteArray(Long in) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		try {
			dos.writeLong(in.longValue());
			dos.close();
			return baos.toByteArray();
		} catch (Exception e) {
			return null;
		}
	}

	private byte[] toByteArray(double in) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		try {
			dos.writeDouble(in);
			dos.close();
			return baos.toByteArray();
		} catch (Exception e) {
			return null;
		}
	}

	private long toLong(byte[] in) {
		ByteBuffer bb = ByteBuffer.wrap(in);
		return bb.getLong();
	}
	
	private double toDouble(byte[] in) {
		ByteBuffer bb = ByteBuffer.wrap(in);
		return bb.getDouble();
	}

	public void deleteArticle(Article article) {
		indexRepository.obsoleteArticle(article);
		if (ableToDelete()) {
			// deletion operation
			List<Article> obsoleteArticles = articleRepository
					.getObsoleteArticles();
			for (Article obsolete : obsoleteArticles) {
				// get the indexArticleStatistics records whose
				// articleNumber corresponds to this obsolete article
				boolean intentionToDeleteArticle = true;
				List<IndexArticleStatistics> indexArticleStatisticsRecords = indexRepository
						.findIndexArticleStatisticsRecordsByArticle(obsolete,
								intentionToDeleteArticle);
				// get the indexInArticleTitlePlaces records for these
				// indexArticleStatistics's and for content and description too
				List<IndexInArticleTitlePlaces> indexInArticleTitlePlacesRecords = new ArrayList<IndexInArticleTitlePlaces>();
				List<IndexInArticleContentPlaces> indexInArticleContentPlacesRecords = new ArrayList<IndexInArticleContentPlaces>();
				List<IndexInArticleDescriptionPlaces> indexInArticleDescriptionPlacesRecords = new ArrayList<IndexInArticleDescriptionPlaces>();
				for (IndexArticleStatistics indexStatistics : indexArticleStatisticsRecords) {
					for (IndexInArticleTitlePlaces indexInArticleTitlePlaces : indexRepository
							.findIndexInArticleTitlePlacesByIndexArticleStatistics(indexStatistics)) {
						indexInArticleTitlePlacesRecords
								.add(indexInArticleTitlePlaces);
					}
					for (IndexInArticleContentPlaces indexInArticleContentPlaces : indexRepository
							.findIndexInArticleContentPlacesByIndexArticleStatistics(indexStatistics)) {
						indexInArticleContentPlacesRecords
								.add(indexInArticleContentPlaces);
					}
					for (IndexInArticleDescriptionPlaces indexInArticleDescriptionPlaces : indexRepository
							.findIndexInArticleDescriptionPlacesByIndexArticleStatistics(indexStatistics)) {
						indexInArticleDescriptionPlacesRecords
								.add(indexInArticleDescriptionPlaces);
					}
				}
				// get the indices that cooperate with these
				// indexArticleStatistics's
				List<Indices> indicesInTheObsoleteArticle = new ArrayList<Indices>();
				for (IndexArticleStatistics indexArticleStatistics : indexArticleStatisticsRecords) {
					if (!isAlreadyExist(indexArticleStatistics.getIndex(),
							indicesInTheObsoleteArticle))
						indicesInTheObsoleteArticle.add(indexArticleStatistics
								.getIndex());
				}
				// get indices out of those , that has no rows existing in
				// the IndexArticleStatistics table , then delete them
				List<Indices> indicesToBeDeleted = new ArrayList<Indices>();
				for (Indices index : indicesInTheObsoleteArticle) {
					if (!(indexRepository
							.findIndexArticleStatisticsRecordsByIndex(index)
							.size() > 1))
						indicesToBeDeleted.add(index);
				}
				// delete the indexArticleStatistics's places , then
				// delete indexArticleStatistics records, then indices
				indexRepository
						.deleteIndexInArticleTitlePlacesRecords(indexInArticleTitlePlacesRecords);
				indexRepository
						.deleteIndexInArticleContentPlacesRecords(indexInArticleContentPlacesRecords);
				indexRepository
						.deleteIndexInArticleDescriptionPlacesRecords(indexInArticleDescriptionPlacesRecords);
				for (IndexArticleStatistics indexArticleStatistics : indexArticleStatisticsRecords)
					indexRepository
							.deleteIndexArticleStatictics(indexArticleStatistics);
				for (Indices index : indicesToBeDeleted)
					indexRepository.deleteIndex(index);
				// delete the obsolete article itself
				articleRepository.deleteArticle(obsolete);
			}
		}
	}

	private boolean isAlreadyExist(Indices index, List<Indices> indices) {
		if (indices == null || index == null || indices.size() == 0)
			return false;
		for (Indices i : indices) {
			if ((i.getWord().equals(index.getWord()))
					&& (i.getId() == index.getId()))
				return true;
		}
		return false;
	}

	public void editArticle(Article oldArticle, Article newArticle) {
		saveOrUpdate(newArticle);
		deleteArticle(oldArticle);
	}

	private boolean ableToDelete() {
		return indexRepository.ableToDelete();
	}
}
