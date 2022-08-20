package com.security.model.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.security.model.entity.Users;

@Repository
public interface Userrepository extends CrudRepository<Users,Long>{
   public Optional<Users> findByEmail(String email);
}
