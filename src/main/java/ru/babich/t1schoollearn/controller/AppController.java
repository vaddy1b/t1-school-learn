package ru.babich.t1schoollearn.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.babich.t1schoollearn.exception.ResourceNotFoundException;
import ru.babich.t1schoollearn.model.TaskDTO;
import ru.babich.t1schoollearn.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class AppController {

    private final TaskService taskService;

    public AppController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<TaskDTO> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public TaskDTO getTaskById(@PathVariable Long id) throws ResourceNotFoundException {
        return taskService.getTaskById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDTO createTask(@RequestBody TaskDTO taskDto) {
        return taskService.createTask(taskDto);
    }

    @PutMapping("/{id}")
    public TaskDTO updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDto) {

        if (!id.equals(taskDto.getId())) {
            throw new IllegalArgumentException("ID in path and body must match");
        }

        taskDto.setId(id);
        return taskService.updateTask(taskDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

}