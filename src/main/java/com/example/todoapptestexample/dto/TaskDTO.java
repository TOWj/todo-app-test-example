package com.example.todoapptestexample.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class TaskDTO {

    @NotBlank(message = "Поле задачи не должно быть пустым!")
    @Size(max = 60, message = "Длина задачи должна быть не больше 60 символов!")
    private String body;

    private boolean completed;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
