package jmsfundamentals.grouping;

import java.util.Map;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class MessageGroupingDemo {

	public static void main(String[] args) throws Exception {
		InitialContext context = new InitialContext();
		Queue queue = (Queue) context.lookup("queue/myQueue");
		
		try (ActiveMQConnectionFactory connection = new ActiveMQConnectionFactory();
			 JMSContext jmsContext = connection.createContext();
			 JMSContext jmsContext2 = connection.createContext();){
			
				JMSProducer producer = jmsContext.createProducer();
				JMSConsumer consumer1 = jmsContext2.createConsumer(queue);
				JMSConsumer consumer2 = jmsContext2.createConsumer(queue);
				
				int count = 10;
				TextMessage[] messages = new TextMessage[count];
				
				for(int i = 0; i < count; i++) {
					messages[i] = jmsContext.createTextMessage("Group-0 message " + i);
					messages[i].setStringProperty("JMSXGroupID", "Group-0");
					
					producer.send(queue, messages[i]);
				}
		} finally {}
	}
	
	
	class MyListener implements MessageListener{
		private String name;
		private Map<String, String> receivedMessages;
		
		public MyListener(String name, Map<String, String> receivedMessages) {
			this.name = name;
			this.receivedMessages = receivedMessages;
		}

		@Override
		public void onMessage(Message message) {
			TextMessage textMessage = (TextMessage) message;
			try {
				System.out.println("Message received is: " + textMessage.getText());
				System.out.println("Listener name: " + name);
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}
}
