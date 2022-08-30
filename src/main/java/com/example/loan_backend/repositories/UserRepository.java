package com.example.loan_backend.repositories;

import com.example.loan_backend.models.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, UUID> {
  public Optional<User> findByEmail(String email);

  public boolean existsByEmail(String email);

  @Query("select u from User u where u.firstname = ?1")
  List<User> findByFirstname(String firstname);

  @Query("select u from User u where u.lastname = ?1")
  List<User> findByLastnamme(String lastname);
  
}
