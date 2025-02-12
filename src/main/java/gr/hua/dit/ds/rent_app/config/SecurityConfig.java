package gr.hua.dit.ds.rent_app.config;

import gr.hua.dit.ds.rent_app.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


/*
Security configuration class for setting up security
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    private UserService userService;

    private UserDetailsService userDetailsService;

    private BCryptPasswordEncoder passwordEncoder;


    public SecurityConfig(UserService userService, UserDetailsService userDetailsService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

   /*
   Configures which URLs are publicly accessible, restricts access to certain URLs based on roles,
   configures login and logout behavior
    */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        /*Publicly accessible URls*/
                        .requestMatchers("/", "/home", "/register", "/saveUser", "/images/**", "/js/**", "/css/**").permitAll()
                        /*URLs accessible only for users with role OWNER*/
                        .requestMatchers("/renter/**").hasAnyRole("ADMIN", "OWNER")
                        /*URLs accessible only for users with role ADMIN*/
                        .requestMatchers("/owner/**", "/request/**", "/verifyUser/**").hasRole("ADMIN")
                        /*All other requests require authentication*/
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        /*Custom login page*/
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/property", true)//redirect after successful login
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .permitAll())
                .logout((logout) -> logout.permitAll());
        return http.build();
    }

}
