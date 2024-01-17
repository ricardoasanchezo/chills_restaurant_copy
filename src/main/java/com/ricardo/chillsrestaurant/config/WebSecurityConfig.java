package com.ricardo.chillsrestaurant.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static com.ricardo.chillsrestaurant.user.Permission.*;
import static com.ricardo.chillsrestaurant.user.Role.EMPLOYEE;
import static com.ricardo.chillsrestaurant.user.Role.MANAGER;
import static org.springframework.http.HttpMethod.*;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity()
public class WebSecurityConfig
{
    private final CustomLoginFailureHandler customLoginFailureHandler;
    private final CustomLoginSuccessHandler customLoginSuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
    {
        String[] staticResources = {
                "/style/**",
                "/images/**",
                "/script/**",};

        http
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**", "/homepage", "/login")
                        .permitAll()
                        //.requestMatchers("/auth/employee/login")
                        //.permitAll()
                        .requestMatchers(staticResources)
                        .permitAll()

                        // Anonymous registration
//                        .requestMatchers(GET, "/auth/register").anonymous()
//                        .requestMatchers(POST, "/auth/register").anonymous()

                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/login")
                        .failureHandler(customLoginFailureHandler)
                        .successHandler(customLoginSuccessHandler)
                        .permitAll()
                )
                .logout(Customizer.withDefaults())
        ;

        return http.build();
    }
}
