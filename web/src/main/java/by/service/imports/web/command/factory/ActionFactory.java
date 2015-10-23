package by.service.imports.web.command.factory;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import by.service.imports.web.command.ActionCommand;
import by.service.imports.web.command.client.CommandEnum;

public class ActionFactory {
	
	private static final Logger LOG = Logger.getLogger(ActionFactory.class);
	
	public static ActionCommand defineCommand(HttpServletRequest request) {
		LOG.debug("Run defineCommand method");
        ActionCommand current = null;
        String action = request.getParameter("command");
        try {
            CommandEnum currentEnum = CommandEnum.valueOf(action.toUpperCase());
            current = currentEnum.getCurrentCommand();
        } catch (IllegalArgumentException e) {
        	LOG.error("ActionFactory exception in defineCommand method", e);
        }
        return current;
	}
}
