package com.security.model.entity;


import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity @Setter @Getter
@Table(name = "users")
public class Users implements UserDetails {
   
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long id;

   @Column(nullable = false, unique = true)
   private String email;

   @Column(nullable = false)
   private String password;

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      return null;
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
