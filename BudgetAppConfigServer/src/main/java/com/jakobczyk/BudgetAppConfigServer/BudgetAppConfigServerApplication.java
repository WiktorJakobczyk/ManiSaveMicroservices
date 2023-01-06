package com.jakobczyk.BudgetAppConfigServer;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@EnableConfigServer
@CrossOrigin(origins = "http://localhost:3000")
public class BudgetAppConfigServerApplication {

	public static void main(String[] args) {
		int nums [] [] = new int [3][3];
		nums[0] = new int[2];
//		SpringApplication.run(BudgetAppConfigServerApplication.class, args);
	}

}
