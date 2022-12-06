package com.example.todoapptestexample.controllers;

import com.example.todoapptestexample.models.Task;
import com.example.todoapptestexample.models.User;
import com.example.todoapptestexample.security.PersonDetails;
import com.example.todoapptestexample.services.TaskService;
import com.example.todoapptestexample.services.UserService;
import com.example.todoapptestexample.util.exceptions.TaskNotFoundErrorResponse;
import com.example.todoapptestexample.util.exceptions.TaskNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.core.ControllerEntityLinks;
import org.springframework.hateoas.server.core.ControllerEntityLinksFactoryBean;
import org.springframework.hateoas.server.mvc.ControllerLinkRelationProvider;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/todo")
public class AppController {

    private final TaskService taskService;

    @Autowired
    public AppController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping()
    public String todoPage(Model model, @ModelAttribute("task") Task task) {

        Link linkPatch = WebMvcLinkBuilder.linkTo(AppController.class).withRel("patch");
        Link linkDelete = WebMvcLinkBuilder.linkTo(AppController.class).withRel("delete");

        model.addAttribute("updateLink", linkPatch.getHref());
        model.addAttribute("deleteLink", linkDelete.getHref());

        User user = getUserFromAuthentication();

        List<Task> tasks = taskService.getAllByUserId(user.getId());
        model.addAttribute("tasks", tasks);

        return "todo-page";
    }

    @PostMapping()
    public String create(@ModelAttribute("task") @Valid Task task,
                                 BindingResult result) {
        User user = getUserFromAuthentication();

        if (result.hasErrors()) {
            return "redirect:/todo";
        }

        taskService.save(task, user);

        return "redirect:/todo";
    }

    @PatchMapping("/complete/{id}")
    public String complete(@PathVariable("id") int id, @ModelAttribute("task") Task task) {

        taskService.completeById(id, task);

        return "redirect:/todo";
    }

    @GetMapping("/{id}")
    public String edit(Model model, @PathVariable("id") int id) {

        Link linkPatch = WebMvcLinkBuilder.linkTo(AppController.class).slash(id).withRel("patch");

        User user = getUserFromAuthentication();
        Task task = taskService.getById(id);

        if (user.getId() != taskService.getById(id).getUser().getId()) {
            return "redirect:/todo";
        }

        model.addAttribute("task", task);
        model.addAttribute("updateLink", linkPatch.getHref());

        return "todo-edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("task") @Valid Task task,
                         BindingResult result, @PathVariable("id") int id) {

        if (result.hasErrors()) {
            return "redirect:/todo/{id}";
        }

        taskService.update(id, task);

        return "redirect:/todo";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        taskService.delete(id);
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
