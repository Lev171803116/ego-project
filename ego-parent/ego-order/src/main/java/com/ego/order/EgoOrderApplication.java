package com.ego.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.ego")
public class EgoOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(EgoOrderApplication.class, args);
	}

}
