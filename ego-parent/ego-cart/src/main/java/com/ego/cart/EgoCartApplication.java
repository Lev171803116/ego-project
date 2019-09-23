package com.ego.cart;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
@ComponentScan("com.ego")
public class EgoCartApplication {

	public static void main(String[] args) {
		SpringApplication.run(EgoCartApplication.class, args);
	}

}
