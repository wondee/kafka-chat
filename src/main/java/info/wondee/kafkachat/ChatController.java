package info.wondee.kafkachat;

import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class ChatController {

	private static final String KAFKA_CHAT_TOPIC = "kafka-chat";

	private static final String REDIS_CHATROOM_PREFIX = "Chatroom-";

	private static final String REDIS_CHATROOM_PATTERN = REDIS_CHATROOM_PREFIX + "*";

	@Autowired
	private KafkaTemplate<Integer, ChatMessage> kafkaTemplate;
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@RequestMapping(value="/room/{chatroom}/message", method=RequestMethod.POST, consumes="application/json", produces="application/json")
	public HttpEntity<ChatMessage> sendMessage(@PathVariable String chatroom, @RequestBody ChatMessage body) {

		try {
			body.setTime(new Date());
			body.setRoom(chatroom);
			
			kafkaTemplate.send(KAFKA_CHAT_TOPIC, body).get();
			
			String redisKey = REDIS_CHATROOM_PREFIX + chatroom;
			
			redisTemplate.opsForSet().add(redisKey, body.getUser());
			redisTemplate.expire(redisKey, 10, TimeUnit.MINUTES);
			
			
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
		
		return new HttpEntity<>(body);
		
	}
	
	@RequestMapping(value="/room", method=RequestMethod.GET, produces="application/json")
	public Map<String, Set<String>> getChatrooms() {
		SetOperations<String, String> ops = redisTemplate.opsForSet();
		
		return 
			redisTemplate.keys(REDIS_CHATROOM_PATTERN)
				.stream()
				.collect(
					Collectors.toMap(
						name -> name.substring(REDIS_CHATROOM_PREFIX.length()), 
						name -> ops.members(name)
					)
				);
	}
	
	
	@RequestMapping(value="/room/{chatroom}", method=RequestMethod.GET, produces="application/json")
	public List<ChatMessage> getChatroom(@PathVariable String chatroom) {
		
		Consumer<Integer, ChatMessage> consumer = new KafkaConsumer<>(consumerConfigs(), null, new JsonDeserializer<>(ChatMessage.class));
		
		consumer.subscribe(Arrays.asList(KAFKA_CHAT_TOPIC));
		
		ConsumerRecords<Integer, ChatMessage> records = consumer.poll(10000);
		Iterator<ConsumerRecord<Integer, ChatMessage>> iter = records.iterator();
		
		System.out.println("received "+ records.count() + " messages ");
		
		List<ChatMessage> result = Lists.newArrayListWithExpectedSize(records.count());
		
		while (iter.hasNext()) {
			ConsumerRecord<Integer,ChatMessage> next = iter.next();
			ChatMessage msg = next.value();
			
			if (chatroom.equals(msg.getRoom())) {
				result.add(msg);
			}
		}
		
		consumer.close();
		
		return result;
	}
//
//	public static void main(String[] args) {
//		List<ChatMessage> chatroom = new ChatController().getChatroom("kafka-chat");
//		
//		chatroom.forEach(room -> System.out.println(room));
//	}
//	
	private Map<String, Object> consumerConfigs() {

		return ImmutableMap.<String, Object>builder()
					.put("bootstrap.servers", "localhost:9092")
					.put("key.deserializer", IntegerDeserializer.class)
					.put("value.deserializer", JsonDeserializer.class)
					.put("auto.offset.reset", "earliest")
					.put("max.partition.fetch.bytes", 2097152) // important, but why???
					.put("group.id", UUID.randomUUID().toString()) 

				.build();
	}
	
	
	
}
