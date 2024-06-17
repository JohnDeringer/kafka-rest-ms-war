package com.interfrontier.kafkarestms;

import com.interfrontier.kafkarestms.avro.Hobbit;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:john.deringer@gmail.com">John Deringer</a>
 * Date: 1/19/24
 */
@Component
public class MyConsumer {


  @KafkaListener(topics = {"hobbit-avro"}, groupId = "spring-boot-kafka")
  public void consume(ConsumerRecord<Integer, Hobbit> record) {
    System.out.println("received [" + record.value() + "] " +
            "key [" + record.key() + "] " +
            "Id [" + record.value().getId() + "] " +
            "Quote [" + record.value().getQuote() + "]"
    );

  }

//  @KafkaListener(topics={"hobbit"}, groupId="spring-boot-kafka")
//  public void consume(ConsumerRecord<String, Purchase> record) {
//
//    System.out.println("received key [" + record.key() +
//            "] value [" + record.value() +
//            "] topic [" + record.topic() +
//            "] partition [" + record.partition() +
//            "] offset [" + record.offset() +
//            "] headers [" + record.headers() +
//            "]");
//  }
//  public void consume(String quote) {
//    System.out.println("received= " + quote);
//  }

}
