package com.ego.portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.ego")
public class EgoPortalApplication {

	public static void main(String[] args) {
		SpringApplication.run(EgoPortalApplication.class, args);
	}

}
