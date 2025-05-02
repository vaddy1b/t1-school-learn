package ru.babich.t1schoollearn.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.babich.t1schoollearn.exception.ResourceNotFoundException;
import ru.babich.t1schoollearn.exception.TaskNotFoundException;
import ru.babich.t1schoollearn.model.TaskDTO;
import ru.babich.t1schoollearn.service.TaskService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private AppController appController;

    @Test
    @DisplayName("Тест получения всех задач")
    void getAllTasks_ShouldReturnListOfTasks() {
        // Given
        TaskDTO task1 = new TaskDTO(1L, "Task 1", "Description 1", 1L, "DONE");
        TaskDTO task2 = new TaskDTO(2L, "Task 2", "Description 2", 2L, "PENDING");
        when(taskService.getAllTasks()).thenReturn(List.of(task1, task2));

        // When
        List<TaskDTO> result = appController.getAllTasks();

        // Then
        assertEquals(2, result.size());
        assertEquals("Task 1", result.get(0).getTitle());
    }

    @Test
    @DisplayName("Тест получения задачи по ID - успешный случай")
    void getTaskById_ShouldReturnTask_WhenTaskExists() throws ResourceNotFoundException {
        Long taskId = 1L;
        TaskDTO task = new TaskDTO(taskId, "Task 1", "Description 1", 1L, "DONE");
        when(taskService.getTaskById(taskId)).thenReturn(Optional.of(task));

        TaskDTO result = appController.getTaskById(taskId);

        assertEquals(taskId, result.getId());
        assertEquals("Task 1", result.getTitle());
    }

    @Test
    @DisplayName("Тест получения задачи по ID - задача не найдена")
    void getTaskById_ShouldReturnNotFound_WhenTaskNotExists() {

        Long taskId = 999L;
        when(taskService.getTaskById(taskId)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> appController.getTaskById(taskId));

    }

    @Test
    @DisplayName("Тест создания новой задачи")
    void createTask_ShouldReturnCreatedTask() {

        TaskDTO inputDto = new TaskDTO(null, "New Task", "New Desc", 5L, "PENDING");
        TaskDTO savedDto = new TaskDTO(1L, "New Task", "New Desc", 5L, "PENDING");

        when(taskService.createTask(inputDto)).thenReturn(savedDto);

        TaskDTO result = appController.createTask(inputDto);

        // Then
        assertEquals(1L, result.getId());
        assertEquals("New Task", result.getTitle());
    }

    @Test
    @DisplayName("Тест обновления задачи - успешный случай")
    void updateTask_ShouldReturnUpdatedTask() {

        Long taskId = 1L;
        TaskDTO inputDto = new TaskDTO(taskId, "Updated Task", "Updated Desc", 1L, "DONE");
        TaskDTO updatedDto = new TaskDTO(taskId, "Updated Task", "Updated Desc", 1L, "DONE");
        when(taskService.updateTask(inputDto)).thenReturn(updatedDto);


        TaskDTO result = appController.updateTask(taskId, inputDto);

        assertEquals(taskId, result.getId());
        assertEquals("DONE", result.getStatus());
    }

    @Test
    @DisplayName("Тест обновления задачи - задача не найдена")
    void updateTask_ShouldReturnNotFound_WhenTaskNotFound() {

        Long taskId = 999L;
        TaskDTO inputDto = new TaskDTO(taskId, "Task", "Desc", 1L, "DONE");
        when(taskService.updateTask(inputDto)).thenThrow(new TaskNotFoundException("Task not found"));

        assertThrows(TaskNotFoundException.class, () -> appController.updateTask(taskId, inputDto));
    }

    @Test
    @DisplayName("Тест удаления задачи - успешный случай")
    void deleteTask_ShouldReturnNoContent_WhenTaskExists() {

        Long taskId = 1L;
        doNothing().when(taskService).deleteTask(taskId);

        assertDoesNotThrow(() -> appController.deleteTask(taskId));
        verify(taskService, times(1)).deleteTask(taskId);
    }

    @Test
    @DisplayName("Тест удаления задачи - задача не найдена")
    void deleteTask_ShouldReturnNotFound_WhenTaskNotFound() {

        Long taskId = 999L;
        doThrow(new TaskNotFoundException("Task not found")).when(taskService).deleteTask(taskId);

        assertThrows(TaskNotFoundException.class, () -> appController.deleteTask(taskId));
    }
}