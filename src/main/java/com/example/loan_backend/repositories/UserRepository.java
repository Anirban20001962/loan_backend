package com.example.loan_backend.repositories;

import com.example.loan_backend.models.User;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, UUID> {
  public Optional<User> findByEmailIgnoreCase(String email);

  public Iterable<User> findAllByRole(String role);

  public boolean existsByEmailIgnoreCase(String email);

  public Iterable<User> findAllByFirstnameStartingWithIgnoreCaseAndRole(String pattern, String roles);

  public Iterable<User> findAllByLastnameStartingWithIgnoreCaseAndRole(String pattern, String roles);

  public Iterable<User> findAllByEmailStartingWithIgnoreCaseAndRole(String pattern, String roles);
}
