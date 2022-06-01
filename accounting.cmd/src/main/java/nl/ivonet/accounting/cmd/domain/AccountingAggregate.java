package nl.ivonet.accounting.cmd.domain;

import lombok.NoArgsConstructor;
import nl.ivonet.accounting.cmd.api.commands.OpenAccountCommand;
import nl.ivonet.accounting.common.events.AccountClosedEvent;
import nl.ivonet.accounting.common.events.AccountOpenedEvent;
import nl.ivonet.accounting.common.events.FundsDepositedEvent;
import nl.ivonet.accounting.common.events.FundsWithdrawnEvent;
import nl.ivonet.cqrs.core.domain.AggregateRoot;

import java.util.Date;

@NoArgsConstructor
public class AccountingAggregate extends AggregateRoot {
    private Boolean active;
    private double balance;

    public AccountingAggregate(OpenAccountCommand command) {
        this.raiseEvent(AccountOpenedEvent.builder()
                .id(command.getId())
                .accountHolder(command.getAccountHolder())
                .accountNumber(command.getAccountNumber())
                .accountType(command.getAccountType())
                .creationDate(new Date())
                .initialBalance(command.getInitialBalance())
                .build());
    }


    public void apply(AccountOpenedEvent event) {
        this.id = event.getId();
        this.active = true;
        this.balance = event.getInitialBalance();
    }

    public void depositFunds(double amount) {
        if (!this.active) {
            throw new IllegalStateException("Funds can not be deposited into a closed account.");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("The Deposit amount must be greater than zero.");
        }
        this.raiseEvent(FundsDepositedEvent.builder()
                .id(this.id)
                .amount(amount)
                .build());
    }

    public void apply(FundsDepositedEvent event) {
        this.id = event.getId();
        this.balance += event.getAmount();
    }

    public void withdrawFunds(double amount) {
        if (!this.active) {
            throw new IllegalStateException("Funds can not be withdrawn from a closed account.");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("The Withdraw amount must be greater than zero.");
        }
        if (this.balance < amount) {
            throw new IllegalArgumentException("Withdrawal declined. Insufficient funds.");
        }
        this.raiseEvent(FundsWithdrawnEvent.builder()
                .id(this.id)
                .amount(amount)
                .build());
    }

    public void apply(FundsWithdrawnEvent event) {
        this.id = event.getId();
        this.balance -= event.getAmount();
    }

    public void closeAccount() {
        if (!this.active) {
            throw new IllegalStateException("Account is already closed.");
        }
        this.raiseEvent(AccountClosedEvent.builder()
                .id(this.id)
                .build());
    }

    public void apply(AccountClosedEvent event) {
        this.id = event.getId();
        this.active = false;
    }
}

