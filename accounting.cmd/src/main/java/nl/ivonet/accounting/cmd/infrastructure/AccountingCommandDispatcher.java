package nl.ivonet.accounting.cmd.infrastructure;

import nl.ivonet.cqrs.core.commands.BaseCommand;
import nl.ivonet.cqrs.core.commands.CommandHandlerMethod;
import nl.ivonet.cqrs.core.infrastructure.CommandDispatcher;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Concrete Mediator.
 */
@Service
public class AccountingCommandDispatcher implements CommandDispatcher {
    @SuppressWarnings("rawtypes")
    private final Map<Class<? extends BaseCommand>, List<CommandHandlerMethod>> routes;

    public AccountingCommandDispatcher() {
        this.routes = new HashMap<>();
    }

    @Override
    public <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler) {
        this.routes.computeIfAbsent(type, k -> new LinkedList<>()).add(handler);
    }

    @Override
    public void dispatch(BaseCommand command) {
        var handlers = this.routes.get(command.getClass());
        if (handlers == null) {
            throw new IllegalStateException("No handler registered for command " + command.getClass());
        }
        //noinspection unchecked
        handlers.forEach(h -> h.handle(command));
    }

}
