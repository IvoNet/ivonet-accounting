package nl.ivonet.accounting.cmd.api.controlers;

import lombok.AllArgsConstructor;
import nl.ivonet.accounting.cmd.api.commands.WithdrawFundsCommand;
import nl.ivonet.accounting.common.dto.BaseResponse;
import nl.ivonet.cqrs.core.infrastructure.CommandDispatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.logging.Logger.getLogger;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/withdrawFunds")
public class WithdrawFundsController {
    private static final Logger log = getLogger(WithdrawFundsController.class.getName());
    private final CommandDispatcher commandDispatcher;

    @PutMapping("{id}")
    public ResponseEntity<BaseResponse> withdrawFunds(@PathVariable("id") String accountId, @RequestBody WithdrawFundsCommand command) {
        log.info("withdrawFunds: " + command);
        try {
            command.setId(accountId);
            this.commandDispatcher.dispatch(command);
            return ResponseEntity.ok(new BaseResponse("Funds withdrawn"));
        } catch (IllegalStateException e) {
            log.warning("Client made a bad request " + e.getMessage());
            return ResponseEntity.badRequest().body(new BaseResponse(e.getMessage()));
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage(), e);
            return ResponseEntity.internalServerError().body(new BaseResponse(e.getMessage()));
        }
    }
}
