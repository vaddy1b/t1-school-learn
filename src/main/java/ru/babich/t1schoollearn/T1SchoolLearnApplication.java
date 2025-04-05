package ru.babich.t1schoollearn;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.babich.t1schoollearn.model.Task;
import ru.babich.t1schoollearn.model.TaskDTO;
import ru.babich.t1schoollearn.repo.TaskRepository;
import ru.babich.t1schoollearn.service.TaskService;

import java.util.List;

@SpringBootApplication
public class T1SchoolLearnApplication {

    public static void main(String[] args) {
        SpringApplication.run(T1SchoolLearnApplication.class,args);
    }

}
