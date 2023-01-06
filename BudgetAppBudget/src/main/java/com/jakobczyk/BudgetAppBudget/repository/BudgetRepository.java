package com.jakobczyk.BudgetAppBudget.repository;

import com.jakobczyk.BudgetAppBudget.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
    @Query("SELECT b FROM Budget b WHERE b.userId=:userId AND :currentDate BETWEEN b.startDate and b.endDate")
    Optional<Budget> getCurrentByUserId(String userId, LocalDateTime currentDate);

    List<Budget> findAllByUserId(String userId);
}