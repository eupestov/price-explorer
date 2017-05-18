package com.example.priceexplorer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * Created by eugene on 18/05/17.
 */
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/api/admin/**").hasRole("ADMIN")
                    .antMatchers("/api/price/**").hasRole("USER")
                    .antMatchers("/api/**").authenticated()
                    .antMatchers("/**").permitAll()
                .and()
                    .httpBasic()
                .and()
                    // to allow proxy middleware with SPA
                    .csrf().disable();

    }

    @Bean
    public UserDetailsService userDetailsService() {
        // real app will authenticate users against a DB or will use OAuth
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user").password("user").roles("USER").build());
        manager.createUser(User.withUsername("admin").password("admin").roles("USER", "ADMIN").build());
        return manager;
    }
}
