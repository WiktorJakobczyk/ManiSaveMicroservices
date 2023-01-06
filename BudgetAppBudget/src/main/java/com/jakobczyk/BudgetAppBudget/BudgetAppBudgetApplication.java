package com.jakobczyk.BudgetAppBudget;

import com.jakobczyk.BudgetAppBudget.model.Budget;
import com.jakobczyk.BudgetAppBudget.repository.BudgetRepository;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@SpringBootApplication
@EnableFeignClients
public class BudgetAppBudgetApplication {

	public static void main(String[] args) {
		SpringApplication.run(BudgetAppBudgetApplication.class, args);
	}

}
