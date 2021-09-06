package com.example.patient.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
public class ApplicationConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;

/*    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.formLogin().loginPage("/login");
        http.authorizeRequests().antMatchers("/login").permitAll();
        http.authorizeRequests().anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login")
                .and()
                .logout().permitAll();
        http.csrf();
    }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginPage("/login").defaultSuccessUrl("/patient/all", true);
        http.authorizeRequests().antMatchers("/login", "/css/**").permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        http.csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // ici c'est une methode static
//        auth.inMemoryAuthentication().withUser("admin").password("{noop}admin").roles("ADMIN");
        // ici c'est l'exemple avec JDBC

        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(" select email, password, 1 as enabled "
                        +" from user "
                        +" where email = ?")
                .authoritiesByUsernameQuery("select email , roles"
                    +" from user"
                    + " where email = ?")
                .passwordEncoder(
                        passwordEncoder()
                );


        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
