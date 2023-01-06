package com.jakobczyk.BudgetAppBudget.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BudgetUpdateRequest {
    private BigDecimal max;
    private BigDecimal current;
}
