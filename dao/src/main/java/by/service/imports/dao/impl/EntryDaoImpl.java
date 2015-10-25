package by.service.imports.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import by.service.imports.dao.EntryDao;
import by.service.imports.dao.pool.ConnectionPool;
import by.service.imports.entity.Entry;
import by.service.imports.util.Constants;
import by.service.imports.util.DBUtil;
import by.service.imports.util.PropertiesManager;

public class EntryDaoImpl extends AbstractDao<Entry, Integer> implements EntryDao {
	
	private static EntryDaoImpl instance;
	private static final Logger LOG = Logger.getLogger(EntryDaoImpl.class);
	
	private EntryDaoImpl() {
	}
	
	public synchronized static EntryDaoImpl getInstance() {
		LOG.debug("Run getInstance method");
		if (instance == null) {
			instance = new EntryDaoImpl();
		}
		return instance;
	}

	@Override
	protected void setParameters(String methodName, Statement statement, Entry object) throws SQLException {
		LOG.debug("Run setParameters method");
		if(methodName.equals(Constants.METHOD_NAME_CREATE)) {
			((PreparedStatement) statement).setString(1, object.getName());
			((PreparedStatement) statement).setString(2, object.getSurname());
			((PreparedStatement) statement).setString(3, object.getLogin());
			((PreparedStatement) statement).setString(4, object.getEmail());
			((PreparedStatement) statement).setString(5, object.getPhoneNumber());
		} else if(methodName.equals(Constants.METHOD_NAME_UPDATE)) {
			((PreparedStatement) statement).setString(1, object.getName());
			((PreparedStatement) statement).setString(2, object.getSurname());
			((PreparedStatement) statement).setString(3, object.getLogin());
			((PreparedStatement) statement).setString(4, object.getEmail());
			((PreparedStatement) statement).setString(5, object.getPhoneNumber());
			((PreparedStatement) statement).setInt(6, object.getId());
		}
	}

	@Override
	protected void setParameters(String methodName, Statement statement, Integer id) throws SQLException {
		LOG.debug("Run setParameters method");
		if(methodName.equals(Constants.METHOD_NAME_READ)) {
			((PreparedStatement) statement).setInt(1, id);
		} else if(methodName.equals(Constants.METHOD_NAME_DELETE)) {
			((PreparedStatement) statement).setInt(1, id);
		}
	}

	@Override
	protected String getSql(String methodName) {
		LOG.debug("Run setParameters method");
		if(methodName.equals(Constants.METHOD_NAME_CREATE)) {
			return PropertiesManager.SQL_REQUEST.getProperty("sql.create.entry");
		} else if(methodName.equals(Constants.METHOD_NAME_READ)) {
			return PropertiesManager.SQL_REQUEST.getProperty("sql.read.entry");
		} else if(methodName.equals(Constants.METHOD_NAME_READ_ALL)) {
			return PropertiesManager.SQL_REQUEST.getProperty("sql.read.all.entry");
		} else if(methodName.equals(Constants.METHOD_NAME_UPDATE)) {
			return PropertiesManager.SQL_REQUEST.getProperty("sql.update.entry");
		} else if(methodName.equals(Constants.METHOD_NAME_DELETE)) {
			return PropertiesManager.SQL_REQUEST.getProperty("sql.delete.entry");
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	protected Entry create(ResultSet resultSet) throws SQLException {
		LOG.debug("Run create method");
		Entry entry = null;
		if(resultSet.next()) {
			entry = new Entry();
			entry.setId(resultSet.getInt(Constants.PARAM_ENTRY_ID));
			entry.setName(resultSet.getString(Constants.PARAM_ENTRY_NAME));
			entry.setSurname(resultSet.getString(Constants.PARAM_ENTRY_SURNAME));
			entry.setLogin(resultSet.getString(Constants.PARAM_ENTRY_LOGIN));
			entry.setEmail(resultSet.getString(Constants.PARAM_ENTRY_EMAIL));
			entry.setPhoneNumber(resultSet.getString(Constants.PARAM_ENTRY_PHONE));
		}
		return entry;
	}
	
	@Override
	protected List<Entry> createList(ResultSet resultSet) throws SQLException {
		LOG.debug("Run createList method");
		List<Entry> entries = new ArrayList<>();
		while (resultSet.next()) {
			Entry entry = new Entry();
			entry.setId(resultSet.getInt(Constants.PARAM_ENTRY_ID));
			entry.setName(resultSet.getString(Constants.PARAM_ENTRY_NAME));
			entry.setSurname(resultSet.getString(Constants.PARAM_ENTRY_SURNAME));
			entry.setLogin(resultSet.getString(Constants.PARAM_ENTRY_LOGIN));
			entry.setEmail(resultSet.getString(Constants.PARAM_ENTRY_EMAIL));
			entry.setPhoneNumber(resultSet.getString(Constants.PARAM_ENTRY_PHONE));
			entries.add(entry);
		}
		return entries;
	}

	@Override
	public Entry readByNameAndSurname(String name, String surname) {
		LOG.debug("Run readByLogin method, name=" + name + ", surname=" + surname);
		Entry entry = null;
		Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
        	connection = ConnectionPool.getInstance().getConnection();
			statement = connection.prepareStatement(PropertiesManager.SQL_REQUEST.getProperty("sql.read.entry.by.name.and.surname"));
			statement.setString(1, name);
			statement.setString(2, surname);
			resultSet = statement.executeQuery();
			entry = create(resultSet);
        } catch (SQLException e) {
        	LOG.error("Can not read by name=" + name + ", surname=" + surname, e);
        } finally {
            DBUtil.close(connection, statement, resultSet);
        }
        LOG.debug(entry + " is returned");
		return entry;
	}
	
	@Override
	public List<Entry> readAndOrderBy(String orderBy, int beginIndex, int quantity) {
		LOG.debug("Run readAndOrderBy method, orderBy=" + orderBy + ", beginInedx=" + beginIndex + ", quantity=" + quantity);
		List<Entry> entries = null;
		Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
        	connection = ConnectionPool.getInstance().getConnection();
			statement = connection.prepareStatement(getSqlRequest(orderBy));
			statement.setInt(1, beginIndex);
			statement.setInt(2, quantity);
			resultSet = statement.executeQuery();
			entries = createList(resultSet);
        } catch (SQLException e) {
        	e.printStackTrace();
        	LOG.error("Can not read entry, orderBy=" + orderBy + ", beginInedx=" + beginIndex + ", quantity=" + quantity, e);
        } finally {
            DBUtil.close(connection, statement, resultSet);
        }
        LOG.debug("Read entry good");
        return entries != null ? entries : Collections.<Entry>emptyList();
	}

	@Override
	public int quantity() {
		LOG.debug("Run quantity method");
		int quantity = 0;
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try{
			connection = ConnectionPool.getInstance().getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(PropertiesManager.SQL_REQUEST.getProperty("sql.entry.quantity"));
			quantity = getQuantity(resultSet);
		} catch (SQLException e) {
			LOG.error("Can not calculete quantity entry", e);
		} finally {
			DBUtil.close(connection, statement, resultSet);
		}
		return quantity;
	}

	private String getSqlRequest(String orderBy) {
		LOG.debug("Run getSqlRequest method");
		if(orderBy.equals(Constants.PARAM_ENTRY_ID)) {
			return PropertiesManager.SQL_REQUEST.getProperty("sql.read.entry.and.order.by.id");
		} else if(orderBy.equals(Constants.PARAM_ENTRY_NAME)) {
			return PropertiesManager.SQL_REQUEST.getProperty("sql.read.entry.and.order.by.name");
		} else if(orderBy.equals(Constants.PARAM_ENTRY_SURNAME)) {
			return PropertiesManager.SQL_REQUEST.getProperty("sql.read.entry.and.order.by.surname");
		} else if(orderBy.equals(Constants.PARAM_ENTRY_LOGIN)) {
			return PropertiesManager.SQL_REQUEST.getProperty("sql.read.entry.and.order.by.login");
		} else if(orderBy.equals(Constants.PARAM_ENTRY_EMAIL)) {
			return PropertiesManager.SQL_REQUEST.getProperty("sql.read.entry.and.order.by.email");
		} else if(orderBy.equals(Constants.PARAM_ENTRY_PHONE)) {
			return PropertiesManager.SQL_REQUEST.getProperty("sql.read.entry.and.order.by.phone");
		} else {
			throw new IllegalArgumentException(); 
		}
	}
	
	private int getQuantity(ResultSet resultSet) throws SQLException {
		LOG.debug("Run getQuantity method");
		int quantity = 0;
        while (resultSet.next()) {
            quantity = resultSet.getInt(Constants.PARAM_NAME_COUNT);
        }
        return quantity;
	}
}
