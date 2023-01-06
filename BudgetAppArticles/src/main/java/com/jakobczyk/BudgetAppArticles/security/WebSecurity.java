package com.jakobczyk.BudgetAppArticles.security;


import com.jakobczyk.BudgetAppArticles.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurity extends WebSecurityConfigurerAdapter {
    private final Environment environment;
    private final ArticleService articleService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/articles/**").permitAll()
                .antMatchers(HttpMethod.POST, "/articles/**").hasAnyRole("WRITER", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/articles/**").hasAnyRole("WRITER", "ADMIN")
                .anyRequest().authenticated()
        .and()
        .addFilter(new AuthorizationFilter(authenticationManager(), environment));
        http.headers().frameOptions().disable();
    }
}
