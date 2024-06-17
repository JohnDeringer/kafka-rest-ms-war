package com.interfrontier.kafkarestms;

import com.interfrontier.kafkarestms.avro.Purchase;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Map;

import static org.apache.kafka.clients.consumer.ConsumerConfig.*;
import static org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG;


/**
 * @author <a href="mailto:john.deringer@gmail.com">John Deringer</a>
 * Date: 1/21/24
 */
@Configuration
@PropertySource("classpath:application.properties")
public class KafkaConsumerConfig {

//  @Value("${bootstrap.servers:localhost:9092}")
//  private String bootstrapServers;
//
//  @Value("${schema.registry.url:localhost:8081}")
//  private String schemaRegistryUrl;

  @Bean
  public ConsumerFactory consumerFactory(
          @Value("${bootstrap.servers}") final String bootstrapServers,
          @Value("${schema.registry.url}") final String schemaRegistryUrl) {

    return new DefaultKafkaConsumerFactory<>(
            Map.of(BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
                    GROUP_ID_CONFIG, "spring-boot-kafka",
                    KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class,
                    VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class,
                    KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl,
                    KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true
            ));
  }

//  @Bean
//  public ConsumerFactory<String, Purchase> consumerFactory() {
//
//    return new DefaultKafkaConsumerFactory<>(
//            Map.of(BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
//                    KEY_DESERIALIZER_CLASS_CONFIG, StringSerializer.class,
//                    VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class,
//                    KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl,
//                    KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true
//            ));

//    return new DefaultKafkaProducerFactory<>(
//            Map.of(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092",
//                    RETRIES_CONFIG, 0,
//                    BUFFER_MEMORY_CONFIG, 33554432,
//                    KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
//                    VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class
//            ));
//  }

//  @Bean
//  public KafkaTemplate<String, Purchase> kafkaTemplate() {
//    return new KafkaTemplate<>(consumerFactory());
//  }


}
