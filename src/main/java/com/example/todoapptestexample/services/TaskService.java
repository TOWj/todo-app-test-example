package com.example.todoapptestexample.services;

import com.example.todoapptestexample.models.Task;
import com.example.todoapptestexample.models.User;
import com.example.todoapptestexample.repositories.TaskRepository;
import com.example.todoapptestexample.util.exceptions.TaskNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class TaskService {

    private TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllByUserId(int id) {
        User user = new User();
        user.setId(id);
        return taskRepository.findAllByUser(user);
    }

    public Task getById(int id) throws TaskNotFoundException {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isEmpty()) {
            throw new TaskNotFoundException("Task not found!");
        }
        return task.get();
    }

    @Transactional
    public void save(Task task) {
        task.setCreatedAt(new Timestamp(new Date().getTime()));
        task.setUpdatedAt(new Timestamp(new Date().getTime()));
        taskRepository.save(task);
    }

    @Transactional
    public void update(int id, Task task) throws TaskNotFoundException {
        Optional<Task> taskToUpdate = taskRepository.findById(id);
        if (taskToUpdate.isEmpty()) {
            throw new TaskNotFoundException("Task not found!");
        }
        task.setId(id);
        task.setUser(taskToUpdate.get().getUser());

        taskRepository.save(task);
    }

    @Transactional
    public void delete(int id) {
        taskRepository.deleteById(id);
    }
}
