package by.service.imports.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.service.imports.dao.impl.EntryDaoImpl;
import by.service.imports.service.EntryService;
import by.service.imports.service.impl.EntryServiceImpl;
import by.service.imports.util.Constants;
import by.service.imports.util.PropertiesManager;

public class PageCommand implements ActionCommand{

	private static final Logger LOG = Logger.getLogger(PageCommand.class);
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		LOG.debug("Run PageCommand execute method");
		EntryService entryService = EntryServiceImpl.getInstance(EntryDaoImpl.getInstance());
		int pageNumber = Integer.parseInt(request.getParameter(Constants.ATTRIB_NAME_PAGE));
		String button = request.getParameter(Constants.ATTRIB_NAME_BUTTON);
		if(button != null) {
			if (button.equals(Constants.ATTRIB_NAME_NEXT_BUTTON)) {
				pageNumber++;
			} else if (button.equals(Constants.ATTRIB_NAME_PREV_BUTTON)) {
				pageNumber--;
			}
		}
		int entryQuantity;
		int beginIndex = 0;
		if (pageNumber != 1) {
			beginIndex = 6 * (pageNumber - 1);
		}
		String orderBy = request.getParameter(Constants.ATTRIB_NAME_ORDER_BY);
		synchronized (entryService) {
			entryQuantity = entryService.quantity();
			request.setAttribute(Constants.ATTRIB_NAME_ENTRIES, entryService.findEntryAndOrderBy(orderBy, beginIndex, 6));
		}
		request.setAttribute(Constants.ATTRIB_NAME_NEXT_BUTTON_ACTIVE, isActiveNextButton(entryQuantity, pageNumber));
		request.setAttribute(Constants.ATTRIB_NAME_PREV_BUTTON_ACTIVE, isActivePrevButton(pageNumber));
		request.setAttribute(Constants.ATTRIB_NAME_PAGE, pageNumber);
		request.setAttribute(Constants.ATTRIB_NAME_ORDER_BY, orderBy);
		return PropertiesManager.PAGE.getProperty("path.page.entries");
	}

	private String isActiveNextButton(int entryQuantity, int pageNumber) {
		if (entryQuantity > 6 * pageNumber) {
			return Constants.ATTRIB_NAME_ACTIVE;
		} else {
			return Constants.ATTRIB_NAME_DISABLED;
		}
	}
	
	private String isActivePrevButton(int pageNumber) {
		if (pageNumber != 1) {
			return Constants.ATTRIB_NAME_ACTIVE;
		} else {
			return Constants.ATTRIB_NAME_DISABLED;
		}
	}

}
