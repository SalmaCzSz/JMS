package jmsfundamentals;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.activemq.artemis.jms.client.*;

public class RequestReplyDemo {

	public static void main(String[] args) throws NamingException{
		InitialContext initialContext = new InitialContext();
		Queue requestQueue = (Queue) initialContext.lookup("queue/requestQueue");
		Queue replyQueue = (Queue) initialContext.lookup("queue/replyQueue");
		
		try {
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
			JMSContext jmsContext = connectionFactory.createContext();
			JMSProducer producer = jmsContext.createProducer();
			producer.send(requestQueue, "Arise Awoke and stop not till the goal is reached");
			
			JMSConsumer consumer = jmsContext.createConsumer(requestQueue);
			String messageReceived = consumer.receiveBody(String.class);
			System.out.println(messageReceived);
			
			JMSProducer replyProducer = jmsContext.createProducer();
			replyProducer.send(replyQueue, "You are awesome!!");
			
			JMSConsumer replyConsumer = jmsContext.createConsumer(replyQueue);
			System.out.println(replyConsumer.receiveBody(String.class));
		} catch(Exception e) {
			
		}
	}

}