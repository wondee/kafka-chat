package info.wondee.kafkachat;

import java.util.Map;

import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.google.common.collect.ImmutableMap;

@EnableKafka
@Configuration
public class KafkaConfig {

	@Bean
	public ProducerFactory<Integer, ChatMessage> producerFactory() {
		return new DefaultKafkaProducerFactory<>(producerConfigs(), null, new JsonSerializer<ChatMessage>());
	}

	@Bean
	public KafkaTemplate<Integer, ChatMessage> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}

	@Bean
	public Map<String, Object> producerConfigs() {

		return ImmutableMap.<String, Object>builder()
					.put("bootstrap.servers", "localhost:9092")
					.put("key.serializer", IntegerSerializer.class)
					.put("value.serializer", JsonSerializer.class)
					
					.put("group.id", "spring-boot-test") // needed but don't know why...

				.build();
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<Integer, ChatMessage> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<Integer, ChatMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}

	@Bean
	public ConsumerFactory<Integer, ChatMessage> consumerFactory() {
		return new DefaultKafkaConsumerFactory<>(consumerConfigs(), null, new JsonDeserializer<>(ChatMessage.class));
	}

	@Bean
    public Map<String, Object> consumerConfigs() {
		return ImmutableMap.<String, Object>builder()
				.put("bootstrap.servers", "localhost:9092")
				.put("key.deserializer", IntegerDeserializer.class)
				.put("value.deserializer", JsonDeserializer.class)
				
				.put("group.id", "spring-boot-test") // needed but dont know why...

			.build();
    }

}
