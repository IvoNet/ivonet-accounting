package nl.ivonet.accounting.cmd;

import lombok.AllArgsConstructor;
import nl.ivonet.accounting.cmd.api.commands.*;
import nl.ivonet.cqrs.core.infrastructure.CommandDispatcher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class CommandApplication {
	public static void main(String[] args) {
		SpringApplication.run(CommandApplication.class, args);
	}

}
