package nl.ivonet.accounting.cmd.infrastructure;

import lombok.AllArgsConstructor;
import nl.ivonet.accounting.cmd.domain.AccountingAggregate;
import nl.ivonet.cqrs.core.domain.AggregateRoot;
import nl.ivonet.cqrs.core.events.BaseEvent;
import nl.ivonet.cqrs.core.handlers.EventSourcingHandler;
import nl.ivonet.cqrs.core.infrastructure.EventStore;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
@AllArgsConstructor
public class AccountingEventSourcingHandler implements EventSourcingHandler<AccountingAggregate> {

    private final EventStore eventStore;

    @Override
    public void save(AggregateRoot aggregate) {
        this.eventStore.saveEvents(aggregate.getId(), aggregate.getUncommittedChanges(), aggregate.getVersion());
        aggregate.markChangesAsCommitted();

    }

    @Override
    public AccountingAggregate getById(String id) {
        var aggregate = new AccountingAggregate();
        var events = this.eventStore.getEvents(id);
        if (events == null || events.isEmpty()) {
            return aggregate;
        }
        aggregate.replayEvents(events);
        var latestVersion = events.stream()
                .map(BaseEvent::getVersion)
                .max(Comparator.naturalOrder());
        aggregate.setVersion(latestVersion.orElseThrow(IllegalStateException::new));

        return aggregate;
    }
}
