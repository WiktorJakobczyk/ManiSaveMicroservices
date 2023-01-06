package com.jakobczyk.BudgetAppBudget.Client;

import com.jakobczyk.BudgetAppBudget.model.response.ExpenseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@FeignClient(name="expenses-ws", fallback = ExpensesFallback.class)
public interface ExpensesClient {

    @GetMapping("/expenses/{userId}/from/{from}/to/{to}")
    List<ExpenseResponse> getExpenses(@RequestHeader(value = "Authorization", required = true) String authorizationHeader,
                                      @PathVariable String userId,
                                      @PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") Date from,
                                      @PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") Date to);
}

@Component
class ExpensesFallback implements ExpensesClient{

    @Override
    public List<ExpenseResponse> getExpenses(@RequestHeader(value = "Authorization", required = true) String authorizationHeader,
                                      @PathVariable String userId,
                                      @PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") Date from,
                                      @PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") Date to){
        return new ArrayList<>();
    };
}

