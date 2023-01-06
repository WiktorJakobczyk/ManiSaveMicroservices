package com.jakobczyk.BudgetAppBudget.controller;

import com.jakobczyk.BudgetAppBudget.model.BudgetDto;
import com.jakobczyk.BudgetAppBudget.model.request.BudgetCreateRequest;
import com.jakobczyk.BudgetAppBudget.model.request.BudgetUpdateRequest;
import com.jakobczyk.BudgetAppBudget.model.response.BudgetResponse;
import com.jakobczyk.BudgetAppBudget.service.BudgetService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/budgets")
@RequiredArgsConstructor
public class BudgetController {
    private final BudgetService budgetService;

    @GetMapping("/{userId}")
    private ResponseEntity<BudgetResponse> getCurrentWeekBudgetForUser(@PathVariable String userId){
        BudgetDto budgetDto = budgetService.getBudget(userId);
        ModelMapper modelMapper = new ModelMapper();
        BudgetResponse response = modelMapper.map(budgetDto, BudgetResponse.class);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{userId}/all")
    private ResponseEntity<List<BudgetResponse>> getAllBudgetsForUser(@PathVariable String userId){
        List<BudgetDto> budgetsDto = budgetService.getBudgets(userId);
        ModelMapper modelMapper = new ModelMapper();
        List<BudgetResponse> response = modelMapper.map(budgetsDto, new TypeToken<List<BudgetResponse>>() {}.getType());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    private ResponseEntity<BudgetResponse> createWeekBudget(@RequestBody BudgetCreateRequest budgetCreateRequest){
        BudgetDto budgetDto = budgetService.getBudgetDtoFromCreateRequest(budgetCreateRequest);
        budgetService.createBudget(budgetDto);
        ModelMapper modelMapper = new ModelMapper();
        BudgetResponse response = modelMapper.map(budgetDto, BudgetResponse.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{userId}")
    private ResponseEntity<BudgetResponse> updateBudget(@RequestBody BudgetUpdateRequest budgetUpdateRequest, @PathVariable String userId){
        BudgetDto budgetDto = budgetService.updateBudget(budgetUpdateRequest, userId);
        ModelMapper modelMapper = new ModelMapper();
        BudgetResponse response = modelMapper.map(budgetDto, BudgetResponse.class);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
