package com.example.todoapptestexample.controllers;

import com.example.todoapptestexample.security.PersonDetails;
import com.example.todoapptestexample.services.TaskService;
import com.example.todoapptestexample.util.exceptions.TaskNotFoundErrorResponse;
import com.example.todoapptestexample.util.exceptions.TaskNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

    private final TaskService taskService;

    @Autowired
    public AppController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public String index() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!authentication.getName().equals("anonymousUser")) {
            System.out.println("Авторизованный");
        } else {
            System.out.println("Неавторизованный");
        }
//        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        return "index";
    }

    @GetMapping("/todo")
    public String todoPage(Model model) {
//        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        return "todo-page";
    }


    @ExceptionHandler
    private ResponseEntity<TaskNotFoundErrorResponse> handleException(TaskNotFoundException exception) {
        TaskNotFoundErrorResponse errorResponse = new TaskNotFoundErrorResponse(
                exception.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

}
