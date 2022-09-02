package com.example.loan_backend.repositories;

import com.example.loan_backend.models.User;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, UUID> {
  public Optional<User> findByEmailIgnoreCase(String email);

  public boolean existsByEmailIgnoreCase(String email);

  public Iterable<User> findAllByFirstnameStartingWithIgnoreCase(String pattern);

  public Iterable<User> findAllByLastnameStartingWithIgnoreCase(String pattern);

  public Iterable<User> findAllByEmailStartingWithIgnoreCase(String pattern);
}
