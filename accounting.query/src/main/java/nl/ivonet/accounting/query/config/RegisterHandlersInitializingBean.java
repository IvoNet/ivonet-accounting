package nl.ivonet.accounting.query.config;

import lombok.AllArgsConstructor;
import nl.ivonet.accounting.query.api.queries.*;
import nl.ivonet.cqrs.core.infrastructure.QueryDispatcher;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

import static java.util.logging.Logger.getLogger;

@Component
@AllArgsConstructor
public class RegisterHandlersInitializingBean implements InitializingBean {

    private static final Logger log = getLogger(RegisterHandlersInitializingBean.class.getName());

    private final QueryDispatcher queryDispatcher;
    private final QueryHandler queryHandler;

    @Override
    public void afterPropertiesSet() {
        this.queryDispatcher.registerHandler(FindAllAccountsQuery.class, this.queryHandler::handle);
        this.queryDispatcher.registerHandler(FindAccountByIdQuery.class, this.queryHandler::handle);
        this.queryDispatcher.registerHandler(FindAccountByAccountHolderQuery.class, this.queryHandler::handle);
        this.queryDispatcher.registerHandler(FindAccountWithBalanceQuery.class, this.queryHandler::handle);
        log.info("Registered AccountingQuery handlers");
    }
}
