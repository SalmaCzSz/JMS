package jmsfundamentals.messageestructure;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class MessagePriority {

	public static void main(String[] args) throws NamingException{
		InitialContext initialContext = new InitialContext();
		Queue queue = (Queue) initialContext.lookup("queue/myQueue");
		
		try {
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
			JMSContext jmsContext = connectionFactory.createContext();
			JMSProducer producer = jmsContext.createProducer();
			
			String[] messages = new String[3];
			messages[0] = "Message one";
			messages[1] = "Message two";
			messages[2] = "Message three";
			
			producer.setPriority(3);
			producer.send(queue, messages[0]);
			producer.setPriority(1);
			producer.send(queue, messages[1]);
			producer.setPriority(9);
			producer.send(queue, messages[2]);
			
			JMSConsumer consumer = jmsContext.createConsumer(queue);
			
			for(int i = 0; i < 3; i++) {
				System.out.println(consumer.receiveBody(String.class));
			}
		}catch(Exception e) {
			
		}
	}
}
