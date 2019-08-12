package dev.foolen.bungeeplaytime.database.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariDataSource;

import dev.foolen.bungeeplaytime.config.Config;
import dev.foolen.bungeeplaytime.database.mysql.databases.playerdatabase.PlayerDatabase;
import net.md_5.bungee.config.Configuration;

public class MySQLModule {

	private static MySQLModule instance;
	
	private final String HOSTNAME;
	private final String DATABASE;
	private final String PORT;
	private final String USERNAME;
	private final String PASSWORD;
	
	private HikariDataSource datasource;

	public MySQLModule() {
		instance = this;
		
		Configuration config = Config.getConfig();
		
		HOSTNAME = config.getString("mysql.hostname");
		DATABASE = config.getString("mysql.database");
		PORT = config.getString("mysql.port");
		USERNAME = config.getString("mysql.username");
		PASSWORD = config.getString("mysql.password");
		
		datasource = new HikariDataSource();
		
		datasource.setMaximumPoolSize(25);
		datasource.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
		datasource.addDataSourceProperty("url", HOSTNAME);
		datasource.addDataSourceProperty("port", PORT);
		datasource.addDataSourceProperty("databaseName", DATABASE);
		datasource.addDataSourceProperty("user", USERNAME);
		datasource.addDataSourceProperty("password", PASSWORD);
		
		// Load databases
		new PlayerDatabase();
	}
	
	public static MySQLModule getInstance() {
		return instance;
	}
	
	public HikariDataSource getDataSource() {
		return datasource;
	}

	public void closeConnection() {
		if (datasource != null) {
			datasource.close();
		} else {
			System.out.println("[WARNING] Datasource was not closed!");
		}
	}

	public void insertQuery(String query) {

		Connection connection = null;
		PreparedStatement pStatement = null;

		try {
			connection = datasource.getConnection();
			pStatement = connection.prepareStatement(query);
			pStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("[WARNING] Something went wrong on inserting Query @ Database:insertQuery");
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException ex) {
				System.out.println("[WARNING] Couldn't close database connection");
			}
			try {
				if (pStatement != null) {
					pStatement.close();
				}
			} catch (SQLException ex) {
				System.out.println("[WARNING] Couldn't close preparedstatement");
			}
		}
	}
}
