package com.jakobczyk.BudgetAppAuth.service;


import com.jakobczyk.BudgetAppAuth.model.User;
import com.jakobczyk.BudgetAppAuth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@RequiredArgsConstructor
@Service
public class AuthService implements UserDetailsService {
    private final UserRepository userRepository;


    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
            User user = userRepository.findByEmail(s).orElseThrow(()->new UsernameNotFoundException(s));
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                new ArrayList<>()
                );
    }


    public User getUserDetailsByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException(email));
    }
}
