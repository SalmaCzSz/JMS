package jmsfundamentals;

import javax.naming.InitialContext;
import javax.jms.ConnectionFactory;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Connection;
import javax.jms.Queue;
import javax.jms.MessageProducer;

public class QueueBrowserDemo {
	public static void main(String[] args) {
		InitialContext initialContext = null;
		Connection connection = null;
		
		try {
			// Configuración de la conexión
			initialContext = new InitialContext(); 
			ConnectionFactory connectionFactory = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
			connection = connectionFactory.createConnection();
			Session session = connection.createSession(); 
			Queue queue = (Queue) initialContext.lookup("queue/myQueue");
			MessageProducer producer = session.createProducer(queue);
			TextMessage message = session.createTextMessage("I'm the creator of my destinity");
			producer.send(message);
			System.out.println("Message Sent: " + message.getText());
			
			// Consumidor de mensajes
			MessageConsumer consumer =  session.createConsumer(queue);
			connection.start();
			TextMessage messageReceived = (TextMessage) consumer.receive(5000);
			System.out.println("Message Received: " + messageReceived.getText());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(initialContext != null) {
				try {
					initialContext.close();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			
			if(connection != null) {
				try {
					connection.close();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		
	}
}