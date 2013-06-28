package edu.asu.krypton.model.repository;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import edu.asu.krypton.model.persist.db.Article;
import edu.asu.krypton.model.persist.db.IndexArticleStatistics;
import edu.asu.krypton.model.persist.db.IndexInArticleContentPlaces;
import edu.asu.krypton.model.persist.db.IndexInArticleDescriptionPlaces;
import edu.asu.krypton.model.persist.db.IndexInArticleTitlePlaces;
import edu.asu.krypton.model.persist.db.Indices;

@Repository
public class IndexRepository extends edu.asu.krypton.model.repository.Repository<Indices> {

	public IndexRepository(){
		setPersistentClass(Indices.class);
	}
	
	
	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	public boolean insertOrUpdateIndex(Indices index) {
		try{
			mongoTemplate.save(index);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteIndex(Indices index) {
		try{
			mongoTemplate.remove(index);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteIndexArticleStatictics(
			IndexArticleStatistics indexArticleStatistics) {
		try{
			mongoTemplate.remove(indexArticleStatistics);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	
	public Indices getIndexByWord(String word) {
		Query query = new Query();
		query.addCriteria(Criteria.where("word").is(word));
		return mongoTemplate.findOne(query, Indices.class);
	}
	
	public List<Indices> getIndexByPhrase(String phrase) {
		Query query = new Query();
		query.addCriteria(Criteria.where("word").regex(".*" + phrase + ".*"));
		return mongoTemplate.find(query, Indices.class);
	}

	
//	public boolean ableToDelete() {
//		List<Article> allArticles = mongoTemplate.findAll(Article.class);
//		int numberOfArticles = allArticles.size();
//		int numberOfObsoleteArticles = 0;
//		for(Article article : allArticles){
//			if(article.isObsolete()==true)
//				numberOfObsoleteArticles++;
//		}
//		if ((numberOfObsoleteArticles * 100 / numberOfArticles >= 25)
//				&& (numberOfArticles >= 50))
//			return true;
//		return false;
//	}

	 
	public List<Indices> getAllIndices() {
		return mongoTemplate.findAll(Indices.class);
	}
	
	public boolean insertOrUpdateIndexArticleStatistics(
			IndexArticleStatistics indexArticleStatistics, boolean adjust) {
		try {
			if (adjust) {
				// adjust the articleNumber before saving if adjust equals true
				Query query = new Query();
				query.addCriteria(Criteria.where("index").is(indexArticleStatistics.getIndex()));
				List<IndexArticleStatistics> list = mongoTemplate.find(query, IndexArticleStatistics.class);
//				 System.out.println(list.size());
				BigInteger articleNumberToBeUsed = new BigInteger("0");
				for(IndexArticleStatistics indexStatistics : list)
					articleNumberToBeUsed = articleNumberToBeUsed.add(toBigInteger(indexStatistics.getArticleNumber()));
				indexArticleStatistics.setArticleNumber(toByteArray(toBigInteger(indexArticleStatistics.getArticleNumber()).subtract(articleNumberToBeUsed)));
			}
			mongoTemplate.save(indexArticleStatistics);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	
	public boolean insertOrUpdateIndexInArticleTitlePlaces(
			IndexInArticleTitlePlaces indexInArticleTitlePlaces) {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("indexArticleStatistics").is(indexInArticleTitlePlaces.getIndexArticleStatistics()));
			List<IndexInArticleTitlePlaces> list = mongoTemplate.find(query, IndexInArticleTitlePlaces.class);
			if (!((list == null) || (list.size() == 0))) {
				long placeToBeUsed = 0;
				for(IndexInArticleTitlePlaces i : list)
					placeToBeUsed += toLong(i.getPlace());
				indexInArticleTitlePlaces.setPlace(toByteArray(new Long(
						toLong(indexInArticleTitlePlaces.getPlace())
								- placeToBeUsed)));
			}
			mongoTemplate.save(indexInArticleTitlePlaces);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	
	public boolean insertOrUpdateIndexInArticleContentPlaces(
			IndexInArticleContentPlaces indexInArticleContentPlaces) {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("indexArticleStatistics").is(indexInArticleContentPlaces.getIndexArticleStatistics()));
			List<IndexInArticleContentPlaces> list = mongoTemplate.find(query, IndexInArticleContentPlaces.class);
			if (!((list == null) || (list.size() == 0))) {
				long placeToBeUsed = 0;
				for(IndexInArticleContentPlaces i : list)
					placeToBeUsed += toLong(i.getPlace());
				indexInArticleContentPlaces.setPlace(toByteArray(new Long(
						toLong(indexInArticleContentPlaces.getPlace())
								- placeToBeUsed)));
			}
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	
	public boolean insertOrUpdateIndexInArticleDescriptionPlaces(
			IndexInArticleDescriptionPlaces indexInArticleDescriptionPlaces) {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("indexArticleStatistics").is(indexInArticleDescriptionPlaces.getIndexArticleStatistics()));
			List<IndexInArticleDescriptionPlaces> list = mongoTemplate.find(query, IndexInArticleDescriptionPlaces.class);
			if (!((list == null) || (list.size() == 0))) {
				long placeToBeUsed = 0;
				for(IndexInArticleDescriptionPlaces i : list)
					placeToBeUsed += toLong(i.getPlace());
				indexInArticleDescriptionPlaces.setPlace(toByteArray(new Long(
						toLong(indexInArticleDescriptionPlaces.getPlace())
								- placeToBeUsed)));
			}
			mongoTemplate.save(indexInArticleDescriptionPlaces);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public IndexArticleStatistics findIndexArticleStatisticsRecordByArticleAndIndex(
			Article article, Indices index) {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("index").is(index));
			List<IndexArticleStatistics> list = mongoTemplate.find(query, IndexArticleStatistics.class);
			if (list == null || list.size() == 0)
				return null;
			byte[] startNumber = list.get(0).getArticleNumber();
			if (Arrays.equals(toByteArray(new BigInteger(article.getId(),16)), startNumber))
				return list.get(0);
			for (int i = 1; i < list.size(); i++) {
				IndexArticleStatistics indexStatistics = list.get(i);
				if (Arrays.equals(toByteArray(new BigInteger(article.getId(),16)),
						toByteArray(toBigInteger(startNumber).add(toBigInteger(indexStatistics.getArticleNumber()))))){
					return list.get(i);
				}
				startNumber = toByteArray(toBigInteger(startNumber).add(toBigInteger(indexStatistics.getArticleNumber())));
			}
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	
	public IndexArticleStatistics findIndexArticleStatisticsRecordByArticleIDAndIndex(
			BigInteger articleID, Indices index) {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("index").is(index));
			List<IndexArticleStatistics> list = mongoTemplate.find(query, IndexArticleStatistics.class);
			if (list == null || list.size() == 0)
				return null;
			byte[] startNumber = list.get(0).getArticleNumber();
			if (Arrays.equals(toByteArray(articleID), startNumber))
				return list.get(0);
			for (int i = 1; i < list.size(); i++) {
				IndexArticleStatistics indexStatistics = list.get(i);
				if (Arrays.equals(toByteArray(articleID),
						toByteArray(toBigInteger(startNumber).add(toBigInteger(indexStatistics.getArticleNumber()))))){
					return list.get(i);
				}
				startNumber = toByteArray(toBigInteger(startNumber).add(toBigInteger(indexStatistics.getArticleNumber())));
			}
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	
	public List<IndexArticleStatistics> findIndexArticleStatisticsRecordsByIndex(
			Indices index) {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("index").is(index));
			List<IndexArticleStatistics> list = mongoTemplate.find(query, IndexArticleStatistics.class);
			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public void obsoleteArticle(Article article) {
		article.setObsolete(true);
		mongoTemplate.save(article);
	}

	
	public List<IndexInArticleTitlePlaces> findIndexInArticleTitlePlacesByIndexArticleStatistics(
			IndexArticleStatistics indexStatistics) {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("indexArticleStatistics").is(indexStatistics));
			List<IndexInArticleTitlePlaces> list = mongoTemplate.find(query, IndexInArticleTitlePlaces.class);
			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	
	public List<IndexInArticleContentPlaces> findIndexInArticleContentPlacesByIndexArticleStatistics(
			IndexArticleStatistics indexStatistics) {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("indexArticleStatistics").is(indexStatistics));
			List<IndexInArticleContentPlaces> list = mongoTemplate.find(query, IndexInArticleContentPlaces.class);
			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public void deleteIndexInArticleTitlePlacesRecords(
			List<IndexInArticleTitlePlaces> records) {
		for (IndexInArticleTitlePlaces record : records) {
			mongoTemplate.remove(record);
		}
	}

	public void deleteIndexInArticleContentPlacesRecords(
			List<IndexInArticleContentPlaces> records) {
		for (IndexInArticleContentPlaces record : records) {
			mongoTemplate.remove(record);
		}
	}

	public void deleteIndexInArticleDescriptionPlacesRecords(
			List<IndexInArticleDescriptionPlaces> records) {
		for (IndexInArticleDescriptionPlaces record : records) {
			mongoTemplate.remove(record);
		}
	}

	
	public List<IndexArticleStatistics> findIndexArticleStatisticsRecordsByArticle(
			Article article, boolean intentionToDeleteArticle) {
		try {
			List<Indices> allIndices = mongoTemplate.findAll(Indices.class);
			List<IndexArticleStatistics> indexArticleStatisticsRecords = new ArrayList<IndexArticleStatistics>();
			for (Indices index : allIndices) {
				IndexArticleStatistics indexStatistics = findIndexArticleStatisticsRecordByArticleAndIndex(
						article, index);
				if (indexStatistics != null) {
					indexArticleStatisticsRecords.add(indexStatistics);
					if (intentionToDeleteArticle) {
						List<IndexArticleStatistics> indexArticleStatisticsList = findIndexArticleStatisticsRecordsByIndex(index);
						int size = indexArticleStatisticsList.size();
						for (int i = 0; i < size; i++) {
							if (indexArticleStatisticsList.get(i).equals(
									indexStatistics)) {
								indexArticleStatisticsList.get(i + 1).setArticleNumber(toByteArray(toBigInteger(indexStatistics.getArticleNumber()).add(toBigInteger(indexArticleStatisticsList.get(i + 1).getArticleNumber()))));
								insertOrUpdateIndexArticleStatistics(
										indexArticleStatisticsList.get(i + 1),
										false);
							}
						}
					}
				}
			}
			return indexArticleStatisticsRecords;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
	public List<IndexInArticleDescriptionPlaces> findIndexInArticleDescriptionPlacesByIndexArticleStatistics(
			IndexArticleStatistics indexStatistics) {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("indexArticleStatistics").is(indexStatistics));
			List<IndexInArticleDescriptionPlaces> list = mongoTemplate.find(query, IndexInArticleDescriptionPlaces.class);
			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	private byte[] toByteArray(Long in) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		try {
			dos.writeLong(in.longValue());
			dos.close();
			return baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private byte[] toByteArray(BigInteger in) {
		try {
			return in.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private BigInteger toBigInteger(byte[] in) {
		return new BigInteger(in);
	}

	private long toLong(byte[] in) {
		ByteBuffer bb = ByteBuffer.wrap(in);
		return bb.getLong();
	}
	
	public List<BigInteger> getRealArticleNumbers(Indices index) {
		Query query = new Query();
		query.addCriteria(Criteria.where("index").is(index));
		List<IndexArticleStatistics> list = mongoTemplate.find(query, IndexArticleStatistics.class);
		//System.out.println(list.size());
		List<BigInteger> realArticleNumbers = new ArrayList<BigInteger>();
		BigInteger startNumber = toBigInteger(list.get(0).getArticleNumber());
		System.out.println(startNumber.toString(16));
		realArticleNumbers.add(startNumber);
		int size = list.size();
		for (int i = 1; i < size; i++) {
			realArticleNumbers.add(startNumber.add(toBigInteger(list.get(i).getArticleNumber())));
			startNumber = startNumber.add(toBigInteger(list.get(i).getArticleNumber()));
		}
		return realArticleNumbers;
	}
}
