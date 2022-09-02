package com.example.loan_backend.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import com.example.loan_backend.models.User;
import com.example.loan_backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;



@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepo;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostConstruct
    public void addAdmin() { // IF THE USER ADMIN DOESN'T EXISTS IN THE DATABASE THEN IT WOULD BE AUTOMATICALLY ADDED
        int flag=0;

        for(User i:userRepo.findAll())
        {
            if((i.getEmail().equals("admin@admin")))
            {
                flag=1;
                break;
            }
        }

        if(flag==0)
        {
            User user=new User();

            user.setFirstname("admin");
            user.setLastname("");
            user.setEmail("admin@admin");
            user.setRole("ROLE_ADMIN");
            user.setPassword(passwordEncoder.encode("admin"));

            userRepo.save(user);

        }
    }

    public boolean saveUser(User user) {
        for(User i:userRepo.findAll())
        {
            if((i.getEmail().equals(user.getEmail())) || (user.getEmail().equals("admin@admin")))
            {
                return false;
            }
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if(user.getRole()==null)
        {
            user.setRole("ROLE_USER");
        }


        userRepo.save(user);

        return true;

    }


    public List<User> getUsers() {
        List<User> users=new ArrayList<>();

        for(User i:userRepo.findAll())
        {
            users.add(i);
        }

        return users;

    }

    public boolean verifyUser(UUID id) {
        return userRepo.existsById(id);

    }

}