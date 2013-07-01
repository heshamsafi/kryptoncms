package edu.asu.krypton.model.persist.db;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import edu.asu.krypton.model.repository.Repository;

@Document
@XmlRootElement
public class Indices implements DbEntity{

	@Id
	private String id;
	@Indexed(unique=true)
	private String word;
//	@DBRef
//	private Collection<IndexArticleStatistics> indexStatisticsList;
	
	public Indices() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	
//	public Collection<IndexArticleStatistics> getIndexStatisticsList() {
//		return indexStatisticsList;
//	}
//	public void setIndexStatisticsList(
//			Collection<IndexArticleStatistics> indexStatisticsList) {
//		this.indexStatisticsList = indexStatisticsList;
//	}
	public boolean equals(Indices index) {
		if(index.getId() == this.id
		&& index.getWord().equalsIgnoreCase(this.word) ){
			return true;
		}
		return false;
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
}
