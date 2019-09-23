package com.ego.item;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.ego")
public class EgoItemApplication {

	public static void main(String[] args) {
		SpringApplication.run(EgoItemApplication.class, args);
	}

}
