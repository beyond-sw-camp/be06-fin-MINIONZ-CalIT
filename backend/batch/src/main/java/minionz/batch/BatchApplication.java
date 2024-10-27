package minionz.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = {
        "minionz.common",
        "minionz.batch"
})

@EntityScan(basePackages = {
        "minionz.common",
        "minionz.batch"
})

@ComponentScan(basePackages = {
        "minionz.common",
        "minionz.batch"
})

@SpringBootApplication
public class BatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(BatchApplication.class, args);
    }

}
