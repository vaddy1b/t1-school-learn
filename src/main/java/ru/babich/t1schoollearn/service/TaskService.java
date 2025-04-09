package ru.babich.t1schoollearn.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.babich.t1schoollearn.annottaion.TrackTrace;
import ru.babich.t1schoollearn.exception.TaskNotFoundException;
import ru.babich.t1schoollearn.kafka.producer.TaskProducer;
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

    private final TaskProducer producer;

    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

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
                    return new TaskNotFoundException("No such task found with id: { " + id + "}");
                }));
    }

    @Transactional
    public TaskDTO createTask(@RequestBody TaskDTO taskDto) {

        if (taskDto == null) {
            throw new IllegalArgumentException("TaskDTO cannot be null");
        }

        var task = taskMapper.toEntity(taskDto);
        var savedTask = taskRepository.save(task);
        return taskMapper.toDto(savedTask);
    }

    @TrackTrace
    @Transactional
    public TaskDTO updateTask(TaskDTO taskDto) {

        if (taskDto == null || !taskRepository.existsById(taskDto.getId())) {
            throw new IllegalArgumentException("TaskDTO cannot be null or not exist");
        }

        Task task = taskMapper.toEntity(taskDto);
        var updatedTask = taskRepository.save(task);

        if (taskDto.getStatus() != null) {
            try {
                producer.sendTaskUpdate(task.getId(), taskDto.getStatus());
            } catch (Exception e) {
                logger.error("Failed to send task update to Kafka", e);
            }
        }

        return taskMapper.toDto(updatedTask);
    }

    @Transactional
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
    }
}