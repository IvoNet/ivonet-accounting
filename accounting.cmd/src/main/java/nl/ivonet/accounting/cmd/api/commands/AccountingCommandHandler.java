package nl.ivonet.accounting.cmd.api.commands;

import lombok.AllArgsConstructor;
import nl.ivonet.accounting.cmd.domain.AccountingAggregate;
import nl.ivonet.cqrs.core.handlers.EventSourcingHandler;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountingCommandHandler implements CommandHandler {

    private final EventSourcingHandler<AccountingAggregate> eventSourcingHandler;

    @Override
    public void handle(OpenAccountCommand command) {
        var aggregate = new AccountingAggregate(command);
        eventSourcingHandler.save(aggregate);

    }

    @Override
    public void handle(DepositFundsCommand command) {
        var aggregate = eventSourcingHandler.getById(command.getId());
        aggregate.depositFunds(command.getAmount());
        eventSourcingHandler.save(aggregate);
    }

    @Override
    public void handle(WithdrawFundsCommand command) {
        var aggregate = eventSourcingHandler.getById(command.getId());
        aggregate.withdrawFunds(command.getAmount());
        eventSourcingHandler.save(aggregate);
    }

    @Override
    public void handle(CloseAccountCommand command) {
        var aggregate = eventSourcingHandler.getById(command.getId());
        aggregate.closeAccount();
        eventSourcingHandler.save(aggregate);
    }
}
