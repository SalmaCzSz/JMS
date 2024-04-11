package pubsub.jms.payroll;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Message;
import javax.jms.Topic;
import javax.naming.InitialContext;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import pubsub.jms.hr.models.Employee;


public class PayrollApp {

	public static void main(String[] args) throws Exception {
		InitialContext initialContext = new InitialContext();
		Topic topic = (Topic) initialContext.lookup("topic/empTopic");
		
		try {
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
			JMSContext jmsContext = connectionFactory.createContext();
			JMSConsumer consumer = jmsContext.createConsumer(topic);
			Message message = consumer.receive();
			Employee employee = message.getBody(Employee.class);
			
			System.out.println(employee.getFirstName());
		} finally {};
	}
}
