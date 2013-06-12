package edu.asu.krypton.model.persist.db;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class IndexArticleStatistics implements DbEntity{

	@Id
	private Long id;
	private byte[] articleNumber;
	private byte[] score;
	private byte[] numberOfOccurencesInTitle = toByteArray(0);
	private byte[] numberOfOccurencesInDescription = toByteArray(0);
	private byte[] numberOfOccurencesInContent = toByteArray(0);
	@DBRef
	private Indices index;
	@DBRef
	private Collection<IndexInArticleContentPlaces> indexInArticleContentPlaces;
	@DBRef
	private Collection<IndexInArticleTitlePlaces> indexInArticleTitlePlaces;
	@DBRef
	private Collection<IndexInArticleDescriptionPlaces> indexInArticleDescriptionPlaces;

	public byte[] getScore() {
		return score;
	}

	public void setScore(byte[] score) {
		this.score = score;
	}
	
	public Collection<IndexInArticleContentPlaces> getIndexInArticleContentPlaces() {
		return indexInArticleContentPlaces;
	}

	public void setIndexInArticleContentPlaces(
			Collection<IndexInArticleContentPlaces> indexInArticleContentPlaces) {
		this.indexInArticleContentPlaces = indexInArticleContentPlaces;
	}

	public Collection<IndexInArticleTitlePlaces> getIndexInArticleTitlePlaces() {
		return indexInArticleTitlePlaces;
	}

	public void setIndexInArticleTitlePlaces(
			Collection<IndexInArticleTitlePlaces> indexInArticleTitlePlaces) {
		this.indexInArticleTitlePlaces = indexInArticleTitlePlaces;
	}

	public Collection<IndexInArticleDescriptionPlaces> getIndexInArticleDescriptionPlaces() {
		return indexInArticleDescriptionPlaces;
	}

	public void setIndexInArticleDescriptionPlaces(
			Collection<IndexInArticleDescriptionPlaces> indexInArticleDescriptionPlaces) {
		this.indexInArticleDescriptionPlaces = indexInArticleDescriptionPlaces;
	}

	public IndexArticleStatistics() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public byte[] getNumberOfOccurencesInTitle() {
		return numberOfOccurencesInTitle;
	}

	public void setNumberOfOccurencesInTitle(byte[] numberOfOccurencesInTitle) {
		this.numberOfOccurencesInTitle = numberOfOccurencesInTitle;
	}

	public byte[] getNumberOfOccurencesInDescription() {
		return numberOfOccurencesInDescription;
	}

	public void setNumberOfOccurencesInDescription(
			byte[] numberOfOccurencesInDescription) {
		this.numberOfOccurencesInDescription = numberOfOccurencesInDescription;
	}

	public byte[] getNumberOfOccurencesInContent() {
		return numberOfOccurencesInContent;
	}

	public void setNumberOfOccurencesInContent(
			byte[] numberOfOccurencesInContent) {
		this.numberOfOccurencesInContent = numberOfOccurencesInContent;
	}

	public byte[] getArticleNumber() {
		return articleNumber;
	}

	public void setArticleNumber(byte[] articleNumber) {
		this.articleNumber = articleNumber;
	}

	public Indices getIndex() {
		return index;
	}

	public void setIndex(Indices index) {
		this.index = index;
	}

	public boolean equals(IndexArticleStatistics indexStatistics) {
		if( Arrays.equals(indexStatistics.getArticleNumber(), this.articleNumber) 
				&& indexStatistics.getNumberOfOccurencesInContent() == this.numberOfOccurencesInContent
				&& indexStatistics.getNumberOfOccurencesInDescription() == this.numberOfOccurencesInDescription
				&& indexStatistics.getNumberOfOccurencesInTitle() == this.numberOfOccurencesInTitle
				&& Arrays.equals(indexStatistics.getScore(), this.score) 
				&& indexStatistics.getId() == this.id 
				&& indexStatistics.getIndex().equals(this.index)){
			return true;
		}
		return false;
	}

	private byte[] toByteArray(long in) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		try {
			dos.writeLong(in);
			dos.close();
			return baos.toByteArray();
		} catch (Exception e) {
			return null;
		}
	}
}
