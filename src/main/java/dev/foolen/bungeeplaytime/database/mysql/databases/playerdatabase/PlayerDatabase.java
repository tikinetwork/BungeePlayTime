package dev.foolen.bungeeplaytime.database.mysql.databases.playerdatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dev.foolen.bungeeplaytime.database.mysql.MySQLModule;

public class PlayerDatabase {
	
	public PlayerDatabase() {
		setupTable();
	}
	
	private void setupTable() {
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = MySQLModule.getInstance().getDataSource().getConnection();
			pstmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `players` (" 
						+ "  `uuid` varchar(255) NOT NULL," 
						+ "  `total_seconds_played` int(11) NOT NULL," 
						+ "  PRIMARY KEY(`uuid`))");
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("[WARNING] Something went wrong on creating the table @ PlayerDatabase:setupTable()");
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException ex) {
				System.out.println("[WARNING] Couldn't close database connection");
			}
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException ex) {
				System.out.println("[WARNING] Couldn't close prepared statement");
			}
		}
	}
}
