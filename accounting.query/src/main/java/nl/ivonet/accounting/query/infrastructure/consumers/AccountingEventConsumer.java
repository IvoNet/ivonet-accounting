package nl.ivonet.accounting.query.infrastructure.consumers;

import com.rabbitmq.client.Channel;
import lombok.AllArgsConstructor;
import nl.ivonet.accounting.common.events.AccountClosedEvent;
import nl.ivonet.accounting.common.events.AccountOpenedEvent;
import nl.ivonet.accounting.common.events.FundsDepositedEvent;
import nl.ivonet.accounting.common.events.FundsWithdrawnEvent;
import nl.ivonet.accounting.query.infrastructure.handlers.EventHandler;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
@EnableRabbit
@AllArgsConstructor
public class AccountingEventConsumer implements EventConsumer {

    private final EventHandler eventHandler;

    @RabbitListener(queues = "AccountOpenedEvent")
    @Override
    public void consume(AccountOpenedEvent event, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        this.eventHandler.on(event);
        channel.basicAck(tag, false);
    }

    @RabbitListener(queues = "FundsDepositedEvent")
    @Override
    public void consume(FundsDepositedEvent event, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        this.eventHandler.on(event);
        channel.basicAck(tag, false);
    }

    @RabbitListener(queues = "FundsWithdrawnEvent")
    @Override
    public void consume(FundsWithdrawnEvent event, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        this.eventHandler.on(event);
        channel.basicAck(tag, false);
    }

    @RabbitListener(queues = "AccountClosedEvent")
    @Override
    public void consume(AccountClosedEvent event, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        this.eventHandler.on(event);
        channel.basicAck(tag, false);
    }
}
