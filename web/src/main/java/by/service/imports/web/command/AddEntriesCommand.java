package by.service.imports.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.service.imports.util.PropertiesManager;

public class AddEntriesCommand implements ActionCommand{
	
	private static final Logger LOG = Logger.getLogger(AddEntriesCommand.class);
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		LOG.debug("Run AddEntriesCommand execute method");
		return PropertiesManager.PAGE.getProperty("path.page.add.entries");
	}
}
