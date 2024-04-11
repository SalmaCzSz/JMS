package pubsub.jms.security;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Message;
import javax.jms.Topic;
import javax.naming.InitialContext;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import pubsub.jms.hr.models.Employee;


public class SecurityApp {

	public static void main(String[] args) throws Exception {
		InitialContext initialContext = new InitialContext();
		Topic topic = (Topic) initialContext.lookup("topic/empTopic");
		
		try {
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
			JMSContext jmsContext = connectionFactory.createContext();
			
			jmsContext.setClientID("securityApp");
			JMSConsumer consumer = jmsContext.createDurableConsumer(topic, "subscription1");
			consumer.close();
			consumer = jmsContext.createDurableConsumer(topic, "subscription1");
			
			Thread.sleep(10000);
			
			Message message = consumer.receive();
			Employee employee = message.getBody(Employee.class);
			System.out.println(employee.getFirstName());
			
			consumer.close();
			jmsContext.unsubscribe("subscription1");
		} finally {};
	}
}
