package com.example.todoapptestexample.services;

import com.example.todoapptestexample.models.User;
import com.example.todoapptestexample.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UsersRepository usersRepository;

    @Autowired
    public UserService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public Optional<User> loadUserByUsernameForValidation(String username) {
        return usersRepository.findByLogin(username);
    }
}
