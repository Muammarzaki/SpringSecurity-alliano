package com.security.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.security.model.dao.UsersDao;
import com.security.model.entity.Role;
import com.security.model.entity.Users;

@Service
public class UserService implements UserDetailsService {
   
   private @Autowired UsersDao usersDao;

   @Autowired
   private BCryptPasswordEncoder bCryptPasswordEncoder;

   public Users save(Users entity){
      entity.setRole(Role.USER);
      entity.setPassword(bCryptPasswordEncoder.encode(entity.getPassword()));
      return usersDao.save(entity);
   }

   public Iterable<Users> findAllUsers(){
      return usersDao.findAll();
   }

   @Override
   public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
      System.out.println(usersDao.findByEmail(email).get().getPassword());
      return usersDao.findByEmail(email).orElseThrow( () -> new UsernameNotFoundException("unkown "+ email));
   }
}
