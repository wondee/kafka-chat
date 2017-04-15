

ng serve

cd /d d:\dev\kafka\kafka_2.11-0.10.2.0\

call bin\windows\zookeeper-server-start.bat config\zookeeper.properties
call bin\windows\kafka-server-start.bat config\server.properties

rem kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test-topic
rem kafka-console-consumer.bat --bootstrap-server localhost:9092 --from-beginning --topic spring-boot
rem kafka-console-producer.bat --broker-list localhost:9092 --topic my-replicated-topic
rem kafka-topics.bat --describe --zookeeper localhost:2181 --topic spring.boot

list all chats:

curl  http://localhost:8080/

post a message:

curl -H "Content-Type: application/json" -X POST -d '{"text":"Test message...", "user":"Markus"}' http://localhost:8080/test-chat