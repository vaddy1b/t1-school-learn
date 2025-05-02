package ru.babich.t1schoollearn.service;

import org.apache.kafka.clients.producer.Producer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.babich.t1schoollearn.exception.TaskNotFoundException;
import ru.babich.t1schoollearn.mapper.TaskMapper;
import ru.babich.t1schoollearn.model.Task;
import ru.babich.t1schoollearn.model.TaskDTO;
import ru.babich.t1schoollearn.repo.TaskRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskService taskService;

    private Task task;
    private Task task2;
    private TaskDTO taskDto;
    private TaskDTO taskDto2;

    @BeforeEach
    void setUpBase() {

        Long taskId = 1L;
        task = new Task(taskId, "Task 1", "Description 1", 2L, "DONE");
        task2 = new Task(taskId + 1, "Task 2", "Description 2", 3L, "DONE");
        taskDto = new TaskDTO(taskId, "Task 1", "Description 1", 2L, "DONE");
        taskDto2 = new TaskDTO(taskId + 1, "Task 2", "Description 2", 3L, "DONE");
    }

    @Test
    void getAllTasks_ShouldReturnListOfTasks() {
        when(taskRepository.findAll()).thenReturn(List.of(task, task2));
        when(taskMapper.toDto(task)).thenReturn(taskDto);
        when(taskMapper.toDto(task2)).thenReturn(taskDto2);

        List<TaskDTO> result = taskService.getAllTasks();

        assertEquals(2, result.size());
        assertEquals("Task 1", result.get(0).getTitle());
        assertEquals("DONE", result.get(1).getStatus());
    }

    @Test
    void getTaskById_ShouldReturnTask_WhenTaskExists() {

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskMapper.toDto(task)).thenReturn(taskDto);

        TaskDTO result = taskService.getTaskById(1L)
                .orElseThrow(() -> new AssertionError("Task not found"));

        // Then
        assertEquals(1L, result.getId());
        assertEquals("Task 1", result.getTitle());
        assertEquals("DONE", result.getStatus());
    }

    @Test
    void getTaskById_ShouldThrowException_WhenTaskNotFound() {

        Long taskId = 999L;

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.getTaskById(taskId));
    }

    @Test
    void createTask_ShouldSaveAndReturnTask() {

        TaskDTO inputDto = new TaskDTO(null, "New Task", "New Desc", null, "PENDING");
        Task savedTask = new Task(3L, "New Task", "New Desc", null, "PENDING");
        TaskDTO expectedDto = new TaskDTO(3L, "New Task", "New Desc", null, "PENDING");

        when(taskMapper.toEntity(inputDto)).thenReturn(savedTask);
        when(taskRepository.save(savedTask)).thenReturn(savedTask);
        when(taskMapper.toDto(savedTask)).thenReturn(expectedDto);

        TaskDTO result = taskService.createTask(inputDto);

        assertEquals(3L, result.getId());
        assertEquals("New Task", result.getTitle());
        assertEquals("PENDING", result.getStatus());
    }

    @Test
    void updateTask_ShouldUpdateAndReturnTask() {

        Long taskId = 1L;
        TaskDTO inputDto = new TaskDTO(taskId, "Updated Task", "Updated Desc", 2L, "DONE");
        Task task = new Task(taskId, "Updated Task", "Updated Desc", 2L, "DONE");
        TaskDTO expectedDto = new TaskDTO(taskId, "Updated Task", "Updated Desc", 2L, "DONE");

        when(taskMapper.toEntity(inputDto)).thenReturn(task);
        when(taskRepository.save(task)).thenReturn(task);
        when(taskMapper.toDto(task)).thenReturn(expectedDto);


        TaskDTO result = taskService.updateTask(inputDto);

        assertEquals(taskId, result.getId());
        assertEquals("DONE", result.getStatus());
    }

    @Test
    void deleteTask_ShouldDeleteTask_WhenTaskExists() {

        Long taskId = 1L;
        when(taskRepository.existsById(taskId)).thenReturn(true);

        taskService.deleteTask(taskId);

        verify(taskRepository, times(1)).deleteById(taskId);
    }

    @Test
    void deleteTask_ShouldThrowException_WhenTaskNotFound() {

        Long taskId = 999L;
        when(taskRepository.existsById(taskId)).thenReturn(false);

        assertThrows(TaskNotFoundException.class, () -> taskService.deleteTask(taskId));
    }
}