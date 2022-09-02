package com.example.loan_backend.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.loan_backend.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.loan_backend.models.Loan;
import com.example.loan_backend.repositories.LoanRepository;

import javax.transaction.Transactional;

@Service
@Transactional
public class LoanService {

    @Autowired
    private LoanRepository loanRepo;

    public void addLoan(Loan loan) {
        loan.setStatus("PENDING");

        loanRepo.save(loan);
    }

    public List<Loan> getLoansByUser(UUID id) {

        System.out.println(loanRepo.findLoansByUser(new User(id)));

		/*List<Loan> result = new ArrayList<Loan>();

		loanRepo.findAll().forEach(result::add);

		List<Loan> filter = new ArrayList<Loan>();

		int i;

		for(i=0;i<result.size();i++)
		{
			if(result.get(i).getUser().getId().equals(id))
			{
				filter.add(result.get(i));
			}
		}*/

        return loanRepo.findLoansByUser(new User(id));
    }




    public List<Loan> getLoansByStatus(String status) {


        return loanRepo.findLoansByStatus(status);
    }


    public List<Loan> getAllLoans() {
        List<Loan> result = new ArrayList<Loan>();

        loanRepo.findAll().forEach(result::add);

        return result;
    }

}