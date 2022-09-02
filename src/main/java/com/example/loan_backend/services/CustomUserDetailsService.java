package com.example.loan_backend.services;

import com.example.loan_backend.CustomUserDetails;
import com.example.loan_backend.models.User;
import com.example.loan_backend.repositories.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

    Optional<User> user = userRepository.findByEmailIgnoreCase(email);

    user.orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

    return user.map(CustomUserDetails::new).get();
  }
}
