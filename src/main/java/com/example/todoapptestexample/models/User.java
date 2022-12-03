package com.example.todoapptestexample.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "login")
    @NotEmpty(message = "Поле имени не должно быть пустым!")
    @Size(max = 50, message = "Имя не должно быть длиннее 50 символов!")
    private String login;

    @Column(name = "password")
    @NotEmpty(message = "Поле пароля не должно быть пустым!")
    @Size(min = 4, max = 20, message = "Длина пароля должна быть от 4 до 20 символов!")
    private String password;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "role")
    private String role;

    @OneToMany(mappedBy = "user")
    private List<Task> taskList;

    public User() {
    }

    public User(String login, Date createdAt) {
        this.login = login;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }
}
