package com.example.loan_backend.controller;

import com.example.loan_backend.AccountRoles;
import com.example.loan_backend.CustomUserDetails;
import com.example.loan_backend.models.User;
import com.example.loan_backend.repositories.UserRepository;
import com.example.loan_backend.request.LoginRequest;
import com.example.loan_backend.request.SignupRequest;
import com.example.loan_backend.response.LoginResponse;
import com.example.loan_backend.response.MsgDataResponse;
import com.example.loan_backend.services.CustomUserDetailsService;
import com.example.loan_backend.util.JwtUtil;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthController {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private CustomUserDetailsService customUserDetailsService;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private JwtUtil jwtUtil;

  @PostMapping("/signup")
  public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest sr) {

    User user = new User(sr);

    if (userRepository.existsByEmailIgnoreCase(user.getEmail()))
      throw new BadCredentialsException("Account Already exists");

    user.setRole(AccountRoles.ROLE_USER);
    user.setPassword(passwordEncoder.encode(sr.password));
    userRepository.save(user);

    return new ResponseEntity<>(new MsgDataResponse("Account Created Successfully", null), HttpStatus.CREATED);

  }

  @PostMapping("/signin")
  public ResponseEntity<?> sigin(@Valid @RequestBody LoginRequest request) {

    authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(request.email, request.password));

    CustomUserDetails user = customUserDetailsService.loadUserByUsername(request.email);

    String jwt = jwtUtil.generateToken(user);

    return new ResponseEntity<>(new LoginResponse(jwt, user.getUser()), HttpStatus.OK);

  }
}
