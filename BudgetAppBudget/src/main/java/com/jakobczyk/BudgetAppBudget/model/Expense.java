package com.jakobczyk.BudgetAppBudget.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
//@Entity
//@Table(name="expenses")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private BigDecimal cost;
    @Column(nullable = false)
    private String userId;
    @Column(nullable = false)
    private Date created;

    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinTable(name="expense_category",
            joinColumns={@JoinColumn(name="EXPENSE_ID", referencedColumnName="ID")},
            inverseJoinColumns={@JoinColumn(name="CATEGORY_ID", referencedColumnName="ID")})
    private ExpenseCategory category;
}
