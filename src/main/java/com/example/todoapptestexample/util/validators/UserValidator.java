package com.example.todoapptestexample.util.validators;

import com.example.todoapptestexample.models.User;
import com.example.todoapptestexample.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class UserValidator implements Validator {

    private final UserService userService;

    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        Optional<User> userForCreation = userService.loadUserByUsernameForValidation(user.getLogin());

        if (userForCreation.isPresent()) {
            errors.rejectValue("login", "", "Юзер с таким логином уже существует!");
        }
    }
}
