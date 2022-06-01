package nl.ivonet.accounting.query.domain;

import lombok.*;
import nl.ivonet.accounting.common.dto.AccountType;
import nl.ivonet.cqrs.core.domain.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BankAccount extends BaseEntity {

    @Id
    private String id;
    private String accountNumber;
    private String accountHolder;
    private AccountType accountType;
    private Date creationDate;
    private double balance;

}
