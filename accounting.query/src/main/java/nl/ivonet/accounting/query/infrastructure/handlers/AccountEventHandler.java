package nl.ivonet.accounting.query.infrastructure.handlers;

import lombok.AllArgsConstructor;
import nl.ivonet.accounting.common.events.AccountClosedEvent;
import nl.ivonet.accounting.common.events.AccountOpenedEvent;
import nl.ivonet.accounting.common.events.FundsDepositedEvent;
import nl.ivonet.accounting.common.events.FundsWithdrawnEvent;
import nl.ivonet.accounting.query.domain.AccountRepository;
import nl.ivonet.accounting.query.domain.BankAccount;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountEventHandler implements EventHandler {

    private final AccountRepository accountRepository;

    @Override
    public void on(AccountOpenedEvent event) {
        accountRepository.save(BankAccount.builder()
                .id(event.getId())
                .accountNumber(event.getAccountNumber())
                .accountHolder(event.getAccountHolder())
                .accountType(event.getAccountType())
                .creationDate(event.getCreationDate())
                .balance(event.getInitialBalance())
                .build());
    }

    @Override
    public void on(FundsWithdrawnEvent event) {
        var account = accountRepository.findById(event.getId()).orElseThrow(() -> new IllegalStateException("Account not found."));
        account.setBalance(account.getBalance() - event.getAmount());
        accountRepository.save(account);

    }

    @Override
    public void on(FundsDepositedEvent event) {
        var account = accountRepository.findById(event.getId()).orElseThrow(() -> new IllegalStateException("Account not found."));
        account.setBalance(account.getBalance() + event.getAmount());
        accountRepository.save(account);

    }

    @Override
    public void on(AccountClosedEvent event) {
        accountRepository.deleteById(event.getId());
    }
}
