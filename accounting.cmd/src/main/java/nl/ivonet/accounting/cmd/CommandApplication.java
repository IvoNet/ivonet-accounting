package nl.ivonet.accounting.cmd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;

@SpringBootApplication
@EnableWebSecurity
@Configuration
public class CommandApplication {
    public static void main(String[] args) {
        SpringApplication.run(CommandApplication.class, args);
    }


    @Bean
    protected DefaultSecurityFilterChain securityChain(HttpSecurity http) throws Exception {
        return http.authorizeRequests()
                .antMatchers("/manage/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .build();
    }
}
