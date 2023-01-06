package com.jakobczyk.BudgetAppBudget.Exception;

public class BudgetNotFound  extends RuntimeException {
    private static final long serialVersionUID = 2510359808079926201L;

    public BudgetNotFound(String message) {
        super(message);
    }
}
