package com.test.demo.app;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import lombok.Getter;
import lombok.Setter;

@EnableWebMvc
@RestController
@RequestMapping(path = "/")
public class App {
   
   @Autowired
   private AppService appService;
   
   @PostMapping(path = "register")
   public AppEntity register(@RequestBody AppEntity entity){
      return appService.register(entity);
   }

   @GetMapping(path = "home")
   public String home(){
      return "<h1>hello wod</h1>";
   }

   @GetMapping(path = "/findAll")
   public Iterable<AppEntity> findAll(){
      return appService.findAll();
   }
}

@Service
class AppService implements UserDetailsService {

   @Autowired
   private Apprepository apprepository;

   public AppEntity register(AppEntity entity) {
      entity.setRole(UserRole.USER);
      return apprepository.save(entity);
   }

   public Iterable<AppEntity> findAll(){
      return apprepository.findAll();
   }

   @Override
   public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
      return apprepository.findByEmail(email).orElseThrow( () -> new UsernameNotFoundException("user with email "+email+" empty"));
   }

}

@Repository
interface Apprepository extends CrudRepository<AppEntity, Long> {

   public Optional<AppEntity> findByEmail(String email);
}

@Entity
@Table(name = "appEntity")
@Setter
@Getter
class AppEntity implements UserDetails {
   
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long id;
   
   @Column(nullable = false, unique = true)
   private String email;
   
   @Column(nullable = false)
   private String password;

   @Enumerated(EnumType.STRING)
   private UserRole role;

   public AppEntity(long id, String email, String password) {
      super();
      this.id = id;
      this.email = email;
      this.password = password;
   }
   public AppEntity(){
      super();
   }
   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      SimpleGrantedAuthority authority = new SimpleGrantedAuthority(this.role.name());
      return Collections.singletonList(authority);
   }
   @Override
   public String getUsername() {
      return this.email;
   }

   @Override
   public String getPassword(){
      return this.password;
   }

   @Override
   public boolean isAccountNonExpired() {
      return false;
   }
   @Override
   public boolean isAccountNonLocked() {
      return false;
   }
   @Override
   public boolean isCredentialsNonExpired() {
      return false;
   }
   @Override
   public boolean isEnabled() {
      return false;
   }

}

@EnableWebSecurity
@Configuration
class SecurityConfiguration {

   @Autowired
   private AppService appService;

   @Autowired
   private BCryptPasswordEncoder encoder;

   @Bean
   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      http.csrf().disable()
      .authorizeRequests().antMatchers(HttpMethod.POST, "/register").permitAll()
      .anyRequest().authenticated().and()
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and().authenticationProvider(authProvider());
      return http.build();
   }

   private DaoAuthenticationProvider authProvider(){
      DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
      provider.setUserDetailsService(this.appService);
      provider.setPasswordEncoder(this.encoder);
      return provider;
   }
}

enum UserRole {
   USER("user"),ADMIN("admin");

   protected String value;

   private UserRole(String value){
      this.value = value;
   }
}
