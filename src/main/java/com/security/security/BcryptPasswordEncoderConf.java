package com.security.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class BcryptPasswordEncoderConf {

   @Bean
   public BCryptPasswordEncoder bcrypt() {
      return new BCryptPasswordEncoder(10);
   }
}
