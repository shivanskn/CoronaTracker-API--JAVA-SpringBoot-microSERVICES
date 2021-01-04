package coronatracker.mydataCoronatracker;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MydataCoronatrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MydataCoronatrackerApplication.class, args);
	}

}
