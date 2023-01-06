package com.jakobczyk.BudgetAppApiGateway;

import io.netty.handler.logging.LogLevel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.logging.AdvancedByteBufFormat;



@SpringBootApplication
public class BudgetAppApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(BudgetAppApiGatewayApplication.class, args);
	}

}
