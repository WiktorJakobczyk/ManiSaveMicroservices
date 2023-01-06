package com.jakobczyk.BudgetAppAuth.model.responseModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserAuthResponseModel {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<String> roles;
}
