package com.example.loan_backend.repositories;

import com.example.loan_backend.models.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, UUID> {
  public Optional<User> findByEmail(String email);

  public boolean existsByEmail(String email);
  public boolean existsById(UUID id);

}
