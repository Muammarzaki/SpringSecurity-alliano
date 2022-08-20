package com.security.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.security.services.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

   @Autowired
   private UserService userService;
   
   @Autowired
   private BCryptPasswordEncoder bcrypt;

   @Bean(value = "filterChain")
   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      http.authorizeRequests().antMatchers(HttpMethod.POST, "/register").permitAll()
      .anyRequest().authenticated().and().sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and().authenticationProvider(authProvider());
      return http.build();
   }

   public DaoAuthenticationProvider authProvider(){
      DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
      auth.setUserDetailsService(this.userService);
      auth.setPasswordEncoder(this.bcrypt);
      return auth;
   }

   @Bean(name = "authManager")
   public AuthenticationManager authManager(AuthenticationConfiguration authConf )throws Exception {
      return authConf.getAuthenticationManager();
   }

}
