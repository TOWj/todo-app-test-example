package com.example.todoapptestexample.services;

import com.example.todoapptestexample.models.User;
import com.example.todoapptestexample.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;

@Service
@Transactional(readOnly = true)
public class RegistrationService {

    private final UsersRepository usersRepository;

    @Autowired
    public RegistrationService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Transactional
    public void register(User user) {
        user.setRole("USER");
        user.setCreatedAt(new Timestamp(new Date().getTime()));
        usersRepository.save(user);
    }
}
