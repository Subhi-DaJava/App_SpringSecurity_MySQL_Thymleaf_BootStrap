package com.ocr.patientsmvc.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;
import javax.xml.ws.soap.Addressing;

/*
https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
Spring Security without the WebSecurityConfigurerAdapter
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //In-Memory Authentication pour tester l'application utiliser la mémoire
  /*  @Bean
    public InMemoryUserDetailsManager userDetailsService(){

        PasswordEncoder passwordEncoder = passwordEncoder();

        UserDetails user = User.builder()
                .username("user1")
                .password(passwordEncoder.encode("1234"))   //.password("{noop}1234") éviter à encoder (sans passwordEncoder)
                .roles("USER")
                .build();
        UserDetails user2 = User.builder()
                .username("user2")
                .password(passwordEncoder.encode("5678"))   //.password("{noop}1234") éviter à encoder (sans passwordEncoder)
                .roles("USER")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("91011"))   //.password("{noop}1234") éviter à encoder (sans passwordEncoder)
                .roles("USER","ADMIN")
                .build();
        //avoir le Bcrypt
        //System.out.println(passwordEncoder.encode("1234"));

        return new InMemoryUserDetailsManager(user,user2,admin);
    }*/
    // https://www.baeldung.com/spring-security-jdbc-authentication
    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        PasswordEncoder passwordEncoder = passwordEncoder();
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select username as principal, password as credentials, active from users where username=?")
                .authoritiesByUsernameQuery("select username as principal, role as role from users_roles where username=?")
                .rolePrefix("ROLE_")
                .passwordEncoder(passwordEncoder);

    }
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.formLogin();
        //Autoriser les users pour accéder à des pages
        http.authorizeHttpRequests().antMatchers("/").permitAll(); //ça ne nécessite pas à une authentification
        http.authorizeHttpRequests().antMatchers("/admin/**").hasRole("ADMIN");
        http.authorizeHttpRequests().antMatchers("/user/**").hasRole("USER");

        //Autoriser les ressources statics
        http.authorizeHttpRequests().antMatchers("/webjars/**").permitAll();

        http.authorizeHttpRequests().anyRequest().authenticated();
        http.exceptionHandling().accessDeniedPage("/403");
    }


  /*  @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.formLogin();
        //Autoriser les users pour accéder à des pages
        http.authorizeHttpRequests().antMatchers("/").permitAll(); //ça ne nécessite pas à une authentification
        http.authorizeHttpRequests().antMatchers("/admin/**").hasRole("ADMIN");
        http.authorizeHttpRequests().antMatchers("/user/**").hasRole("USER");
        http.authorizeHttpRequests().anyRequest().authenticated();
        http.exceptionHandling().accessDeniedPage("/403");
        return http.build();
    }*/

    @Bean
    PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }

}
