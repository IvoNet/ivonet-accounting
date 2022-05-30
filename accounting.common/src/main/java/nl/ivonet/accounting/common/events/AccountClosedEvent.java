package nl.ivonet.accounting.common.events;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import nl.ivonet.cqrs.core.events.BaseEvent;

@Data
@SuperBuilder
public class AccountClosedEvent extends BaseEvent {
}
