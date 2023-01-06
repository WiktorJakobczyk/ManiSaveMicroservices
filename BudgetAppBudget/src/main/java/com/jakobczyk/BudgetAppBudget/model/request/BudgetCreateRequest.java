package com.jakobczyk.BudgetAppBudget.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BudgetCreateRequest {
    private String userId;
    private BigDecimal max;
    private LocalDateTime startDate;
}
