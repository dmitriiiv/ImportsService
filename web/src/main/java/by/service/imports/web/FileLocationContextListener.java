package by.service.imports.web;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import by.service.imports.util.Constants;

public class FileLocationContextListener implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		String rootPath = System.getProperty("catalina.home");
		ServletContext context = servletContextEvent.getServletContext();
		String relativePath = context.getInitParameter("tempfile.dir");
		File file = new File(rootPath + File.separator + relativePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		context.setAttribute(Constants.ATTRIB_NAME_FILES_DIR_FILE, file);
		context.setAttribute(Constants.ATTRIB_NAME_FILES_DIR, rootPath + File.separator + relativePath);
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
	}

}
