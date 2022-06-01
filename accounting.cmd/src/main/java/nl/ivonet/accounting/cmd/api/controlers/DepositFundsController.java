package nl.ivonet.accounting.cmd.api.controlers;

import lombok.AllArgsConstructor;
import nl.ivonet.accounting.cmd.api.commands.DepositFundsCommand;
import nl.ivonet.accounting.common.dto.BaseResponse;
import nl.ivonet.cqrs.core.infrastructure.CommandDispatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.logging.Logger.getLogger;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v1/depositFunds")
public class DepositFundsController {

    private static final Logger log = getLogger(DepositFundsController.class.getName());

    private final CommandDispatcher commandDispatcher;

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> depositFunds(@PathVariable("id") String accountId, @RequestBody DepositFundsCommand command) {
        log.info("depositFunds: " + command);
        try {
            command.setId(accountId);
            this.commandDispatcher.dispatch(command);
            return ResponseEntity.ok(new BaseResponse("Funds deposited"));
        } catch (IllegalStateException | IllegalArgumentException e) {
            log.warning("Client made a bad request " + e.getMessage());
            return ResponseEntity.badRequest().body(new BaseResponse(e.getMessage()));
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage(), e);
            return ResponseEntity.internalServerError().body(new BaseResponse(e.getMessage()));
        }
    }

}
