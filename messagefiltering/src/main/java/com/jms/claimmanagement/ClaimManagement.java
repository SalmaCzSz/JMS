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
			jmsContext.createConsumer(claimQueue, "");
			ObjectMessage objectMessage = jmsContext.createObjectMessage();
			
			Claim claim = new Claim();
			claim.setHospitalID(1);
			claim.setClaimAmount(1000);
			claim.setDoctorName("John");
			claim.setDoctorType("gyna");
			claim.setInsuranceProvider("blue cross");
			
			objectMessage.setObject(claim);
			producer.send(claimQueue, objectMessage);
			
		} finally {}
	}
}
