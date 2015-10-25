package by.service.imports.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import by.service.imports.dao.EntryDao;
import by.service.imports.entity.Entry;
import by.service.imports.service.EntryService;

public class EntryServiceImpl implements EntryService{
	
	private static final Logger LOG = Logger.getLogger(EntryServiceImpl.class);
	private static EntryServiceImpl instance;
	private EntryDao entryDao;
	
	private EntryServiceImpl(EntryDao entryDao) {
		LOG.debug("Run EntryService constructor");
		this.entryDao = entryDao;
	}
	
	public synchronized static EntryServiceImpl getInstance(EntryDao entryDao){
		if (instance == null) {
			instance = new EntryServiceImpl(entryDao);
		}
		return instance;
	}

	@Override
	public List<Entry> findEntryAndOrderBy(String orderBy, int beginIndex, int quantity) {
		LOG.debug("Run findEntryAndOrderBy method");
		return entryDao.readAndOrderBy(orderBy, beginIndex, quantity);
	}

	@Override
	public int quantity() {
		LOG.debug("Run quantity method");
		return entryDao.quantity();
	}

	@Override
	public boolean addEntry(Entry entry) {
		LOG.debug("Run addEntry method");
		return entryDao.create(entry);
	}

	@Override
	public boolean updateEntry(Entry entry) {
		LOG.debug("Run updateEntry method");
		return entryDao.update(entry);
	}

	@Override
	public Entry findByNameAndSurname(String name, String surname) {
		return entryDao.readByNameAndSurname(name, surname);
	}

}
