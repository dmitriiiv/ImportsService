package by.service.imports.service.parser.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import by.service.imports.entity.Entry;
import by.service.imports.service.parser.FileParser;

public class CsvFileParser implements FileParser{
	
	private final String FILE_PATH;
	private final String csvSplitBy = ",";
	
	public CsvFileParser(String filePath) {
		FILE_PATH = filePath;
	}

	public List<Entry> parse() {
		List<Entry> entries = new ArrayList<>();
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new FileReader(FILE_PATH));
			String line = null;
			while ((line = bufferedReader.readLine()) != null){
				entries.add(createEntry(line.split(csvSplitBy)));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return entries;
	}
	
	private Entry createEntry(String[] valies){
		return new Entry(valies[0], valies[1], valies[2], valies[3], valies[4]);
	}
}
