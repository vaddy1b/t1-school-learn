package ru.babich.t1schoollearn.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.babich.t1schoollearn.T1SchoolLearnApplication;
import ru.babich.t1schoollearn.annottaion.TrackTrace;
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

    private static final Logger logger = LoggerFactory.getLogger(T1SchoolLearnApplication.class);

    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }


    public Optional<TaskDTO> getTaskById(Long id) {
        return Optional.ofNullable(taskRepository.findById(id)
                .map(taskMapper::toDto)
                .orElseThrow(() -> {
                    logger.warn("No task found with id: {}", id);
                    return new NullPointerException("Task not found with id: " + id);
                }));
    }

    public TaskDTO createTask(TaskDTO taskDto) {
        var task = taskMapper.toEntity(taskDto);
        var savedTask = taskRepository.save(task);
        return taskMapper.toDto(savedTask);
    }

    @TrackTrace
    public TaskDTO updateTask(TaskDTO taskDto) {

        Task task = taskMapper.toEntity(taskDto);
        var updatedTask = taskRepository.save(task);
        return taskMapper.toDto(updatedTask);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}