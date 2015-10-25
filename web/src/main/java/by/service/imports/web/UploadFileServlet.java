package by.service.imports.web;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import by.service.imports.gm.FileMessageProducer;
import by.service.imports.util.Constants;
import by.service.imports.util.PropertiesManager;


public class UploadFileServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(UploadFileServlet.class);
	private ServletFileUpload servletFileUpload = null;
	
	@Override
	public void init() throws ServletException {
		DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
		File filesDir = (File) getServletContext().getAttribute(Constants.ATTRIB_NAME_FILES_DIR_FILE);
		fileItemFactory.setRepository(filesDir);
		servletFileUpload = new ServletFileUpload(fileItemFactory);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOG.debug("Run doPost method");
		if (!ServletFileUpload.isMultipartContent(request)) {
			throw new ServletException("Content type is not multipart/form-data");
		}
		if (parseFileRequest(request, response)) {
			try {
				response.sendRedirect("controller?command=success_import");
			} catch (IOException e) {
				LOG.error("Exception redirect", e);
			}
		} else {
			request.setAttribute(Constants.ATTRIB_NAME_ERROR_MESSAGE, "File format has to be .csv");
			String nextPage = PropertiesManager.PAGE.getProperty("path.page.add.entries");
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(nextPage);
            requestDispatcher.forward(request, response);
		}
	}
	
	private boolean parseFileRequest(HttpServletRequest request, HttpServletResponse response) {
		LOG.debug("Run parseFileRequest method");
		boolean isSuccess = false;
		try {
			List<FileItem> fileItemsList = servletFileUpload.parseRequest(request);
			Iterator<FileItem> fileItemsIterator = fileItemsList.iterator();
			while (fileItemsIterator.hasNext()) {
				FileItem fileItem = fileItemsIterator.next();
				if (isFormatCsv(fileItem)) {
					String filePath = getServletContext().getAttribute(Constants.ATTRIB_NAME_FILES_DIR) + File.separator;
					File file = new File(filePath + fileItem.getName());
					int i = 1;
					while (file.exists()) {
						file = getNewFile(fileItem, filePath, i);
						i++;
					}
					fileItem.write(file);
					sendPathOnServer(file);
					isSuccess = true;
				}
			}
			FileMessageProducer.close();
		} catch (FileUploadException e) {
			LOG.error("Exception in uploading file", e);
		} catch (Exception e) {
			LOG.error("Exception in writting file", e);
		}
		return isSuccess;
	}
	
	private File getNewFile(FileItem fileItem, String filePath, int counter) {
		LOG.debug("Run getNewFile method");
		String fileName = fileItem.getName().replace(".csv", "");
		String newFileName = fileName + "(" + counter + ")" + ".csv";
		return new File(filePath + newFileName);
	}
	
	private boolean isFormatCsv(FileItem fileItem) {
		LOG.debug("Run isFormatCsv");
		return fileItem.getName().matches(Constants.FILE_FORMAT_PATTERN);
	}
	
	private void sendPathOnServer(File file) {
		FileMessageProducer messageProducer = new FileMessageProducer();
		messageProducer.sendMessage(file.getPath());
	}
}
