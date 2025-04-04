package ch.luethy.juan.timestamp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class TimestampApplication {

	public static void main(String[] args) {
		SpringApplication.run(TimestampApplication.class, args);
	}

}
