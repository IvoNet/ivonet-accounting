package nl.ivonet.accounting.cmd.api.commands;


import lombok.Data;
import nl.ivonet.accounting.common.dto.AccountType;
import nl.ivonet.cqrs.core.commands.BaseCommand;

@Data
public class OpenAccountCommand extends BaseCommand {

    private String accountHolder;
    private String accountNumber;
    private AccountType accountType;
    private double initialBalance;
}
