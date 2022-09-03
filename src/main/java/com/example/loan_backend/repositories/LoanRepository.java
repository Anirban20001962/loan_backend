package com.example.loan_backend.repositories;

import com.example.loan_backend.models.Loan;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface LoanRepository extends CrudRepository<Loan, UUID> {
    public Iterable<Loan> findAllByStatus(String s);

}
