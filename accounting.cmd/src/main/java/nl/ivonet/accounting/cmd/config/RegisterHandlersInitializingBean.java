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
        this.commandDispatcher.registerHandler(OpenAccountCommand.class, this.commandHandler::handle);
        this.commandDispatcher.registerHandler(DepositFundsCommand.class, this.commandHandler::handle);
        this.commandDispatcher.registerHandler(WithdrawFundsCommand.class, this.commandHandler::handle);
        this.commandDispatcher.registerHandler(CloseAccountCommand.class, this.commandHandler::handle);
        log.info("Registered AccountingCommand handlers");
    }
}
