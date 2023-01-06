package com.jakobczyk.BudgetAppBudget.model;

import com.jakobczyk.BudgetAppBudget.model.response.ExpenseResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BudgetDto {
    private Long id;
    private String userId;
    private BigDecimal max;
    private BigDecimal current;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<ExpenseResponse> expensesList;

}
