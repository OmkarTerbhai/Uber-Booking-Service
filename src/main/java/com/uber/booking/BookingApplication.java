package com.uber.booking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EntityScan("com.uber.common.entities")
@EnableDiscoveryClient
public class BookingApplication {

	public static void main(String[] args) {

		SpringApplication.run(BookingApplication.class, args);
	}

}
