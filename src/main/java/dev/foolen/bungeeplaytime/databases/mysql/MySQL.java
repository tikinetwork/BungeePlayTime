package dev.foolen.bungeeplaytime.databases.mysql;

import com.zaxxer.hikari.HikariDataSource;

import dev.foolen.bungeeplaytime.config.Config;
import dev.foolen.bungeeplaytime.databases.PlayerDB;
import net.md_5.bungee.config.Configuration;

public class MySQL {
	private final String HOSTNAME;
	private final int PORT;
	private final String DATABASE;
	private final String USERNAME;
	private final String PASSWORD;

	private static HikariDataSource datasource;

	public MySQL() {
		Configuration config = Config.getConfig();

		HOSTNAME = config.getString("mysql.hostname");
		PORT = config.getInt("mysql.port");
		DATABASE = config.getString("mysql.database");
		USERNAME = config.getString("mysql.username");
		PASSWORD = config.getString("mysql.password");

		connect();

		loadDatabases();
	}

	private void connect() {
		datasource = new HikariDataSource();
		String connectionUrl = "jdbc:mysql://" + HOSTNAME + ":" + PORT + "/" + DATABASE;

		datasource.setMaximumPoolSize(10);
		datasource.setDriverClassName("com.mysql.jdbc.Driver");
		datasource.setJdbcUrl(connectionUrl);
		datasource.setUsername(USERNAME);
		datasource.setPassword(PASSWORD);
	}

	private void loadDatabases() {
		new PlayerDB();
	}

	public static HikariDataSource getDatasource() {
		return datasource;
	}

	public void close() {
		if (datasource.isClosed() != true) {
			datasource.close();
		}
	}
}
