package ru.babich.t1schoollearn;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.babich.t1schoollearn.model.Task;
import ru.babich.t1schoollearn.repo.TaskRepository;

@ComponentScan
public class T1SchoolLearnApplication {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(T1SchoolLearnApplication.class);

        TaskRepository taskRepository = applicationContext.getBean(TaskRepository.class);

        // Тестируем работу с базой данных
        System.out.println("Все задачи в базе:");
        taskRepository.findAll().forEach(System.out::println);

        Task newTask = new Task();
        newTask.setTitle("Новая задача из main");
        newTask.setDescription("Создана в основном классе");
        newTask.setUserId(3L);
        taskRepository.save(newTask);

        taskRepository.findAll().forEach(System.out::println);

        applicationContext.close();
    }

}
