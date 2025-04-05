package ru.babich.t1schoollearn.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.babich.t1schoollearn.annottaion.ExceptionTrace;
import ru.babich.t1schoollearn.mapper.TaskMapper;
import ru.babich.t1schoollearn.model.Task;
import ru.babich.t1schoollearn.model.TaskDTO;
import ru.babich.t1schoollearn.repo.TaskRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    private final TaskMapper taskMapper;

    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<TaskDTO> getTaskById(Long id) {
        return taskRepository.findById(id)
                .map(taskMapper::toDto);
    }

    public TaskDTO createTask(TaskDTO taskDto) {
        var task = taskMapper.toEntity(taskDto);
        var savedTask = taskRepository.save(task);
        return taskMapper.toDto(savedTask);
    }

    @ExceptionTrace
    public TaskDTO updateTask(TaskDTO taskDto) {
        Task task = taskMapper.toEntity(taskDto);
        var updatedTask = taskRepository.save(task);
        return taskMapper.toDto(updatedTask);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}