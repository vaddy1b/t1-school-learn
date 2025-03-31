package ru.babich.t1schoollearn.repo;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.babich.t1schoollearn.model.Task;

import java.util.List;
import java.util.Optional;

@Repository
public class TaskRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Task> taskRowMapper = (rs, rowNum) -> new Task(
            rs.getLong("id"),
            rs.getString("title"),
            rs.getString("description"),
            rs.getLong("user_id")
    );

    public TaskRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Task> findAll() {
        return jdbcTemplate.query("SELECT * FROM tasks", taskRowMapper);
    }

    public Optional<Task> findById(Long id) {
        return jdbcTemplate.query("SELECT * FROM tasks WHERE id = ?",
                taskRowMapper, id).stream().findFirst();
    }

    public Task save(Task task) {
        if (task.getId() == null) {
            return insert(task);
        } else {
            return update(task);
        }
    }

    private Task insert(Task task) {
        Long id = jdbcTemplate.queryForObject(
                "INSERT INTO tasks (title, description, user_id) VALUES (?, ?, ?) RETURNING id",
                Long.class,
                task.getTitle(), task.getDescription(), task.getUserId()
        );
        task.setId(id);
        return task;
    }

    private Task update(Task task) {
        jdbcTemplate.update(
                "UPDATE tasks SET title = ?, description = ?, user_id = ? WHERE id = ?",
                task.getTitle(), task.getDescription(), task.getUserId(), task.getId());

        return task;
    }

    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM tasks WHERE id = ?", id);
    }
}