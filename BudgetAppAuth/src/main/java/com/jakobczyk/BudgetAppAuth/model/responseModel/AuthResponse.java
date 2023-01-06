package com.jakobczyk.BudgetAppAuth.model.responseModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthResponse {
    private String token;
    private String email;
    private String userId;
    private List<String> roles;
}

