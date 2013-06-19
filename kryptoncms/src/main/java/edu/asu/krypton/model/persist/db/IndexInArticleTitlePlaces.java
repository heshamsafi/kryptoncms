package edu.asu.krypton.model.persist.db;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import edu.asu.krypton.model.repository.Repository;

public class IndexInArticleTitlePlaces implements DbEntity{

	@Id
	private Long id;
	@DBRef
	private IndexArticleStatistics indexArticleStatistics;
	private byte[] place;
	
	public byte[] getPlace() {
		return place;
	}
	public void setPlace(byte[] place) {
		this.place = place;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public IndexArticleStatistics getIndexArticleStatistics() {
		return indexArticleStatistics;
	}
	public void setIndexArticleStatistics(
			IndexArticleStatistics indexArticleStatistics) {
		this.indexArticleStatistics = indexArticleStatistics;
	}
	public IndexInArticleTitlePlaces() {
		super();
	}
	@Override
	public void onDelete(Repository<?> repository) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onEdit(Repository<?> repository) {
		// TODO Auto-generated method stub
		
	}

}
