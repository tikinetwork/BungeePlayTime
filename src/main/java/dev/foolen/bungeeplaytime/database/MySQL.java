package dev.foolen.bungeeplaytime.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import dev.foolen.bungeeplaytime.BungeePlayTime;
import dev.foolen.bungeeplaytime.player.Player;
import net.md_5.bungee.config.Configuration;

public class MySQL {

	private DataSource datasource;
	private static Connection connection = null;

	public MySQL(BungeePlayTime plugin) {
		System.out.println("============================");
		System.out.println(plugin.getConfig().getString("mysql.hostname"));
		System.out.println(plugin.getConfig().getString("mysql.username"));
		System.out.println(plugin.getConfig().getString("mysql.password"));
		System.out.println(plugin.getConfig().getString("mysql.database"));
		System.out.println("============================");
		DataSource dataSource = getDataSource(plugin.getConfig());
		
		try {
			connection = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (connection != null) {
			System.out.println("Database connection initialized!");
		} else {
			System.out.println("[ERROR] Database connection failed!!!");
		}
	}

	public DataSource getDataSource(Configuration config) {
		if (datasource == null) {
			HikariConfig hc = new HikariConfig();

			hc.setJdbcUrl(
					"jdbc:mysql://" + config.getString("mysql.database") + "/" + config.getString("mysql.database"));
			hc.setUsername(config.getString("mysql.username"));
			hc.setPassword(config.getString("mysql.password"));

			hc.setMaximumPoolSize(10);
			hc.setAutoCommit(false);
			hc.addDataSourceProperty("cachePrepStmts", "true");
			hc.addDataSourceProperty("prepStmtCacheSize", "250");
			hc.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

			datasource = new HikariDataSource(hc);
		}

		return datasource;
	}
	
	public static ArrayList<Player> getAllPlayers() {
		ArrayList<Player> players = new ArrayList<Player>();
		
		try {
			String sql = "SELECT * FROM `players`";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			ResultSet result = pstmt.executeQuery();
			
			while (result.next()) {
				players.add(new Player(UUID.fromString(result.getString("uuid")), result.getInt("total_seconds_played")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return players;
	}
}
