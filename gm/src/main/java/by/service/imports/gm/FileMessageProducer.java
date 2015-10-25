package by.service.imports.gm;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;

public class FileMessageProducer {

private static final Logger LOG = Logger.getLogger(FileMessageProducer.class);
	
	public static void sendMessage(String filePath) {
		LOG.debug("Run sendMessage method");
		Connection connection = null;
		Session session = null;
		MessageProducer producer = null;
		try {
			ConnectionFactory cf = new com.sun.messaging.ConnectionFactory();
			connection = cf.createConnection();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createTopic("FilePath");
			producer = session.createProducer(destination);
			connection.start();
			TextMessage message = session.createTextMessage();
			message.setText(filePath);
			producer.send(message);
		} catch (JMSException e) {
			LOG.error(e);
		} finally {
			close(producer, session, connection);
		}
	}
	
	private static void close(MessageProducer producer, Session session, Connection connection) {
        LOG.debug("Run close method");
		try {
			if (producer != null) {
                producer.close();
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
