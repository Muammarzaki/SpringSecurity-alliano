package com.security.model.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.security.model.entity.Users;

@Repository
public interface UsersDao extends CrudRepository<Users,Long>{
   public Optional<Users> findByEmail(String email);
}
