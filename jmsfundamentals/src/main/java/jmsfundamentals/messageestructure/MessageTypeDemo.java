package jmsfundamentals.messageestructure;

import javax.jms.BytesMessage;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.activemq.artemis.jms.client.*;

public class MessageTypeDemo {

	public static void main(String[] args) throws NamingException{
		InitialContext initialContext = new InitialContext();
		Queue queue = (Queue) initialContext.lookup("queue/myQueue");
		
		try {
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
			JMSContext jmsContext = connectionFactory.createContext();
			
			JMSProducer producer = jmsContext.createProducer();
			TextMessage textMessage = jmsContext.createTextMessage("Hi c:");
			
			BytesMessage bytesMessage = jmsContext.createBytesMessage();
			bytesMessage.writeUTF("Salma");
			bytesMessage.writeLong(123);
			producer.send(queue, bytesMessage);			
			BytesMessage bytesMessageReceived = (BytesMessage) jmsContext.createConsumer(queue).receive(5000);
			System.out.println(bytesMessageReceived.readUTF());
			System.out.println(bytesMessageReceived.readLong());
			
			
			StreamMessage streamMessage = jmsContext.createStreamMessage();
			streamMessage.writeBoolean(true);
			streamMessage.writeFloat(2.5f);
			producer.send(queue, streamMessage);			
			StreamMessage streamMessageReceived = (StreamMessage) jmsContext.createConsumer(queue).receive(5000);
			System.out.println(streamMessageReceived.readBoolean());
			System.out.println(streamMessageReceived.readFloat());
			
			MapMessage mapMessage = jmsContext.createMapMessage();
			mapMessage.setBoolean("isCreditAvailable", true);
			producer.send(queue, mapMessage);			
			MapMessage mapMessageReceived = (MapMessage) jmsContext.createConsumer(queue).receive(5000);
			System.out.println(mapMessageReceived.getBoolean("isCreditAvailable"));
		} catch(Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
