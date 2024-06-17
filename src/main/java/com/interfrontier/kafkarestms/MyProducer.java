package com.interfrontier.kafkarestms;

import com.github.javafaker.Faker;
import com.interfrontier.kafkarestms.avro.Hobbit;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Stream;

/**
 * @author <a href="mailto:john.deringer@gmail.com">John Deringer</a>
 * Date: 1/19/24
 */
@RequiredArgsConstructor
@Component
public class MyProducer {

  @Autowired
  private final KafkaTemplate<Integer, com.interfrontier.kafkarestms.avro.Hobbit> template = null;
//  private final KafkaTemplate<Integer, String> template = null;



//  @EventListener(ApplicationStartedEvent.class)
//  public void send(com.interfrontier.kafkarestms.avro.Hobbit hobbit) {
//
//
//      template.send("hobbit-avro", hobbit.hashCode(), hobbit);
//    template.send("hobbit", String.valueOf(hobbit.getQuote()), hobbit);


//  }

  Faker faker;
  @EventListener(ApplicationStartedEvent.class)
  public void generate() {

    System.out.println("generate()==================");
    faker = Faker.instance();
    final Flux<Long> interval = Flux.interval(Duration.ofMillis(1_000));

    final Flux<String> quotes = Flux.fromStream(Stream.generate(() -> faker.hobbit().quote()));
//    final Flux<String> quotes = Flux.fromStream(Stream.generate(() -> faker.artist().name()));

//    Integer key = faker.random().nextInt(42);


//    Flux.zip(interval, quotes)
//            .map(it -> template.send("hobbit-avro", faker.random().nextInt(42), new Hobbit(it.getT2()))).blockLast();
    Flux.zip(interval, quotes)
            .map(it -> template.send("hobbit-avro", faker.random().nextInt(42), new Hobbit(it.getT2(), faker.random().nextInt(42)))).blockLast();


//    Flux.zip(interval, quotes)
//            .map(it -> template.send("hobbit", faker.random().nextInt(42), it.getT2())).blockLast();
  }

}
