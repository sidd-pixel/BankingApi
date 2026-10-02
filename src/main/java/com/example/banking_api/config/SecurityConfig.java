package com.example.banking_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration 
public class SecurityConfig {

    @Bean PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
    @Bean 
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(csrf->csrf.disable())
                .httpBasic(httpBasic->{})
                .authorizeHttpRequests(auth->auth
                    .requestMatchers("/auth/register").permitAll()
                    .anyRequest().authenticated()
                );

        return http.build();
    }

    

    // @Bean 
    // public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder){
    //     UserDetails user=User.builder()
    //                     .username("siddhant")
    //                     .password(passwordEncoder.encode("password123"))
    //                     .roles("USER")
    //                     .build();   

    //     return new InMemoryUserDetailsManager(user);
    // }

    
}
