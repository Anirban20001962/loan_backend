package com.example.loan_backend.models;

import com.example.loan_backend.LoanStatus;
import com.example.loan_backend.request.LoanRequest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.Type;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Loan implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;
    @Column(nullable = false)
    private String status;
    @Column(nullable = false)
    private double amount;
    @Column(nullable = false)
    private String purpose;
    @Column(nullable = false)
    private int emimonths;
    @Column(nullable = false)
    private double interest;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public void setStatus(LoanStatus status) {
        this.status = String.valueOf(status);
    }

    public Loan(LoanRequest lr) {
        this.amount = lr.amount;
        this.emimonths = lr.emimonths;
        this.interest = lr.interest;
        this.purpose = lr.purpose;
    }
}
