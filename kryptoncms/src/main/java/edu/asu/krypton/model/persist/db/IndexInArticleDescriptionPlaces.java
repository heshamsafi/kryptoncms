package edu.asu.krypton.model.persist.db;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import edu.asu.krypton.model.repository.Repository;

@Document
@XmlRootElement
public class IndexInArticleDescriptionPlaces implements DbEntity{

	@Id
	private String id;
	@DBRef
	private IndexArticleStatistics indexArticleStatistics;
	private byte[] place;
	
	public byte[] getPlace() {
		return place;
	}

	public void setPlace(byte[] place) {
		this.place = place;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public IndexArticleStatistics getIndexArticleStatistics() {
		return indexArticleStatistics;
	}

	public void setIndexArticleStatistics(
			IndexArticleStatistics indexArticleStatistics) {
		this.indexArticleStatistics = indexArticleStatistics;
	}

	public IndexInArticleDescriptionPlaces() {
		super();
	}

	@Override
	public void onDelete(Repository<?> repository)
			throws ClassNotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEdit(Repository<?> repository) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onInsert(Repository<?> repository) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void merge(DbEntity newObject) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addOwned(DbEntity owned) {
		// TODO Auto-generated method stub
		
	}

}
