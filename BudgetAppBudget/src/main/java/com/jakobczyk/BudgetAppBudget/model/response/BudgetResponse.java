package com.jakobczyk.BudgetAppBudget.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BudgetResponse {
    private Long id;
    private String userId;
    private BigDecimal max;
    private BigDecimal current;
    private LocalDateTime start;
    private LocalDateTime end;
    private List<ExpenseResponse> expensesList;
}
