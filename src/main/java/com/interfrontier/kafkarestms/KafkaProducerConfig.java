package com.interfrontier.kafkarestms;

import com.interfrontier.kafkarestms.avro.Hobbit;
import com.interfrontier.kafkarestms.avro.Purchase;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;

import static org.apache.kafka.clients.CommonClientConfigs.RETRIES_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.*;

import io.confluent.kafka.serializers.KafkaAvroSerializer;

/**
 * @author <a href="mailto:john.deringer@gmail.com">John Deringer</a>
 * Date: 1/19/24
 */
@Configuration
//@PropertySource("classpath:kafkaProducer.properties")
public class KafkaProducerConfig {

  @Value("${bootstrap.servers:http://localhost:9092}")
  private String bootstrapServers;

  @Value("${schema.registry.url}")
  private String schemaRegistryUrl;

  @Value("${retries:0}")
  private String retries;

  @Value("${buffer.memory:33554432}")
  private String bufferMemory;

  @Value("${spring.kafka.producer.key-serializer:org.apache.kafka.common.serialization.StringSerializer}")
  private String keySerializer;

  @Value("${spring.kafka.producer.value-serializer:org.apache.kafka.common.serialization.StringSerializer}")
  private String valueSerializer;


  @Bean
  public ProducerFactory<Integer, Hobbit> producerFactory() {

    return new DefaultKafkaProducerFactory<>(
            Map.of(BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
                    KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl,
                    RETRIES_CONFIG, retries,
                    BUFFER_MEMORY_CONFIG, bufferMemory,
                    KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class,
                    VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class
            ));

//    return new DefaultKafkaProducerFactory<>(
//            Map.of(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092",
//                    RETRIES_CONFIG, 0,
//                    BUFFER_MEMORY_CONFIG, 33554432,
//                    KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
//                    VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class
//            ));
  }

  @Bean
  public KafkaTemplate<Integer, Hobbit> kafkaTemplate() {
    return new KafkaTemplate<>(producerFactory());
  }


}
