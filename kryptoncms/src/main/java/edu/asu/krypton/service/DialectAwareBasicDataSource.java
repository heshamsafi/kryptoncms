package edu.asu.krypton.service;


public class DialectAwareBasicDataSource 
			extends org.apache.commons.dbcp.BasicDataSource {
			//extends org.springframework.jdbc.datasource.DriverManagerDataSource{
	private String dialect;

	public String getDialect() {
		return dialect;
	}

	public void setDialect(String dialect) {
		this.dialect = dialect;
	}
	
}
