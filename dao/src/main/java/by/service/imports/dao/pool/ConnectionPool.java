package by.service.imports.dao.pool;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

import by.service.imports.util.PropertiesManager;

public class ConnectionPool {
	
	private static final Logger LOG = Logger.getLogger(ConnectionPool.class);
	private static ConnectionPool instance;
    private BasicDataSource dataSource;

    private ConnectionPool() {
    	LOG.debug("Run ConnectionPool method");
        dataSource = new BasicDataSource();
        dataSource.setDriverClassName(PropertiesManager.POOL.getProperty("db.driver"));
        dataSource.setUrl(PropertiesManager.POOL.getProperty("db.url"));
        dataSource.setUsername(PropertiesManager.POOL.getProperty("db.user"));
        dataSource.setPassword(PropertiesManager.POOL.getProperty("db.password"));
    }

    public synchronized static ConnectionPool getInstance() {
    	LOG.debug("Run getInstance method");
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
    	LOG.debug("Run getConnection method");
        return dataSource.getConnection();
    }
}
