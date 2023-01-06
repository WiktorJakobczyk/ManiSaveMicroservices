package com.jakobczyk.BudgetAppAuth.repository;



import com.jakobczyk.BudgetAppAuth.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);
}
