package nl.ivonet.accounting.common.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import nl.ivonet.cqrs.core.events.BaseEvent;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class FundsWithdrawnEvent extends BaseEvent {
    private double amount;
}
