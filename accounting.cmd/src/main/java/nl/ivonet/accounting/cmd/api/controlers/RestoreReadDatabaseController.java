package nl.ivonet.accounting.cmd.api.controlers;

import lombok.AllArgsConstructor;
import nl.ivonet.accounting.cmd.api.commands.RestoreReadDatabaseCommand;
import nl.ivonet.accounting.common.dto.BaseResponse;
import nl.ivonet.cqrs.core.infrastructure.CommandDispatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.logging.Logger.getLogger;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v1/restoreReadDatabase")
public class RestoreReadDatabaseController {
    private static final Logger log = getLogger(RestoreReadDatabaseController.class.getName());
    private final CommandDispatcher commandDispatcher;

    @PostMapping
    public ResponseEntity<BaseResponse> restoreReadDatabase() {
        log.info("restoreReadDatabase");
        try {
            this.commandDispatcher.dispatch(new RestoreReadDatabaseCommand());
            return new ResponseEntity<>(new BaseResponse("Database restored"), CREATED);
        } catch (IllegalStateException e) {
            log.warning("Client made a bad request " + e.getMessage());
            return ResponseEntity.badRequest().body(new BaseResponse(e.getMessage()));
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage(), e);
            return ResponseEntity.internalServerError().body(new BaseResponse(e.getMessage()));
        }
    }
}
