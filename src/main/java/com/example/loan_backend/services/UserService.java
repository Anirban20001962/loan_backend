package com.example.loan_backend.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.loan_backend.models.User;
import com.example.loan_backend.repositories.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getUserByFirstName(String pattern) {
        List<User> allUsers = new ArrayList<>();

        userRepository.findAllByFirstnameStartingWithIgnoreCase(pattern).forEach(allUsers::add);
        return allUsers;
    }

    public List<User> getUserByLastName(String pattern) {
        List<User> allUsers = new ArrayList<>();

        userRepository.findAllByLastnameStartingWithIgnoreCase(pattern).forEach(allUsers::add);
        return allUsers;
    }

    public List<User> getUserByEmail(String pattern) {
        List<User> allUsers = new ArrayList<>();

        userRepository.findAllByEmailStartingWithIgnoreCase(pattern).forEach(allUsers::add);
        return allUsers;
    }
}
