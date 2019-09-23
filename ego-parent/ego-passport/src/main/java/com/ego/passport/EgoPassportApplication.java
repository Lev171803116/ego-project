package com.ego.passport;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.ego")
public class EgoPassportApplication {

	public static void main(String[] args) {
		SpringApplication.run(EgoPassportApplication.class, args);
	}

}
