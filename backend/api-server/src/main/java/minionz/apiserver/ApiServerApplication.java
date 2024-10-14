package minionz.apiserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = {
        "minionz.common",
        "minionz.apiserver"
})

@EntityScan(basePackages = {
        "minionz.common",
        "minionz.apiserver"
})

@ComponentScan(basePackages = {
        "minionz.common",
        "minionz.apiserver"
})

@SpringBootApplication
public class ApiServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiServerApplication.class, args);
    }

}
