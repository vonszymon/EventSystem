#!/bin/bash
kafkaDir="/opt/kafka_2.11-0.10.0.0"

$kafkaDir/bin/zookeeper-server-start.sh /opt/kafka_2.11-0.10.0.0/config/zookeeper.properties &
sleep 1s

$kafkaDir/bin/kafka-server-start.sh /opt/kafka_2.11-0.10.0.0/config/server.properties &
sleep 1s

$kafkaDir/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test &
sleep 1s

/app/pivotal-tc-server-developer-3.1.3.SR1/tcruntime-ctl.sh my_server start
sleep 1s

tail -f /app/pivotal-tc-server-developer-3.1.3.SR1/my_server/logs/catalina.out
