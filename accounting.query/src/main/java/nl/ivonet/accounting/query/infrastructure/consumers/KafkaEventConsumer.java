package nl.ivonet.accounting.query.infrastructure.consumers;

import nl.ivonet.accounting.common.events.AccountClosedEvent;
import nl.ivonet.accounting.common.events.AccountOpenedEvent;
import nl.ivonet.accounting.common.events.FundsDepositedEvent;
import nl.ivonet.accounting.common.events.FundsWithdrawnEvent;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

public interface KafkaEventConsumer {
    void consume(@Payload AccountOpenedEvent event, Acknowledgment acknowledgment);

    void consume(@Payload FundsDepositedEvent event, Acknowledgment acknowledgment);

    void consume(@Payload FundsWithdrawnEvent event, Acknowledgment acknowledgment);

    void consume(@Payload AccountClosedEvent event, Acknowledgment acknowledgment);
}
