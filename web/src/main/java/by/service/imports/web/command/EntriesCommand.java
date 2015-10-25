package by.service.imports.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.service.imports.dao.impl.EntryDaoImpl;
import by.service.imports.service.EntryService;
import by.service.imports.service.impl.EntryServiceImpl;
import by.service.imports.util.Constants;
import by.service.imports.util.PropertiesManager;

public class EntriesCommand implements ActionCommand{

	private static final Logger LOG = Logger.getLogger(EntriesCommand.class);
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		LOG.debug("Run EntriesCommand execute method");
		EntryService entryService = EntryServiceImpl.getInstance(EntryDaoImpl.getInstance());
		request.setAttribute(Constants.ATTRIB_NAME_ORDER_BY, Constants.PARAM_ENTRY_ID);
		request.setAttribute(Constants.ATTRIB_NAME_PAGE, 1);
		request.setAttribute(Constants.ATTRIB_NAME_PREV_BUTTON_ACTIVE, Constants.ATTRIB_NAME_DISABLED);
		int entryQuantity;
		synchronized (entryService) {
			entryQuantity = entryService.quantity();
			request.setAttribute(Constants.ATTRIB_NAME_ENTRIES, entryService.findEntryAndOrderBy(Constants.PARAM_ENTRY_ID, 0, 6));
		}
		if (entryQuantity > 6) {
			request.setAttribute(Constants.ATTRIB_NAME_NEXT_BUTTON_ACTIVE, Constants.ATTRIB_NAME_ACTIVE);
		} else {
			request.setAttribute(Constants.ATTRIB_NAME_NEXT_BUTTON_ACTIVE, Constants.ATTRIB_NAME_DISABLED);
		}
		return PropertiesManager.PAGE.getProperty("path.page.entries");
	}
}
