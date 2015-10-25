package by.service.imports.dao;

import java.util.List;

import by.service.imports.entity.Entry;

public interface EntryDao extends CrudDao<Entry,Integer>{

	Entry readByNameAndSurname(String name, String surname);
	
	int quantity();
	
	List<Entry> readAndOrderBy(String orderBy, int beginIndex, int quantity);
}
