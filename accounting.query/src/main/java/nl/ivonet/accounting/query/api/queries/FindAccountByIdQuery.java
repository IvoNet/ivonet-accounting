package nl.ivonet.accounting.query.api.queries;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nl.ivonet.cqrs.core.queries.BaseQuery;

@Getter
@Setter
@AllArgsConstructor
public class FindAccountByIdQuery extends BaseQuery {
    private String id;
}
