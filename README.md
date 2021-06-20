# spring-kafka-example

## EC2 카프카 설치, 실행
```
// EC2 Connection
chmod 400 test-kafka.pem
ssh -i kafka-test.pem ec2-user@{aws ec2 public ip}

// install jdk 11
sudo yum install -y java-11-amazon-corretto

// install kafka
wget https://downloads.apache.org/kafka/2.7.0/kafka_2.12-2.7.0.tgz

// set kafka Heap Memory size
export KAFKA_HEAP_OPTS="-Xmx400m -Xms400m"

// set kafka server
vi config/server.properties
// 주석 해제
listeners=PLAINTEXT://:9092
advertised.listeners=PLAINTEXT://{aws ec2 public ip}:9092

// zookeeper 실행
bin/zookeeper-server-start.sh -daemon config/zookeeper.properties

// kafka server 실행
bin/kafka-server-start.sh -daemon config/server.properties

// log 보기
tail -f logs/*
```

## kafka 여러가지 테스트

```
// macOS kafka CLI 설치
curl https://mirror.navercorp.com/apache/kafka/2.6.2/kafka_2.12-2.6.2.tgz --output kafka.tgz 
tar -xvf kafka.tgz
cd kafka_2.13-2.5.0/bin

// create kafka topic
./kafka-topics.sh --create --bootstrap-server {aws ec2 public ip}:9092 --replication-factor 1 --partitions 3 --topic test

// produce message
./kafka-console-producer.sh --bootstrap-server {aws ec2 public ip}:9092 --topic test

// consume message
./kafka-console-consumer.sh --bootstrap-server {aws ec2 public ip}:9092 --topic test --from-beginning
./kafka-console-consumer.sh --bootstrap-server {aws ec2 public ip}:9092 --topic test -group testgroup --from-beginning

// consume message with group
./kafka-consumer-groups.sh --bootstrap-server {aws ec2 public ip}:9092 --list
./kafka-consumer-groups.sh --bootstrap-server {aws ec2 public ip}:9092 --group testgroup --describe
./kafka-consumer-groups.sh --bootstrap-server {aws ec2 public ip}:9092 --group testgroup --topic test --reset-offsets --to-earliest --execute
./kafka-consumer-groups.sh --bootstrap-server {aws ec2 public ip}:9092 --group testgroup --topic test:1 --reset-offsets --to-offset 10 --execute
```

## WebAPP API for test

- "/kafka/publish"
	produce message to EC2 kafka server with topic named "test"

- "/kafka/subscribe"
	consume message from EC2 kafka topic named "test" with three threads