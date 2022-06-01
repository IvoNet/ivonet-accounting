package nl.ivonet.accounting.query.api.controllers;

import lombok.AllArgsConstructor;
import nl.ivonet.accounting.query.api.dto.AccountLookupResponse;
import nl.ivonet.accounting.query.api.dto.EqualityType;
import nl.ivonet.accounting.query.api.queries.FindAccountByAccountHolderQuery;
import nl.ivonet.accounting.query.api.queries.FindAccountByIdQuery;
import nl.ivonet.accounting.query.api.queries.FindAccountWithBalanceQuery;
import nl.ivonet.accounting.query.api.queries.FindAllAccountsQuery;
import nl.ivonet.accounting.query.domain.BankAccount;
import nl.ivonet.cqrs.core.infrastructure.QueryDispatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.logging.Logger.getLogger;

@Service
@AllArgsConstructor
@RequestMapping("/api/v1/bankAccountLookup")
public class AccountLookupController {
    private static final Logger log = getLogger(AccountLookupController.class.getName());
    private final QueryDispatcher queryDispatcher;

    @GetMapping
    public ResponseEntity<AccountLookupResponse> getAllAccounts() {
        log.info("Received request to get all accounts");
        try {
            List<BankAccount> result = this.queryDispatcher.dispatch(new FindAllAccountsQuery());
            if (result.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(AccountLookupResponse.builder()
                    .accounts(result)
                    .message("Found " + result.size() + " accounts")
                    .build());

        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed to get all accounts", e);
            return ResponseEntity.badRequest().build();
        }
    }


    @GetMapping("/byId/{id}")
    public ResponseEntity<AccountLookupResponse> getAccountById(@PathVariable("id") String id) {
        log.info("Received request to get account by id: " + id);
        try {
            List<BankAccount> result = this.queryDispatcher.dispatch(new FindAccountByIdQuery(id));
            if (result.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(AccountLookupResponse.builder()
                    .accounts(result)
                    .message("Account found")
                    .build());

        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed to get account by id: " + id, e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/byAccountHolder/{accountHolder}")
    public ResponseEntity<AccountLookupResponse> getAccountByAccountHolder(@PathVariable("accountHolder") String accountHolder) {
        log.info("Received request to get account by accountHolder: " + accountHolder);
        try {
            List<BankAccount> result = this.queryDispatcher.dispatch(new FindAccountByAccountHolderQuery(accountHolder));
            if (result.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(AccountLookupResponse.builder()
                    .accounts(result)
                    .message("Found " + result.size() + " account(s)")
                    .build());

        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed to get account by accountHolder: " + accountHolder, e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/withBalance/{equalityType}/{balance}")
    public ResponseEntity<AccountLookupResponse> getAccountWithBalance(@PathVariable("equalityType") EqualityType equalityType,
                                                                       @PathVariable("balance") double balance) {
        log.info("Received request to get account with balance: " + equalityType + " " + balance);
        try {
            List<BankAccount> result = this.queryDispatcher.dispatch(new FindAccountWithBalanceQuery(equalityType, balance));
            if (result.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(AccountLookupResponse.builder()
                    .accounts(result)
                    .message(String.format("Found %d account(s) with balance %s %.2f", result.size(), equalityType == EqualityType.GREATER_THAN ? ">" : "<", balance))
                    .build());

        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed to get account with balance: " + equalityType + " " + balance, e);
            return ResponseEntity.badRequest().build();
        }
    }
}
