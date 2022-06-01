package nl.ivonet.accounting.query.infrastructure;

import nl.ivonet.cqrs.core.domain.BaseEntity;
import nl.ivonet.cqrs.core.infrastructure.QueryDispatcher;
import nl.ivonet.cqrs.core.queries.BaseQuery;
import nl.ivonet.cqrs.core.queries.QueryHandlerMethod;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Concrete Mediator for handling queries.
 */
@Service
public class AccountingQueryDispatcher implements QueryDispatcher {

    @SuppressWarnings("rawtypes")
    private final Map<Class<? extends BaseQuery>, List<QueryHandlerMethod>> routes;

    public AccountingQueryDispatcher() {
        this.routes = new HashMap<>();
    }

    @Override
    public <T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod<T> handler) {
        this.routes.computeIfAbsent(type, k -> new LinkedList<>()).add(handler);
    }

    @Override
    public <U extends BaseEntity> List<U> dispatch(BaseQuery query) {
        var handlers = this.routes.get(query.getClass());
        if (handlers == null || handlers.isEmpty()) {
            throw new IllegalStateException("No handler registered for query " + query.getClass());
        }
        if (handlers.size() > 1) {
            throw new IllegalStateException("Multiple handlers registered for query " + query.getClass());
        }
        //noinspection unchecked
        return handlers.get(0).handle(query);

    }
}
