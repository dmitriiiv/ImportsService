package by.service.imports.dao;

import by.service.imports.dao.impl.EntryDaoImpl;
import by.service.imports.entity.Entry;
import by.service.imports.util.Constants;

public class App {

	public static void main( String[] args )
    {
		System.out.println("Start");
        EntryDao dao = EntryDaoImpl.getInstance();
        for(int i = 1; i<20;i++) {
        	String text = "test" + i;
        	Entry newEntry = new Entry(text, text, text, text, text);
        	dao.create(newEntry);
        }
        System.out.println("Order by " + Constants.PARAM_ENTRY_NAME + " " + dao.readAndOrderBy(Constants.PARAM_ENTRY_ID, 0, 5));
        System.out.println("End");
    }
}
