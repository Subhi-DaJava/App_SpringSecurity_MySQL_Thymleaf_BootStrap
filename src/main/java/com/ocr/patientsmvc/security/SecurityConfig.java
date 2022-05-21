package com.ocr.patientsmvc.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/*
https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
Spring Security without the WebSecurityConfigurerAdapter
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    //Pour tester l'application
    @Bean
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
        return new InMemoryUserDetailsManager(user,user2,admin);
    }



    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.formLogin();
        http.authorizeHttpRequests().anyRequest().authenticated();
        return http.build();
    }
    @Bean
    PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }

}
