package by.service.imports.service;

import java.util.List;

import by.service.imports.entity.Entry;

public interface EntryService {
	
	int quantity();
	
	List<Entry> findEntryAndOrderBy(String orderBy, int beginIndex, int quantity);

}
