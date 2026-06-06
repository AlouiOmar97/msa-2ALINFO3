package esprit.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class JobApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobApplication.class, args);
	}

	@Autowired
	private JobRepository jobRepository;

	@Bean
	ApplicationRunner init() {
		return (args) -> {

			// Save
			jobRepository.save(new Job(
					"Financial Manager",
					"Responsible for managing company finances",
					true));

			jobRepository.save(new Job(
					"IT Support",
					"Maintain and support IT infrastructure",
					false));

			jobRepository.save(new Job(
					"HR Officer",
					"Manage recruitment and employee relations",
					true));

			// Fetch
			jobRepository.findAll().forEach(System.out::println);
		};
	}


}
