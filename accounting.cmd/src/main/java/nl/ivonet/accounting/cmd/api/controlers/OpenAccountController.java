package nl.ivonet.accounting.cmd.api.controlers;


import lombok.AllArgsConstructor;
import nl.ivonet.accounting.cmd.api.commands.OpenAccountCommand;
import nl.ivonet.accounting.cmd.api.dto.OpenAccountResponse;
import nl.ivonet.accounting.common.dto.BaseResponse;
import nl.ivonet.cqrs.core.infrastructure.CommandDispatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.logging.Logger.getLogger;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v1/openBankAccount")
public class OpenAccountController {
    private static final Logger log = getLogger(OpenAccountController.class.getName());
    private final CommandDispatcher commandDispatcher;

    @PostMapping
    public ResponseEntity<BaseResponse> openAccount(@RequestBody OpenAccountCommand command) {
        log.info("openAccount: " + command);
        var id = UUID.randomUUID().toString();
        command.setId(id);
        try {
            commandDispatcher.dispatch(command);
            return new ResponseEntity<>(new OpenAccountResponse("Account created", id), CREATED);
        } catch (IllegalStateException e) {
            log.warning("Client made a bad request " + e.getMessage());
            return ResponseEntity.badRequest().body(new BaseResponse(e.getMessage()));
        } catch (Exception e) {
//            var errorMessage = MessageFormat.format("Error while processing request to open a new bank account for {}", id);
            log.log(Level.SEVERE, e.getMessage(), e);
            return ResponseEntity.internalServerError().body(new BaseResponse(e.getMessage()));
        }
    }
}
