package com.example.todoapptestexample.repositories;

import com.example.todoapptestexample.models.Task;
import com.example.todoapptestexample.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findAllByUser(User user);
    List<Task> findAllByUserOrderByCreatedAt(User user);
}
