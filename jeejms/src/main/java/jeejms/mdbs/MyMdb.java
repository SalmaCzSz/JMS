package jeejms.mdbs;

import java.util.logging.Logger;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class MyMdb implements MessageListener {
	private static Logger LOGGER = Logger.getLogger(MyMdb.class.toString());
	
	@Override
	public void onMessage(Message message) {
		if(message instanceof TextMessage) {
			try {
				String text = ((TextMessage) message).getText();
				LOGGER.info("Received Message is: " + text);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}
