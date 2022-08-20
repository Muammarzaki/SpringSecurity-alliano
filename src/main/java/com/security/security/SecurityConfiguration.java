package com.security.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

   // @Autowired
   // private UserService userService;

   // public BCryptPasswordEncoder bcryptPasswordEncoder() {
   // return new BCryptPasswordEncoder(10);
   // }

   // @Autowired
   // private BCryptPasswordEncoder bcrypt;

   @Bean
   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      // disarankan manulis dgn rapi
      http.authorizeRequests().antMatchers(HttpMethod.POST, "/register").permitAll()
            .anyRequest().authenticated();
      // untuk rest session tidak disarankan
      // [masalah]ketika saya login dan mencoba login dgn username berbeda tetapi
      // password sama
      // tetap bisa di akses sedangkan jika saya ubah password 401
      http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);

      // metode auth
      http.httpBasic();

      // .and().authenticationProvider(authProvider());

      return http.build();
   }

   // public DaoAuthenticationProvider authProvider() {
   // DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
   // auth.setUserDetailsService(this.userService);
   // auth.setPasswordEncoder(this.bcryptPasswordEncoder());
   // return auth;
   // }

   @Bean
   public AuthenticationManager authManager(AuthenticationConfiguration authConf) throws Exception {
      // akan memanggil userservice dan bcryp melalui aplikasi context
      return authConf.getAuthenticationManager();
   }

}
