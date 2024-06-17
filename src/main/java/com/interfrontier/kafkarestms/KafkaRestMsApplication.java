package com.interfrontier.kafkarestms;

import com.interfrontier.kafkarestms.avro.Purchase;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Arrays;
import java.util.Map;

import static org.apache.kafka.clients.CommonClientConfigs.RETRIES_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.*;
import static org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG;

@SpringBootApplication
//@EnableKafkaStreams - TopologyException: Invalid topology: Topology has no stream threads and no global threads, must subscribe to at least one source topic or global table.
public class KafkaRestMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaRestMsApplication.class, args);
	}

	@Bean
	public KafkaAdmin admin() {
		return new KafkaAdmin(Map.of(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092"));
	}

	@Bean
	public NewTopic hobbitAvro() {
		return TopicBuilder.name("hobbit-avro").partitions(15).replicas(1).build();
	}

	@Bean
	public NewTopic topic1() {
		return TopicBuilder.name("thing1")
						.partitions(10)
						.replicas(1)
						.compact()
						.build();
	}

	@Bean
	public NewTopic topic2() {
		return TopicBuilder.name("thing2")
						.partitions(10)
						.replicas(1)
						.config(TopicConfig.COMPRESSION_TYPE_CONFIG, "zstd")
						.build();
	}

	@Bean
	public NewTopic topic3() {
		return TopicBuilder.name("thing3")
						.assignReplicas(0, Arrays.asList(0, 1))
						.assignReplicas(1, Arrays.asList(1, 2))
						.assignReplicas(2, Arrays.asList(2, 0))
						.config(TopicConfig.COMPRESSION_TYPE_CONFIG, "zstd")
						.build();
	}

	@Bean
	NewTopic counts() {
		return TopicBuilder.name("streams-wordcount-output").partitions(6).replicas(1).build();
	}



}
