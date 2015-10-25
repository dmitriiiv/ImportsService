package by.service.imports.service;

import java.util.List;

import by.service.imports.entity.Entry;

public interface EntryService {
	
	int quantity();
	
	List<Entry> findEntryAndOrderBy(String orderBy, int beginIndex, int quantity);

	boolean addEntry(Entry entry);
	
	boolean updateEntry(Entry entry);
	
	Entry findByNameAndSurname(String name, String surname);
	
}
