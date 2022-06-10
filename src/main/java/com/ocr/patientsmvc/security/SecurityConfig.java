package com.ocr.patientsmvc.security;

import com.ocr.patientsmvc.security.service.UserDetailsServiceImpl;
import org.hibernate.result.UpdateCountOutput;
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

 Note: Authority avoir plusieurs rôles

 https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
 Spring Security without the WebSecurityConfigurerAdapter
*/
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

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


    // Construction, vérification des users, étape Build
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        /*
         // Ces codes sont utilisés quand on n'as pas de class User et Role, mais que les tables users(username,pwd et active),
         // roles(une colonne role) et users_roles(username,role)
        PasswordEncoder passwordEncoder = passwordEncoder();
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select username as principal, password as credentials, active from users where username=?")
                .authoritiesByUsernameQuery("select username as principal, role as role from users_roles where username=?")
                .rolePrefix("ROLE_")
                .passwordEncoder(passwordEncoder);*/

        //Authentification avec spring-boot userDetailsService, spring security faire appel à la méthode de UserDetailsServiceImpl pour retourner un userDetails(User de Spring Security)
        auth.userDetailsService(userDetailsService);

    }

    // Spécifier le droit d'accès
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin(); // login par défaut
        // http.formLogin().loginPage("/login"); // Une méthode(@GetMepping("/login")), Spécifier la page de login (le propre form)
        //Autoriser les users pour accéder à des pages
        http.authorizeHttpRequests().antMatchers("/").permitAll(); //ça ne nécessite pas à une authentification
        http.authorizeHttpRequests().antMatchers("/admin/**").hasAuthority("ADMIN"); //pour les UserDetailsService, Spring Security, cela lie à Collection<GrantedAuthority>
        http.authorizeHttpRequests().antMatchers("/user/**").hasAuthority("USER");  //pour les UserDetailsService, Spring Security

        // Autoriser les ressources statics
        http.authorizeHttpRequests().antMatchers("/webjars/**").permitAll();

        http.authorizeHttpRequests().anyRequest().authenticated();

        // Gestion d'exception, qui retourne la page 403 avec la méthode -> @GetMapping("/403")
        http.exceptionHandling().accessDeniedPage("/403");
    }

/*    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder passwordEncoder = passwordEncoder();
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select username as principal, password as credentials, active from users where username=?")
                .authoritiesByUsernameQuery("select username as principal, role as role from users_roles where username=?")
                .rolePrefix("ROLE_")
                .passwordEncoder(passwordEncoder);
    }*/


  /*  @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.formLogin();
        //Autoriser les users pour accéder à des pages
        http.authorizeHttpRequests().antMatchers("/").permitAll(); //ça ne nécessite pas à une authentification
        http.authorizeHttpRequests().antMatchers("/").permitAll(); //ça ne nécessite pas à une authentification
        http.authorizeHttpRequests().antMatchers("/admin/**").hasRole("ADMIN");
        http.authorizeHttpRequests().antMatchers("/user/**").hasRole("USER");
        http.authorizeHttpRequests().anyRequest().authenticated();
        http.exceptionHandling().accessDeniedPage("/403");
        return http.build();
    }*/


}
