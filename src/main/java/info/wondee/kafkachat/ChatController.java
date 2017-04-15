package info.wondee.kafkachat;

import java.util.Date;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.apache.kafka.clients.consumer.Consumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

	private static final String TOPIC_PREFIX = "kafka-chat.";

	@Autowired
	private KafkaTemplate<Integer, ChatMessage> template;
	
	@Autowired
	private ConsumerFactory<Integer, ChatMessage> consumerFactory;
	
	@RequestMapping(value="/{chatroom}", method=RequestMethod.POST, consumes="application/json", produces="application/json")
	public HttpEntity<ChatMessage> sendMessage(@PathVariable String chatroom, @RequestBody ChatMessage body) {

		try {
			body.setTime(new Date());
			
			template.send(TOPIC_PREFIX + chatroom, body).get();
			
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
		
		return new HttpEntity<>(body);
		
	}
	
	@RequestMapping(value="/", method=RequestMethod.GET, produces="application/json")
	public HttpEntity<Set<String>> getAllChatRooms() {
		
		Consumer<Integer, ChatMessage> consumer = consumerFactory.createConsumer();
		
		Set<String> allTopics = consumer.listTopics().keySet().stream()
				.filter(topic -> topic.startsWith(TOPIC_PREFIX))
				.collect(Collectors.toSet());
		
		return new HttpEntity<Set<String>>(allTopics);
	}
	
	
}
