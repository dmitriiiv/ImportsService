package by.service.imports.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import by.service.imports.web.command.ActionCommand;
import by.service.imports.web.command.factory.ActionFactory;

public class Controller extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(Controller.class);

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		String log4j = config.getInitParameter("log4j");
		String path = getServletContext().getRealPath(log4j);
		PropertyConfigurator.configure(path);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LOG.debug("Run doGet method");
		processRequest(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LOG.debug("Run doPost method");
		processRequest(req, resp);
	}
	
	private void processRequest(HttpServletRequest request, HttpServletResponse response) {
		LOG.debug("Run processRequest method");
		ActionCommand command = ActionFactory.defineCommand(request);
        try {
            String nextPage = command.execute(request, response);
            if (!response.isCommitted()) {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(nextPage);
                requestDispatcher.forward(request, response);
            }
        } catch (ServletException | IOException e) {
            LOG.error("Controller exception in processRequest method", e);
        } 
	}
}
