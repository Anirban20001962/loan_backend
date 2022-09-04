package com.example.loan_backend.models;

import com.example.loan_backend.AccountRoles;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
  @Column(nullable = false)
  private String password;
  @Column(nullable = false)
  private String role;
  @Column(nullable = false, unique = true)
  private String email;
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
  private List<Loan> loans;

  @JsonIgnoreProperties("user")
  public List<Loan> getLoans() {
    return this.loans;
  }

  public void setLoans(List<Loan> loans) {
    this.loans = loans;
  }

  public UUID getId() {
    return this.id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getFirstname() {
    return this.firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return this.lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRole() {
    return this.role;
  }

  public void setRole(AccountRoles role) {
    this.role = String.valueOf(role);
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
