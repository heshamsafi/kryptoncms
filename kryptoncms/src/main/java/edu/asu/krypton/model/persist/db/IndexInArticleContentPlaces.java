package edu.asu.krypton.model.persist.db;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class IndexInArticleContentPlaces implements DbEntity{

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
	public IndexInArticleContentPlaces() {
		super();
	}

}
