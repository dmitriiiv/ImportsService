package by.service.imports.util;

import java.util.ResourceBundle;

public enum PropertiesManager {
	
	PAGE {
		@Override
		public String getProperty(String key) {
			return ResourceBundle.getBundle("by.service.imports.web.PageConfig").getString(key);
		}
	},
	POOL {
		@Override
		public String getProperty(String key) {
			return ResourceBundle.getBundle("by.service.imports.dao.DataBase").getString(key);
		}
	}, 
	SQL_REQUEST {
		@Override
		public String getProperty(String key) {
			return ResourceBundle.getBundle("by.service.imports.dao.SqlRequests").getString(key);
		}
	};

	public abstract String getProperty(String key);
}
