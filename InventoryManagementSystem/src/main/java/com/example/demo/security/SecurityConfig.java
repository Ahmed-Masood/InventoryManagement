package com.example.demo.security;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


// authentication
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	 @Autowired
	    private JwtFilter jwtFilter;
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		 http
         .csrf(csrf -> csrf.disable())
         .authorizeHttpRequests(auth -> auth

             // AUTH APIs
             .requestMatchers("/ims.com/auth/**").permitAll()
             .requestMatchers("/h2-console/**").permitAll()

             // USER APIs
             .requestMatchers("/ims.com/user/**")
                 .hasRole("USER")

             
             // ADMIN APIs
             .requestMatchers("/ims.com/admin/**","/ims.com/user/**")
             .hasAnyRole("ADMIN", "USER")
                 
           
                 

             // ANY OTHER REQUEST
             .anyRequest().authenticated()
         )
         .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
         .headers(headers -> headers
                 .frameOptions(frameOptions -> frameOptions
                     .sameOrigin() // Sets X-Frame-Options to SAMEORIGIN
                 ));

     http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

     return http.build();
 }

    @Bean
     PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    
    @Bean
    AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}


// jwtUtil - how to generate the token key first , hto extract the username or role from that token
// JWT filter which will be invoked before the servlet or controller the endpoint or the request 
// authenticatin types 
/* form based
jdbc based 
jwt
oauth --  google / facebook */