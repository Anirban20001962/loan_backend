package com.example.loan_backend.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.loan_backend.AccountRoles;
import com.example.loan_backend.models.User;
import com.example.loan_backend.repositories.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getUsersByFirstName(String pattern) {
        List<User> allUsers = new ArrayList<>();

        userRepository.findAllByFirstnameStartingWithIgnoreCaseAndRole(pattern, String.valueOf(AccountRoles.ROLE_USER))
                .forEach(allUsers::add);
        return allUsers;
    }

    public List<User> getUsersByLastName(String pattern) {
        List<User> allUsers = new ArrayList<>();

        userRepository.findAllByLastnameStartingWithIgnoreCaseAndRole(pattern, String.valueOf(AccountRoles.ROLE_USER))
                .forEach(allUsers::add);
        return allUsers;
    }

    public List<User> getUsersByEmail(String pattern) {
        List<User> allUsers = new ArrayList<>();

        userRepository.findAllByEmailStartingWithIgnoreCaseAndRole(pattern, String.valueOf(AccountRoles.ROLE_USER))
                .forEach(allUsers::add);
        return allUsers;
    }

    public Optional<User> getUniqueUserByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email);
    }

    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        userRepository.findAllByRole(String.valueOf(AccountRoles.ROLE_USER)).forEach(allUsers::add);
        return allUsers;
    }
}
