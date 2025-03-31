package ru.babich.t1schoollearn.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import ru.babich.t1schoollearn.model.Task;
import ru.babich.t1schoollearn.repo.TaskRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task taskDetails) {
        try {
            Task task = taskRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Task not found with id: " + id));
            task.setTitle(taskDetails.getTitle());
            task.setDescription(taskDetails.getDescription());
            task.setUserId(taskDetails.getUserId());
            return taskRepository.save(task);
        } catch (NoSuchElementException ex) {
            ex.getMessage();
        }
        return null;
    }

    public void deleteTask(Long id) {
        try {
            taskRepository.deleteById(id);
        } catch (NoSuchElementException ex) {
            ex.getMessage();
        }
    }
}
