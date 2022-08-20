package com.security.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.security.model.entity.Role;
import com.security.model.entity.Users;
import com.security.model.repositories.Userrepository;

@Service
public class UserService implements UserDetailsService {
   @Autowired
   private Userrepository userrepository;

   @Autowired
   private BCryptPasswordEncoder bCryptPasswordEncoder;

   public Users save(Users entity) {
      entity.setRole(Role.USER);
      entity.setPassword(bCryptPasswordEncoder.encode(entity.getPassword()));
      return userrepository.save(entity);
   }

   public Iterable<Users> findAllUsers() {
      return userrepository.findAll();
   }

   @Override
   public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
      // String passw = bCryptPasswordEncoder.encode("foo");
      System.out.println(userrepository.findByEmail(email).get().getPassword());
      // return new User("help", passw, new ArrayList<>());
      return userrepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("unkown " + email));
   }
}
