package nl.ivonet.accounting.common.events;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import nl.ivonet.cqrs.core.events.BaseEvent;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class AccountClosedEvent extends BaseEvent {
}
