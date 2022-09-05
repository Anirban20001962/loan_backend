package com.example.loan_backend.models;

import com.example.loan_backend.AccountRoles;
import com.example.loan_backend.request.SignupRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.Type;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(columnDefinition = "VARCHAR(36)")
  @Type(type = "uuid-char")
  private UUID id;
  @Column(nullable = false)
  private String firstname;
  @Column(nullable = false)
  private String lastname;
  @JsonProperty(access = Access.WRITE_ONLY)
  @Column(nullable = false)
  private String password;
  @JsonIgnore
  @Column(nullable = false)
  private String role;
  @Column(nullable = false, unique = true)
  private String email;
  @JsonIgnore
  @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
  private List<Loan> loans;

  public void setRole(AccountRoles role) {
    this.role = String.valueOf(role);
  }

  public User(SignupRequest sr) {
    this.firstname = sr.firstname;
    this.lastname = sr.lastname;
    this.email = sr.email;
  }
}
