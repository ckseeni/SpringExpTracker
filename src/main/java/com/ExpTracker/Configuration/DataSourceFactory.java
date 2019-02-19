package com.ExpTracker.Configuration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.dbcp.BasicDataSource;

public class DataSourceFactory {
	
	private static BasicDataSource dataSource = new BasicDataSource();
	
	private static String MSSQL_JDBCDRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static String MSSQL_JDBCURL = "jdbc:sqlserver://localhost:1433;databaseName=ExpTracker";
	private static String POSTGRE_JDBCDRIVER = "org.postgresql.Driver";
	private static String POSTGRE_JDBCURL = "jdbc:postgresql://172.24.130.150:5432/ExpTracker";
	private static String MSSQL_DIALECT = "org.hibernate.dialect.SQLServerDialect";
	private static String POSTGRE_DIALECT = "org.hibernate.dialect.PostgreSQLDialect";
	
	public static BasicDataSource getDataSource() throws IOException {
		FileReader fileReader = new FileReader("C:\\Spring apps\\ExpTracker\\src\\main\\DB\\dbFile.txt");
		BufferedReader br = new  BufferedReader(fileReader);
		String type = br.readLine();
		int db = Integer.parseInt(type);
		if (db == 1) {
			dataSource.setDriverClassName(MSSQL_JDBCDRIVER);
			dataSource.setUrl(MSSQL_JDBCURL);
			dataSource.setUsername("ckseeni199631");
			dataSource.setPassword("Stefan@1996");
		} else {
			dataSource.setDriverClassName(POSTGRE_JDBCDRIVER);
			dataSource.setUrl(POSTGRE_JDBCURL);
			dataSource.setUsername("postgres");
			dataSource.setPassword("root@123");
		}
		return dataSource;
	}
	
	public static String getHibernateDialect() throws IOException {
		FileReader fileReader = new FileReader("C:\\Spring apps\\ExpTracker\\src\\main\\DB\\dbFile.txt");
		BufferedReader br = new  BufferedReader(fileReader);
		String type = br.readLine();
		int db = Integer.parseInt(type);
		if (db == 1)
			return MSSQL_DIALECT;
		return POSTGRE_DIALECT;
	}
}
