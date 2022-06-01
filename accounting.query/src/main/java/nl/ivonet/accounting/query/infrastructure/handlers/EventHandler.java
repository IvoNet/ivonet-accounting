package nl.ivonet.accounting.query.infrastructure.handlers;

import nl.ivonet.accounting.common.events.AccountClosedEvent;
import nl.ivonet.accounting.common.events.AccountOpenedEvent;
import nl.ivonet.accounting.common.events.FundsDepositedEvent;
import nl.ivonet.accounting.common.events.FundsWithdrawnEvent;

public interface EventHandler {
    void on(AccountOpenedEvent event);

    void on(FundsWithdrawnEvent event);

    void on(FundsDepositedEvent event);

    void on(AccountClosedEvent event);
}
