package by.service.imports.gm;

import java.util.List;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;

import by.service.imports.dao.impl.EntryDaoImpl;
import by.service.imports.entity.Entry;
import by.service.imports.service.EntryService;
import by.service.imports.service.impl.EntryServiceImpl;
import by.service.imports.service.parser.FileParser;
import by.service.imports.service.parser.impl.CsvFileParser;


public class FileMessageConsumer extends Thread{

	private static final Logger LOG = Logger.getLogger(FileMessageConsumer.class);
	private static FileMessageConsumer instance;
	
	private FileMessageConsumer() {
	}
	
	public synchronized static FileMessageConsumer getInstance() {
		if(instance == null) {
			instance = new FileMessageConsumer();
		}
		return instance;
	}
	
	@Override
	public void run() {
		LOG.debug("Run run method");
		Connection connection = null;
		Session session = null;
		MessageConsumer consumer = null;
		try {
			ConnectionFactory cf = new com.sun.messaging.ConnectionFactory();
			connection = cf.createConnection();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createTopic("FilePath");
			consumer = session.createConsumer(destination);
			connection.start();
			consumeMessage(consumer);
		} catch (JMSException e) {
			LOG.error(e);
		} finally {
			close(consumer, session, connection);
		}
	}

	private void consumeMessage(MessageConsumer consumer) throws JMSException {
		LOG.debug("Start consumer");
		do {
			TextMessage m = (TextMessage) consumer.receive();
			FileParser parser = new CsvFileParser(m.getText());
			List<Entry> entries = parser.parse();
			EntryService entryService = new EntryServiceImpl(EntryDaoImpl.getInstance());
			synchronized (entryService) {
				for (Entry entry : entries) {
					Entry entryInDB = entryService.findByNameAndSurname(entry.getName(), entry.getSurname());
					if ( entryInDB != null) {
						entry.setId(entryInDB.getId());
						entryService.updateEntry(entry);
					} else {
						entryService.addEntry(entry);
					}
				}
			}
		} while (true);
	}
	
	private static void close(MessageConsumer consumer, Session session, Connection connection) {
        LOG.debug("Run close method");
		try {
			if (consumer != null) {
                consumer.close();
            }
            if (session != null) {
                session.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (JMSException e) {
        	LOG.error("Can not close", e);
        }
    }
}
