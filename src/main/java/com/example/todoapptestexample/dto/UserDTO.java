package com.example.todoapptestexample.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserDTO {

    @NotEmpty(message = "Поле имени не должно быть пустым!")
    @Size(min = 3, max = 50, message = "Длина логина должна быть от 3 до 50 символов!")
    private String login;

    @NotEmpty(message = "Поле пароля не должно быть пустым!")
    @Size(min = 4, message = "Длина пароля должна быть не меньше 4 символов!")
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
