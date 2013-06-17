package edu.asu.krypton.model.persist.db;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class PhotoComment extends Comment{

//	@DBRef
//	private Photo parent;

}
