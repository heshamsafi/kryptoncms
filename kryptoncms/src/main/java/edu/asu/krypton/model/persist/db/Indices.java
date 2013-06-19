package edu.asu.krypton.model.persist.db;

import java.util.Collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import edu.asu.krypton.model.repository.Repository;

public class Indices implements DbEntity{

	@Id
	private Long id;
	private String word;
	@DBRef
	private Collection<IndexArticleStatistics> indexStatisticsList;
	
	public Indices() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	
	public Collection<IndexArticleStatistics> getIndexStatisticsList() {
		return indexStatisticsList;
	}
	public void setIndexStatisticsList(
			Collection<IndexArticleStatistics> indexStatisticsList) {
		this.indexStatisticsList = indexStatisticsList;
	}
	public boolean equals(Indices index) {
		if(index.getId() == this.id
		&& index.getWord().equalsIgnoreCase(this.word) ){
			return true;
		}
		return false;
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
