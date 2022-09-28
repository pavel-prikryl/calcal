package cz.pokus.calcal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.vaadin.annotations.Push;
import com.vaadin.shared.communication.PushMode;

@Configuration
@EnableJpaRepositories(basePackages = { "cz.pokus.calcal.backend.jpa" })
@EnableConfigurationProperties
@SpringBootApplication
@Push(PushMode.AUTOMATIC)
public class CalCalApplication {

    public static void main(String[] args) {
        SpringApplication.run(CalCalApplication.class, args);
    }

    
}
