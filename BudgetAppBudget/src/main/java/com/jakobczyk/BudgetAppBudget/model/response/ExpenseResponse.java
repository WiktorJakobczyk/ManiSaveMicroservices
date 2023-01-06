package com.jakobczyk.BudgetAppBudget.model.response;


import com.jakobczyk.BudgetAppBudget.model.ExpenseCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExpenseResponse {
    private Long id;
    private BigDecimal cost;
    private Date created;
    private ExpenseCategory category;
}
