package nl.ivonet.accounting.cmd.infrastructure;

import lombok.AllArgsConstructor;
import nl.ivonet.cqrs.core.events.BaseEvent;
import nl.ivonet.cqrs.core.producers.EventProducer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@ConditionalOnProperty(name = "event_bus", havingValue = "kafka")
public class AccountingKafkaEventProducer implements EventProducer {

    private final KafkaTemplate<String, BaseEvent> kafkaTemplate;


    @Override
    public void publish(String topic, BaseEvent event) {
        this.kafkaTemplate.send(topic, event);

    }
}
