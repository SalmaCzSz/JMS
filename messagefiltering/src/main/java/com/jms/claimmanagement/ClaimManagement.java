package com.jms.claimmanagement;

import javax.jms.*;
import javax.naming.*;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class ClaimManagement {

	public static void main(String[] args) throws NamingException, JMSException {
		InitialContext initialContext = new InitialContext();
		Queue claimQueue = (Queue) initialContext.lookup("queue/claimQueue");
		
		try {
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
			JMSContext jmsContext = connectionFactory.createContext();
			
			JMSProducer producer = jmsContext.createProducer();
			JMSConsumer consumer = jmsContext.createConsumer(claimQueue, "doctorType IN('neuro', 'psych') OR JMSPriority BETWEEN 3 AND 6");
			ObjectMessage objectMessage = jmsContext.createObjectMessage();
			//objectMessage.setIntProperty("hospitalId", 1);
			//objectMessage.setIntProperty("claimAmount", 1000);
			//objectMessage.setStringProperty("doctorName", "John");
			objectMessage.setStringProperty("doctorType", "gyna");
			
			Claim claim = new Claim();
			claim.setHospitalID(1);
			claim.setClaimAmount(1000);
			claim.setDoctorName("John");
			claim.setDoctorType("gyna");
			claim.setInsuranceProvider("blue cross");
			
			objectMessage.setObject(claim);
			producer.send(claimQueue, objectMessage);
			
			Claim receiveBody = consumer.receiveBody(Claim.class);
			System.out.println(receiveBody.getClaimAmount());
		} finally {}
	}
}
