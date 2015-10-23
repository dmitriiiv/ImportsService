package by.service.imports.dao;

import by.service.imports.dao.impl.EntryDaoImpl;
import by.service.imports.entity.Entry;
import by.service.imports.util.Constants;

public class App {

	public static void main( String[] args )
    {
		System.out.println("Start");
        EntryDao dao = EntryDaoImpl.getInstance();
        //Entry newEntry = new Entry("test", "test", "test", "test", "test");
        System.out.println("Order by " + Constants.PARAM_ENTRY_NAME + " " + dao.readAndOrderBy(Constants.PARAM_ENTRY_ID, 0, 5));
        //if(dao.delete(1)) {
        	System.out.println(dao.readAll());
        //}
        System.out.println("End");
    }
}
