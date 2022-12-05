package com.example.todoapptestexample.controllers;

import com.example.todoapptestexample.models.Task;
import com.example.todoapptestexample.models.User;
import com.example.todoapptestexample.security.PersonDetails;
import com.example.todoapptestexample.services.TaskService;
import com.example.todoapptestexample.services.UserService;
import com.example.todoapptestexample.util.exceptions.TaskNotFoundErrorResponse;
import com.example.todoapptestexample.util.exceptions.TaskNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class AppController {

    private final TaskService taskService;
    private final UserService userService;

    @Autowired
    public AppController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String index(Model model) {

        if (SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
                !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            User user = getUserFromAuthentication();
            model.addAttribute("user", user);
            return "redirect:/todo";
        }

        return "index";
    }

    @GetMapping("/todo")
    public String todoPage(Model model, @ModelAttribute("task") Task task) {

        User user = getUserFromAuthentication();
        model.addAttribute("user", user);

        List<Task> tasks = taskService.getAllByUserId(user.getId());
        model.addAttribute("tasks", tasks);

        return "todo-page";
    }

    @PostMapping("/todo")
    public String createTodoPost(@ModelAttribute("task") @Valid Task task,
                                 BindingResult result, Model model) {
        User user = getUserFromAuthentication();
        model.addAttribute("user", user);
        if (result.hasErrors()) {
            return "redirect:/todo";//Если есть ошибки, не сохраняем в базу
        }
        task.setUser(user);
        taskService.save(task);

        return "redirect:/todo";
    }


    @ExceptionHandler
    private ResponseEntity<TaskNotFoundErrorResponse> handleException(TaskNotFoundException exception) {
        TaskNotFoundErrorResponse errorResponse = new TaskNotFoundErrorResponse(
                exception.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    private User getUserFromAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        return personDetails.getUser();
    }

}
