
# Kafka-Chat 

## Introduction

This is a sample application to test some technologies, different tools and frameworks. 

The rough architecture is:

```

Angular ----- Spring Boot ----- Kafka / Zookeeper
                  |
                  |
                Redis

```

- Angular Frontend renders the view (messages are sent via websockets)
- Kafka stores and distributes the chat messages
- Redis stores the state of the chat rooms
- Spring boot glues it all together

## Some Useful commands

### Project specific
Starting the frontend
```
ng serve
```

Start Redis and Kafka via the script (the kafka installation folder needs to be adjusted)
```
scripts/start_kafka.bat
```

#### System API 

List all chatrooms
```
curl  http://localhost:8080/api/room
```

Get all messages from a chatroom
```
curl  http://localhost:8080/api/room/[chatroom name]
```

Post a message
```
curl -H "Content-Type: application/json" -X POST -d '{"text":"[message text]", "user":"[user name]"}' http://localhost:8080/room/[chatroom name]/message
```

### Kafka specific

create a new kafka topic 
```
kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic [topic name]
```

listen to incoming messages in the conolse
```
kafka-console-consumer.bat --bootstrap-server localhost:9092 --from-beginning --topic [topic name]
```

open a new producer from the console
```
kafka-console-producer.bat --broker-list localhost:9092 --topic [topic name]
```

get a description of a topic
```
kafka-topics.bat --describe --zookeeper localhost:2181 --topic [topic name]
```
