package example.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ExampleUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExampleUserApplication.class, args);
	}
}
