package com.revature.p0.util.datasource;

	
	import java.io.FileNotFoundException;
	import java.io.FileReader;
	import java.io.IOException;
	import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.SQLException;
	import java.util.Properties;

	public class ConnectionFactory {
		private static final ConnectionFactory connectionFactory = new ConnectionFactory();
		private Properties prop = new Properties();
		
		static {
			try {
				Class.forName("org.postgresql.Driver");
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		private ConnectionFactory() {
			
			// Using .properties for DB credentials (this is to obfuscate)
			try {
				// what is this????
				prop.load(new FileReader("src/main/resources/db.properties"));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public static ConnectionFactory getInstance() {
			return connectionFactory;
		}
		
		public Connection getConnection() {
			Connection conn = null;

			try {
				// I need to put this info in some db.properties file
				conn = DriverManager.getConnection(prop.getProperty("url"),prop.getProperty("admin"),prop.getProperty("password"));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			return conn;
		}
		

	}
