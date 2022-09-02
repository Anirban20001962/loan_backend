package com.example.loan_backend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.example.loan_backend.models.Loan;
import com.example.loan_backend.models.User;
import com.example.loan_backend.services.LoanService;
import com.example.loan_backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private LoanService loanService;


	@RequestMapping(value="/users",method=RequestMethod.GET) 
	@CrossOrigin(origins = "http://localhost:3000")
	public List<User> getUsers(){
	  	return userService.getUsers();
	}
	
	
	@RequestMapping(value="/getAllLoans",method=RequestMethod.GET)
	@CrossOrigin(origins = "http://localhost:3000")
	public List<Loan> getAllLoans(){
		return loanService.getAllLoans();
	}
	
	
	@RequestMapping(value="/getLoansByUser/{user_id}",method=RequestMethod.GET)
	@CrossOrigin(origins = "http://localhost:3000")
	public List<Loan> getLoansByUser(@PathVariable UUID user_id){
		if(userService.verifyUser(user_id))
		{
			return loanService.getLoansByUser(user_id);
		}
		else
		{
			return new ArrayList<Loan>();
		}
	}

	@RequestMapping(value="/acceptLoan/{loan_id}", method = RequestMethod.POST)
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<Object> acceptLoan(@PathVariable UUID loan_id){
		loanService.acceptLoan(loan_id);
		return new ResponseEntity<>(true,HttpStatus.OK);
	}

	@RequestMapping(value="/rejectLoan/{loan_id}", method = RequestMethod.POST)
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<Object> rejectLoan(@PathVariable UUID loan_id){
		loanService.rejectLoan(loan_id);
		return new ResponseEntity<>(true,HttpStatus.OK);
	}

	@RequestMapping(value="/getPendingLoans",method=RequestMethod.GET)
	@CrossOrigin(origins = "http://localhost:3000")
	public List<Loan> getPendingLoans(){
		return loanService.getPendingLoans();
	}
}
