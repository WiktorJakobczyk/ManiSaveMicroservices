package com.jakobczyk.BudgetAppAuth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.jakobczyk.BudgetAppAuth.model.User;
import com.jakobczyk.BudgetAppAuth.model.requestModel.LoginRequest;
import com.jakobczyk.BudgetAppAuth.model.responseModel.AuthResponse;
import com.jakobczyk.BudgetAppAuth.service.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthService userService;
    private final Environment environment;
    public static final String AUTHORITIES_KEY = "scopes";

    public AuthenticationFilter(AuthService userService, Environment environment, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.environment = environment;
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try{
           LoginRequest creds = new ObjectMapper().readValue(req.getInputStream(), LoginRequest.class);
           return getAuthenticationManager().authenticate(
                   new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>())
           );
       } catch (IOException e) {
           throw new RuntimeException(e);
       }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res,
                                            FilterChain chain, Authentication authResult) throws IOException {
        String userName = ((org.springframework.security.core.userdetails.User)authResult.getPrincipal()).getUsername();
        User userDetails = userService.getUserDetailsByEmail(userName);
        List<@NotEmpty String> roles = userDetails.getRoles().stream().map(role -> role.getName()).collect(Collectors.toList());
        String token = Jwts.builder()
                .claim(AUTHORITIES_KEY, roles)
                .setSubject(userDetails.getUserId())
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(environment.getProperty("token.expiration_time"))))
                .signWith(SignatureAlgorithm.HS512, environment.getProperty("token.secret"))
                .compact();
        res.addHeader("token", token);
        res.addHeader("userId", userDetails.getUserId());
        res.addHeader("email", userDetails.getEmail());
        PrintWriter out = res.getWriter();
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");

        Gson gson = new Gson();
        AuthResponse authResponse = new AuthResponse(token,userDetails.getEmail(), userDetails.getUserId(), roles);
        String employeeJsonString = gson.toJson(authResponse);
        out.print(employeeJsonString);
        out.flush();
    }


}
