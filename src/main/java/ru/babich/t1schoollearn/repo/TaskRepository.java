package ru.babich.t1schoollearn.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.babich.t1schoollearn.model.Task;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Task save(Task task);
    @Override
    List<Task> findAll();

    @Override
    Optional<Task> findById(Long id);

    @Override
    void deleteById(Long id);

    @Override
    boolean existsById(Long id);


}