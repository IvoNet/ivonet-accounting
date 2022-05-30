package nl.ivonet.accounting.cmd.api.commands;

import nl.ivonet.cqrs.core.commands.BaseCommand;

public class CloseAccountCommand extends BaseCommand {
    public CloseAccountCommand(String id) {
        super(id);
    }
}
