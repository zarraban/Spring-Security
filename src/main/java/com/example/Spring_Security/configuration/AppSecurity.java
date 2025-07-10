package com.example.Spring_Security.configuration;

import com.example.Spring_Security.service.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.AntPathMatcher;

@Configuration
@EnableWebSecurity
public class AppSecurity {

    private final UserDetailsService userDetailsService;

    public AppSecurity(
            UserDetailsServiceImpl userDetailsService
    ){
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configGlobal(AuthenticationManagerBuilder auth, UserDetailsService userDetailsService) throws Exception{
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(getPasswordEncoder());
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize->
                        authorize.requestMatchers("/register/**").permitAll()
                                .requestMatchers("/index").permitAll()
                                .requestMatchers("/users").hasRole("ADMIN"))
                .formLogin(form->
                        form.loginPage("/login")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/users")
                                .permitAll())
                .logout(
                        logout->
                                logout.logoutUrl("/logout")
                                        .logoutSuccessUrl("/index").permitAll());
                        return httpSecurity.build();
    }

}
