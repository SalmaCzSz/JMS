package p2p.jms.hm.elegibilitycheck.listeners;

import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.naming.InitialContext;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import p2p.jms.hm.models.Patient;

public class ElegibilityCheckListener implements MessageListener {

	@Override
	public void onMessage(Message message) {
		ObjectMessage objectMessage = (ObjectMessage) message;
		
		try (ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
			JMSContext jmsContext = connectionFactory.createContext();) {
			
			InitialContext initialContext = new InitialContext();
			Queue replyQueue = (Queue) initialContext.lookup("queue/replyQueue");
			MapMessage replyMessage = jmsContext.createMapMessage();
			
			Patient patient = (Patient) objectMessage.getObject();
			String insuranceProvider = patient.getInsuranceProvider();
			System.out.println("Insurance provider: " + insuranceProvider);
			
			if(insuranceProvider.equals("Blue Cross Blue Shield") || insuranceProvider.equals("United Health")) {
				System.out.println("Patients Copay is: " + patient.getCopay());
				System.out.println("Amount to be pay: " + patient.getAmountToBePayed());
				if(patient.getCopay() < 40 && patient.getAmountToBePayed() <1000) {
					replyMessage.setBoolean("elegible", true);
				}
			} else {
				replyMessage.setBoolean("elegible", false);
			}
			
			JMSProducer producer = jmsContext.createProducer();
			producer.send(replyQueue, replyMessage);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
