package com.example.loan_backend.repositories;

import com.example.loan_backend.models.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, UUID> {
  public Optional<User> findByEmailIgnoreCase(String email);
  
  @Query("SELECT u FROM User u WHERE email=?1 AND role=?2")
  public List<User> getUsersByEmail(String email, String role);
  
  @Query("SELECT j FROM User j WHERE firstname=?1 AND role=?2")
  public List<User> getUsersByFirstName(String firstname, String role);
//  
  @Query("SELECT j FROM User j WHERE lastname=?1 AND role=?2")
  public List<User> getUsersByLastName(String lastname,String role);

  public boolean existsByEmailIgnoreCase(String email);

  public Iterable<User> findAllByFirstnameStartingWithIgnoreCase(String pattern);

  public Iterable<User> findAllByLastnameStartingWithIgnoreCase(String pattern);

  public Iterable<User> findAllByEmailStartingWithIgnoreCase(String pattern);
}
