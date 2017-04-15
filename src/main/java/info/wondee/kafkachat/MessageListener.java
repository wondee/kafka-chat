package info.wondee.kafkachat;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {

	// flexible like requestHandler (see javadoc)
	@KafkaListener(id="main-listener", topics="test-topic")
	public void listen(String message) {
		System.out.println("message received: " + message);
	}
	
}
