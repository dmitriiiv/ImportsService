package by.service.imports.dao;

import java.io.Serializable;
import java.util.List;

public interface CrudDao<E, PK extends Serializable> {
	
	boolean create(E e);
	
	E read(PK id);
	
	List<E> readAll();
	
	boolean update(E e);
	
	boolean delete(PK id);
	
}
