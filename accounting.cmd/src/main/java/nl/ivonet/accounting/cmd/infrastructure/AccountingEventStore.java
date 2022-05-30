package nl.ivonet.accounting.cmd.infrastructure;

import lombok.AllArgsConstructor;
import nl.ivonet.accounting.cmd.domain.AccountingAggregate;
import nl.ivonet.accounting.cmd.domain.EventStoreRepository;
import nl.ivonet.cqrs.core.events.BaseEvent;
import nl.ivonet.cqrs.core.events.EventModel;
import nl.ivonet.cqrs.core.exceptions.AggregateNotFoundException;
import nl.ivonet.cqrs.core.exceptions.ConcurrencyException;
import nl.ivonet.cqrs.core.infrastructure.EventStore;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
@AllArgsConstructor
public class AccountingEventStore implements EventStore {

    private final EventStoreRepository eventStoreRepository;

    @Override
    public void saveEvents(String aggregateId, Iterable<BaseEvent> events, int expectedVersion) {
        var eventsList = eventStoreRepository.findByAggregateId(aggregateId);
        if (expectedVersion != -1 && lastEvent(eventsList).getVersion() != expectedVersion) {
            throw new ConcurrencyException();
        }
        var version = expectedVersion;
        for (var event : events) {
            version++;
            event.setVersion(version);

            final EventModel persistedEvent = eventStoreRepository.save(EventModel.builder()
                    .aggregateId(aggregateId)
                    .aggregateType(AccountingAggregate.class.getName())
                    .eventType(event.getClass().getName())
                    .timestamp(new Date())
                    .version(version)
                    .eventData(event)
                    .build());
            if (persistedEvent == null) {
                throw new IllegalStateException("Could not save event " + event.getClass().getName());
            }
            //TODO produce event to kafka
        }
    }

    @Override
    public List<BaseEvent> getEvents(String aggregateId) {
        var eventsList = eventStoreRepository.findByAggregateId(aggregateId);
        if (eventsList == null || eventsList.isEmpty()) {
            throw new AggregateNotFoundException("Incorrect account id provided!");
        }
        return eventsList.stream().map(EventModel::getEventData).toList();
    }

    private EventModel lastEvent(List<EventModel> eventsList) {
        return eventsList.get(lastIndex(eventsList));
    }

    private int lastIndex(List<EventModel> eventsList) {
        return eventsList.size() - 1;
    }
}
