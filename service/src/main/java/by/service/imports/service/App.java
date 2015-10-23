package by.service.imports.service;

import java.util.List;

import by.service.imports.entity.Entry;
import by.service.imports.service.parser.FileParser;
import by.service.imports.service.parser.impl.CsvFileParser;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        FileParser parser = new CsvFileParser("./src/main/java/file.csv");
        List<Entry> entries = parser.parse();
        for(Entry entry : entries) {
        	System.out.println(entry);
        }
    }
}
