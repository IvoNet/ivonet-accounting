package nl.ivonet.accounting.cmd.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
@ConditionalOnProperty(name = "event_bus", havingValue = "rabbitmq")
public class RabbitMQConfig {


    @Bean
    Queue accountClosedEventQueue() {
        return QueueBuilder.durable("AccountClosedEvent").build();
    }

    @Bean
    Queue accountOpenedEventQueue() {
        return QueueBuilder.durable("AccountOpenedEvent").build();
    }

    @Bean
    Queue fundsDepositedEventQueue() {
        return QueueBuilder.durable("FundsDepositedEvent").build();
    }

    @Bean
    Queue fundsWithdrawnEventQueue() {
        return QueueBuilder.durable("FundsWithdrawnEvent").build();
    }

}
