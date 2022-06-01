package nl.ivonet.accounting.cmd.api.controlers;

import lombok.AllArgsConstructor;
import nl.ivonet.accounting.cmd.api.commands.CloseAccountCommand;
import nl.ivonet.accounting.common.dto.BaseResponse;
import nl.ivonet.cqrs.core.infrastructure.CommandDispatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.logging.Logger.getLogger;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/closeAccount")
public class CloseAccountController {
    private static final Logger log = getLogger(CloseAccountController.class.getName());
    private final CommandDispatcher commandDispatcher;

    @DeleteMapping("{id}")
    public ResponseEntity<BaseResponse> closeAccount(@PathVariable("id") String accountId) {
        log.info("closeAccount: " + accountId);
        try {
            this.commandDispatcher.dispatch(new CloseAccountCommand(accountId));
            return ResponseEntity.ok(new BaseResponse("Account closed"));
        } catch (IllegalStateException e) {
            log.warning("Client made a bad request " + e.getMessage());
            return ResponseEntity.badRequest().body(new BaseResponse(e.getMessage()));
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage(), e);
            return ResponseEntity.internalServerError().body(new BaseResponse(e.getMessage()));
        }
    }

}
