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
	private static Connection connection;
	private static Session session;
	private static MessageProducer producer;

	public FileMessageProducer(){
		try{
			ConnectionFactory cf = new com.sun.messaging.ConnectionFactory();
			connection = cf.createConnection();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createTopic("FilePath");
			producer = session.createProducer(destination);
			connection.start();
		} catch (JMSException e) {
			LOG.error(e);
		}
	}
	
	public void sendMessage(String filePath) {
		LOG.debug("Run sendMessage method");
		try {
			TextMessage message = session.createTextMessage();
			message.setText(filePath);
			producer.send(message);
		} catch (JMSException e) {
			LOG.error(e);
		} 
	}
	
	public static void close() {
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
