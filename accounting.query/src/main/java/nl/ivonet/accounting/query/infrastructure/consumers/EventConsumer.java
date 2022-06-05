package nl.ivonet.accounting.query.infrastructure.consumers;

import com.rabbitmq.client.Channel;
import nl.ivonet.accounting.common.events.AccountClosedEvent;
import nl.ivonet.accounting.common.events.AccountOpenedEvent;
import nl.ivonet.accounting.common.events.FundsDepositedEvent;
import nl.ivonet.accounting.common.events.FundsWithdrawnEvent;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import java.io.IOException;


public interface EventConsumer {
    void consume(@Payload AccountOpenedEvent event, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException;

    void consume(@Payload FundsDepositedEvent event, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException;

    void consume(@Payload FundsWithdrawnEvent event, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException;

    void consume(@Payload AccountClosedEvent event, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException;
}
