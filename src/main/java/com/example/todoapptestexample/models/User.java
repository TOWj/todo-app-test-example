package com.example.todoapptestexample.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
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
    @Size(min = 3, max = 50, message = "Длина логина должна быть от 3 до 50 символов!")
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", createdAt=" + createdAt +
                ", role='" + role + '\'' +
                '}';
    }
}
