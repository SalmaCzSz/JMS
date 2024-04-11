package pubsub.jms.hr;

import javax.jms.JMSContext;
import javax.jms.Topic;
import javax.naming.InitialContext;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import pubsub.jms.hr.models.Employee;

public class HRApp {

	public static void main(String[] args) throws Exception {
		InitialContext initialContext = new InitialContext();
		Topic topic = (Topic) initialContext.lookup("topic/empTopic");
		
		try {
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
			JMSContext jmsContext = connectionFactory.createContext();
			
			Employee employee = new Employee();
			employee.setId(123);
			employee.setFirstName("Bharath");
			employee.setLastName("Thippireddy");
			employee.setDesignation("Software Architect");
			employee.setEmail("bharath@bharath.com");
			employee.setPhone("123456");
			
			jmsContext.createProducer().send(topic, employee);
			System.out.println("Message Sent");
		} finally {};
	}
}
