package info.wondee.kafkachat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;


@SpringBootApplication
@Import({
		KafkaConfig.class, 
		UiConfig.class,
		StompConfig.class
	})
public class KafkaChatApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaChatApplication.class, args);
	}
	
}
