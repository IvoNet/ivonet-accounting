package nl.ivonet.accounting.cmd.api.commands;

public interface CommandHandler {
    void handle(OpenAccountCommand command);

    void handle(CloseAccountCommand command);

    void handle(DepositFundsCommand command);

    void handle(WithdrawFundsCommand command);
}
