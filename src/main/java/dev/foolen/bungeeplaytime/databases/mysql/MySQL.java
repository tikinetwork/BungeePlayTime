package dev.foolen.bungeeplaytime.databases.mysql;

import java.util.Properties;

import com.zaxxer.hikari.HikariConfig;
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
		Properties props = new Properties();
		props.setProperty("jdbcUrl", "jdbc:mysql://" + HOSTNAME + ":" + PORT + "/" + DATABASE);
		props.setProperty("username", USERNAME);
		props.setProperty("password", PASSWORD);
		
		props.setProperty("dataSource.dataSourceClassName", "com.mysql.cj.jdbc.MysqlDataSource");
		props.setProperty("dataSource.maximumPoolSize", "10");
		props.setProperty("dataSource.cachePrepStmts", "true");
		props.setProperty("dataSource.prepStmtCacheSize", "250");
		props.setProperty("dataSource.prepStmtCacheSqlLimit", "2048");
		props.setProperty("dataSource.useServerPrepStmts", "true");
		props.setProperty("dataSource.useLocalSessionState", "true");
		props.setProperty("dataSource.rewriteBatchedStatements", "true");
		props.setProperty("dataSource.cacheResultSetMetadata", "true");
		props.setProperty("dataSource.cacheServerConfiguration", "true");
		props.setProperty("dataSource.elideSetAutoCommits", "true");
		props.setProperty("dataSource.maintainTimeStats", "false");
				
		HikariConfig hconfig = new HikariConfig(props);
		
		datasource = new HikariDataSource(hconfig);
		datasource.setLeakDetectionThreshold(60 * 1000); // REMOVE
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
