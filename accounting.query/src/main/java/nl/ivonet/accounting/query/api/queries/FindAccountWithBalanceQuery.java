package nl.ivonet.accounting.query.api.queries;

import lombok.AllArgsConstructor;
import lombok.Data;
import nl.ivonet.accounting.query.api.dto.EqualityType;
import nl.ivonet.cqrs.core.queries.BaseQuery;

@Data
@AllArgsConstructor
public class FindAccountWithBalanceQuery extends BaseQuery {
    private EqualityType equalityType;
    private Double balance;
}
