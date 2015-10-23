package by.service.imports.dao.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import by.service.imports.dao.CrudDao;
import by.service.imports.dao.pool.ConnectionPool;
import by.service.imports.util.Constants;
import by.service.imports.util.DBUtil;

public abstract class AbstractDao<E, PK extends Serializable> implements CrudDao<E, PK>{
	
	private static final Logger LOG = Logger.getLogger(AbstractDao.class);
	
	@Override
	public boolean create(E object) {
		LOG.debug("Run create method, object=" + object);
		Connection connection = null;
		PreparedStatement statement = null;
		boolean isCreated = false;
		try{
			connection = ConnectionPool.getInstance().getConnection();
			statement = connection.prepareStatement(getSql(Constants.METHOD_NAME_CREATE));
			setParameters(Constants.METHOD_NAME_CREATE, statement, object);
			statement.executeUpdate();
			isCreated = true;
		} catch (SQLException e){
			LOG.error("Can not create" + object, e);
		} finally {
			DBUtil.close(connection, statement);
		}
		LOG.debug(object + "was created");
		return isCreated;
	}

	@Override
	public E read(PK id) {
		LOG.debug("Run read method, id=" + id);
		E result = null;
		Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
        	connection = ConnectionPool.getInstance().getConnection();
			statement = connection.prepareStatement(getSql(Constants.METHOD_NAME_READ));
			setParameters(Constants.METHOD_NAME_READ, statement, id);
			resultSet = statement.executeQuery();
			result = create(resultSet);
        } catch (SQLException e) {
        	LOG.error("Can not read by id=" + id, e);
        } finally {
            DBUtil.close(connection, statement, resultSet);
        }
        LOG.debug(result + " is returned");
		return result;
	}

	@Override
	public boolean update(E object) {
		LOG.debug("Run update method, object=" + object);
		Connection connection = null;
		PreparedStatement statement = null;
		boolean isUpdated = false;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			statement = connection.prepareStatement(getSql(Constants.METHOD_NAME_UPDATE));
			setParameters(Constants.METHOD_NAME_UPDATE, statement, object);
			statement.executeUpdate();
			isUpdated = true;
		} catch (SQLException e) {
			LOG.error("Can not update, object=" + object, e);
		} finally {
			DBUtil.close(connection, statement);
		}
		LOG.debug(object + " is updated");
		return isUpdated;
	}

	@Override
	public boolean delete(PK id) {
		LOG.debug("Run delete method, id=" + id);
		Connection connection = null;
		PreparedStatement statement = null;
		boolean isRemoved = false;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			statement = connection.prepareStatement(getSql(Constants.METHOD_NAME_DELETE));
			setParameters(Constants.METHOD_NAME_DELETE, statement, id);
			statement.executeUpdate();
			isRemoved = true;
		} catch (SQLException e) {
			LOG.error("Can not delete object with id=" + id, e);
		} finally {
			DBUtil.close(connection, statement);
		}
		LOG.debug("Object with id=" + id + " is removed");
		return isRemoved;
	}
	
	@Override
	public List<E> readAll() {
		LOG.debug("Run readAll method");
		List<E> result = null;
		Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try{
        	connection = ConnectionPool.getInstance().getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(getSql(Constants.METHOD_NAME_READ_ALL));
			result = createList(resultSet);
        } catch (SQLException e) {
        	LOG.error("Cannot read all", e);
        } finally {
            DBUtil.close(connection, statement, resultSet);
        }
        LOG.debug("Read all good");
        return result != null ? result : Collections.<E>emptyList();
	}
	
	protected abstract void setParameters(String methodName, Statement statement, E object) throws SQLException;
	
	protected abstract void setParameters(String methodName, Statement statement, PK id) throws SQLException;

	protected abstract String getSql(String methodName);
	
	protected abstract E create(ResultSet resultSet) throws SQLException;
	
	protected abstract List<E> createList(ResultSet resultSet) throws SQLException;
	
}
