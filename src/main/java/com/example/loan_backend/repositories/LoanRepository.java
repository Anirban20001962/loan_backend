package com.example.loan_backend.repositories;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.loan_backend.models.Loan;
import com.example.loan_backend.models.User;
import org.springframework.data.repository.CrudRepository;

public interface LoanRepository extends CrudRepository<Loan, UUID> {

    //@Query(value = "SELECT * FROM loan WHERE user_id = ?1", nativeQuery = true)
    public List<Loan> findLoansByUser(User user);

    public List<Loan> findLoansByStatus(String status);


    Optional<Loan> findById(UUID id);
}
