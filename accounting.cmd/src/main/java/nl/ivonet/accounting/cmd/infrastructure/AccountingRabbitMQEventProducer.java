package nl.ivonet.accounting.cmd.infrastructure;

import lombok.AllArgsConstructor;
import nl.ivonet.cqrs.core.events.BaseEvent;
import nl.ivonet.cqrs.core.producers.EventProducer;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@ConditionalOnProperty(name = "event_bus", havingValue = "rabbitmq")
public class AccountingRabbitMQEventProducer implements EventProducer {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publish(String topic, BaseEvent event) {
        this.rabbitTemplate.convertAndSend(topic, event);
    }
}
