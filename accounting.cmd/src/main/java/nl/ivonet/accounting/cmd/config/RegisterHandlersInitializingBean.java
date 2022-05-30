package nl.ivonet.accounting.cmd.config;

import lombok.AllArgsConstructor;
import nl.ivonet.accounting.cmd.api.commands.*;
import nl.ivonet.cqrs.core.infrastructure.CommandDispatcher;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

import static java.util.logging.Logger.getLogger;


@Component
@AllArgsConstructor
public class RegisterHandlersInitializingBean implements InitializingBean {

    private static final Logger log = getLogger(RegisterHandlersInitializingBean.class.getName());
    private final CommandDispatcher commandDispatcher;
    private final CommandHandler commandHandler;


    @Override
    public void afterPropertiesSet() {
        commandDispatcher.registerHandler(OpenAccountCommand.class, commandHandler::handle);
        commandDispatcher.registerHandler(DepositFundsCommand.class, commandHandler::handle);
        commandDispatcher.registerHandler(WithdrawFundsCommand.class, commandHandler::handle);
        commandDispatcher.registerHandler(CloseAccountCommand.class, commandHandler::handle);
        log.info("Registered AccountingCommand handlers");
    }
}
