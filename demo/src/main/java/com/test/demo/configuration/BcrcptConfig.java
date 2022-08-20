package com.test.demo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class BcrcptConfig {
   
   @Bean(value = "bcryptEncode")
   public BCryptPasswordEncoder bcrypt(){
      return new BCryptPasswordEncoder(10);
   }
}
