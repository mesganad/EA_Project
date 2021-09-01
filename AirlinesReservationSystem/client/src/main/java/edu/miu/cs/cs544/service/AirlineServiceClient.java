package edu.miu.cs.cs544.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import edu.miu.cs.cs544.model.Airline;

@Service
public class AirlineServiceClient {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private DiscoveryClient discoveryClient;

	@Value("${spring-boot-server.name}")
	private String serverName;

//	List<Airline> airlinesCache = new ArrayList<>();

//	@HystrixCommand(fallbackMethod = "findByIdFallback")
//	public Airline findById(String countryCode) {
//		String url = getBaseServiceUrl() + "/countries/" + countryCode;
//		return restTemplate.exchange(url, HttpMethod.GET, createHttpEntity(), Airline.class).getBody();
//	}

//	public Airline findByIdFallback(String countryCode) {
//		System.out.println("Inside findByIdFallback()");
//		return new Airline();
//	}

	public List<Airline> findAll() {
		Airline[] airlines = restTemplate
				.getForObject(getBaseServiceUrl() + "/airlines",
						Airline[].class);

		return Arrays.asList(airlines);
	}

	private String getBaseServiceUrl() {
		List<ServiceInstance> serviceInstances = discoveryClient.getInstances(serverName);
		serviceInstances.forEach(System.out::println);
		return serviceInstances.get(0).getUri().toString();
	}
}
