package nl.ivonet.accounting.query.api.queries;

import lombok.AllArgsConstructor;
import nl.ivonet.accounting.query.api.dto.EqualityType;
import nl.ivonet.accounting.query.domain.AccountRepository;
import nl.ivonet.cqrs.core.domain.BaseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AccountingQueryHandler implements QueryHandler {

    private final AccountRepository accountRepository;

    @Override
    public List<BaseEntity> handle(FindAllAccountsQuery query) {
        List<BaseEntity> result = new ArrayList<>();
        this.accountRepository.findAll().forEach(result::add);
        return result;
    }

    @Override
    public List<BaseEntity> handle(FindAccountByIdQuery query) {
        List<BaseEntity> result = new ArrayList<>();
        this.accountRepository.findById(query.getId()).ifPresent(result::add);
        return result;
    }

    @Override
    public List<BaseEntity> handle(FindAccountByAccountHolderQuery query) {
        List<BaseEntity> result = new ArrayList<>();
        this.accountRepository.findByAccountHolder(query.getAccountHolder()).ifPresent(result::add);
        return result;
    }

    @Override
    public List<BaseEntity> handle(FindAccountWithBalanceQuery query) {
        return query.getEqualityType() == EqualityType.GREATER_THAN
                ? this.accountRepository.findByBalanceGreaterThan(query.getBalance())
                : this.accountRepository.findByBalanceLessThan(query.getBalance());
    }
}
