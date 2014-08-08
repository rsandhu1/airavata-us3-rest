package org.scigap.us3.client.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionManager {
	
	private static Connection connection;
	
	public static Connection getConnection(String jdbcDriver, String jdbcUrl) throws Exception{
		if(connection == null || connection.isClosed()){
			  Class.forName(jdbcDriver);
              connection = DriverManager.getConnection(jdbcUrl);
      }
	return connection;
	}
	public static void closeConnection(Connection connection) throws Exception{
		if(connection != null){
		connection.close();
		}
	}

}
