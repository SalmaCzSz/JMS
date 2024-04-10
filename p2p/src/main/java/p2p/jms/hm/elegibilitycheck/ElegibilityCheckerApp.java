package p2p.jms.hm.elegibilitycheck;

import javax.jms.JMSContext;
import javax.jms.JMSConsumer;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import p2p.jms.hm.elegibilitycheck.listeners.ElegibilityCheckListener;


public class ElegibilityCheckerApp {
	public static void main(String[] args) throws NamingException, JMSException, InterruptedException {
		InitialContext initialContext = new InitialContext();
		Queue requestQueue = (Queue) initialContext.lookup("queue/requestQueue");
		
		try {
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
			JMSContext jmsContext = connectionFactory.createContext();
			JMSConsumer consumer = jmsContext.createConsumer(requestQueue);
			consumer.setMessageListener(new ElegibilityCheckListener());
			
			Thread.sleep(10000);
			
		} finally{};
	}
}