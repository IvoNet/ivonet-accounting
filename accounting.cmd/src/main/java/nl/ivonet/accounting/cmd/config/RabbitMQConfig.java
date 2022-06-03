package nl.ivonet.accounting.cmd.config;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
@ConditionalOnProperty(name = "event_bus", havingValue = "rabbitmq")
public class RabbitMQConfig {


    @Bean
    public Queue accountClosedEventQueue() {
        return new Queue("AccountClosedEvent");
    }

    @Bean
    public Queue accountOpenedEventQueue() {
        return new Queue("AccountOpenedEvent");
    }

    @Bean
    public Queue fundsDepositedEventQueue() {
        return new Queue("FundsDepositedEvent");
    }

    @Bean
    public Queue fundsWithdrawnEventQueue() {
        return new Queue("FundsWithdrawnEvent");
    }

}
