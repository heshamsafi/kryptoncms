package edu.asu.krypton.model.message_proxies;

import java.util.Collection;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class QueryMessage<QueryResult> extends Message {
	private Collection<QueryResult> queryResult;
	
	@XmlElementWrapper
	@XmlAnyElement(lax=true)
	public Collection<QueryResult> getQueryResult() {
		return queryResult;
	}
	public QueryMessage<QueryResult> setQueryResult(Collection<QueryResult> queryResult) {
		this.queryResult = queryResult;
		return this;
	}
	
}
