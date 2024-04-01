package jmsfundamentals.messageestructure;

import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.activemq.artemis.jms.client.*;

public class MessageExpirationDemo {

	public static void main(String[] args) throws NamingException{
		InitialContext initialContext = new InitialContext();
		Queue queue = (Queue) initialContext.lookup("queue/myQueue");
		Queue expiryQueue = (Queue) initialContext.lookup("queue/expiryQueue");
		
		try {
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
			JMSContext jmsContext = connectionFactory.createContext();
			
			JMSProducer producer = jmsContext.createProducer();
			producer.setTimeToLive(2000);
			producer.send(queue, "Hi c:");
			Thread.sleep(5000);
			
			Message messageReceived = jmsContext.createConsumer(queue).receive(5000);
			System.out.println(messageReceived);
			
			System.out.println(jmsContext.createConsumer(expiryQueue).receiveBody(String.class));
		} catch(Exception e) {
			
		}
	}

}
