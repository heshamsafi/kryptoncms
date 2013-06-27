package edu.asu.krypton.model.persist.db;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 
 * @author Nadeem
 *
 */
@Document
public class PhotoComment extends Comment implements DbEntity{

//	@DBRef
//	private Photo parent;

}
