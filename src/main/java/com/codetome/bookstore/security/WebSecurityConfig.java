package com.codetome.bookstore.security;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
public class WebSecurityConfig {

    private DataSource dataSource;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        String[] resources = new String[]{
                "/",
                "/css/**",
                "/plugins/**",
                "/img/**",
                "/css/**",
                "/js/**",
                "/plugins/**",
                "/webfonts/**",
                "/category/**",
                "/book/**",
                "/cart/**",
                "/auth/login"
        };
        http.authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/user/**").hasAuthority("USER_ROLE")
                        .requestMatchers("/admin/**").hasAuthority("ADMIN_ROLE")
                        .requestMatchers(resources).permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/auth/login/process")
                        .defaultSuccessUrl("/",true)
                        .permitAll())
                .logout((logout) -> logout.permitAll());

        return http.build();
    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        final String sqlUserName = "select u.username, u.password, u.enabled from user u where u.username = ?";
        final String sqlAuthorities = "select a.username, a.authority from authorities a where a.username = ?";

        auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery(sqlUserName)
                .authoritiesByUsernameQuery(sqlAuthorities).passwordEncoder(new BCryptPasswordEncoder());
    }

}
