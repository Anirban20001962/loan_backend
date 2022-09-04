package com.example.loan_backend.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import com.example.loan_backend.models.User;
import com.example.loan_backend.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Autowired
    @InjectMocks
    private UserService userService;

    private User user1;
    private User user2;

    private List<User> users = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        user1 = new User();
        user1.setFirstname("user1");
        user1.setLastname("service1");
        user1.setEmail("userService@test.com");

        user2 = new User();
        user2.setLastname("user2");
        user2.setLastname("service2");
        user1.setEmail("userService2@test.com");

        users.add(user1);
        users.add(user2);
    }

    @AfterEach
    public void tearDown() {
        user1 = null;
        user2 = null;

        users = null;
    }

    @Test
    public void getUserByFirstNameTest() {

        when(userRepository.findAllByFirstnameStartingWithIgnoreCase("use")).thenReturn(users);

        List<User> found = userService.getUserByFirstName("use");

        assertEquals(2, found.size());

    }

    @Test
    public void getUserByLastNameTest() {

        when(userRepository.findAllByLastnameStartingWithIgnoreCase("use")).thenReturn(users);

        List<User> found = userService.getUserByLastName("use");

        assertEquals(2, found.size());

    }

    @Test
    public void getUserByEmailTest() {

        when(userRepository.findAllByEmailStartingWithIgnoreCase("use")).thenReturn(users);

        List<User> found = userService.getUserByEmail("use");

        assertEquals(2, found.size());

    }

    @Test
    public void getUniqueUserByEmailTest() {

        when(userRepository.findByEmailIgnoreCase(user1.getEmail())).thenReturn(Optional.of(user1));

        Optional<User> found = userService.getUniqueUserByEmail(user1.getEmail());

        assertTrue(found.isPresent());

    }

    @Test
    public void getAllUsersTest() {

        when(userRepository.findAll()).thenReturn(users);

        List<User> found = userService.getAllUsers();

        assertEquals(2, found.size());

    }

}
