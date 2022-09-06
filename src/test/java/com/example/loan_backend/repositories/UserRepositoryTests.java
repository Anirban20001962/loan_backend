package com.example.loan_backend.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.loan_backend.AccountRoles;
import com.example.loan_backend.models.User;

// Integration Testing for Loan Repository
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;

    private ArrayList<User> users = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        User user1 = new User();
        user1.setRole(AccountRoles.ROLE_USER);
        user1.setFirstname("loan1");
        user1.setLastname("service");
        user1.setPassword("123456");
        user1.setEmail("userRepo1@test.com");

        User user2 = new User();
        user2.setRole(AccountRoles.ROLE_USER);
        user2.setFirstname("loan2");
        user2.setLastname("service");
        user2.setPassword("123456");
        user2.setEmail("userRepo2@test.com");

        user1 = userRepository.save(user1);
        user2 = userRepository.save(user2);

        users.add(user1);
        users.add(user2);

    }

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
        users = new ArrayList<>();
    }

    @Test
    public void findByEmailIgnoreCaseTest() {
        Optional<User> found = userRepository.findByEmailIgnoreCase(users.get(0).getEmail());

        assertTrue(found.isPresent());
    }

    @Test
    public void existsByEmailIgnoreCaseTest() {
        boolean found = userRepository.existsByEmailIgnoreCase(users.get(0).getEmail());

        assertTrue(found);
    }

    @Test
    public void findAllByFirstnameStartingWithIgnoreCaseTest() {
        List<User> found = (List<User>) userRepository.findAllByFirstnameStartingWithIgnoreCaseAndRole("loan",
                String.valueOf(AccountRoles.ROLE_USER));

        assertEquals(2, found.size());
    }

    @Test
    public void findAllByEmailStartingWithIgnoreCaseTest() {
        List<User> found = (List<User>) userRepository.findAllByEmailStartingWithIgnoreCaseAndRole("user",
                String.valueOf(AccountRoles.ROLE_USER));

        assertEquals(2, found.size());
    }

    @Test
    public void findAllByLastnameStartingWithIgnoreCaseTest() {
        List<User> found = (List<User>) userRepository.findAllByLastnameStartingWithIgnoreCaseAndRole("serv",
                String.valueOf(AccountRoles.ROLE_USER));

        assertEquals(2, found.size());
    }
}
