package pubsub.jms.welness;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Message;
import javax.jms.Topic;
import javax.naming.InitialContext;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import pubsub.jms.hr.models.Employee;


public class WelnessApp {

	public static void main(String[] args) throws Exception {
		InitialContext initialContext = new InitialContext();
		Topic topic = (Topic) initialContext.lookup("topic/empTopic");
		
		try {
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
			JMSContext jmsContext = connectionFactory.createContext();
			JMSConsumer consumer1 = jmsContext.createSharedConsumer(topic, "sharedConsumer");
			JMSConsumer consumer2 = jmsContext.createSharedConsumer(topic, "sharedConsumer");
			
			for(int i = 1; i <= 10; i+=2) {
				Message message1 = consumer1.receive();
				Employee employee1 = message1.getBody(Employee.class);
				System.out.println("Consumer 1: " + employee1.getFirstName());
				
				Message message2 = consumer2.receive();
				Employee employee2 = message2.getBody(Employee.class);
				System.out.println("Consumer 2: " +employee2.getFirstName());
			}
		} finally {};
	}
}
