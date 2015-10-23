package by.service.imports.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

public class DBUtil {

	private static final Logger LOG = Logger.getLogger(DBUtil.class);
	
	public static void close(Connection connection, Statement statement, ResultSet resultSet) {
        LOG.debug("Run close method");
		try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
        	LOG.error("Can not close", e);
        }
    }

    public static void close(Connection connection, Statement statement) {
        close(connection, statement, null);
    }
}
