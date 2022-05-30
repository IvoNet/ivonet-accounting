package nl.ivonet.accounting.cmd.api.commands;

import lombok.Data;
import nl.ivonet.cqrs.core.commands.BaseCommand;

@Data
public class DepositFundsCommand extends BaseCommand {
    private double amount;
}
