package nl.ivonet.accounting.common.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import nl.ivonet.accounting.common.dto.AccountType;
import nl.ivonet.cqrs.core.events.BaseEvent;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class AccountOpenedEvent extends BaseEvent {
    private String accountId;
    private String accountHolder;
    private String accountNumber;
    private AccountType accountType;
    private Date creationDate;
    private double initialBalance;

}
