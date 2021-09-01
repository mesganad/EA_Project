package edu.miu.cs.cs544;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import edu.miu.cs.cs544.model.Airline;
import edu.miu.cs.cs544.service.AirlineServiceClient;

//@EnableCircuitBreaker
@SpringBootApplication
@EnableDiscoveryClient
public class Application {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(Application.class, args);

		AirlineServiceClient client = context.getBean(AirlineServiceClient.class);

		List<Airline> airlines = client.findAll();
		airlines.forEach(System.out::println);

//		Airline airline = client.findById("US");
//		System.out.println("Single country starts here:");
//		System.out.println(airline);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
