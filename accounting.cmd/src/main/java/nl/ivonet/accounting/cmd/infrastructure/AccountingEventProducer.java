package nl.ivonet.accounting.cmd.infrastructure;

import lombok.AllArgsConstructor;
import nl.ivonet.cqrs.core.events.BaseEvent;
import nl.ivonet.cqrs.core.producers.EventProducer;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountingEventProducer implements EventProducer {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publish(String topic, BaseEvent event) {
        this.rabbitTemplate.convertAndSend(topic, event);
    }
}
