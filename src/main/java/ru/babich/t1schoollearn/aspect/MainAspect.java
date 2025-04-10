package ru.babich.t1schoollearn.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;


@Aspect
@Component
@Slf4j
public class MainAspect {


    @Before("execution(* ru.babich.t1schoollearn.controller.AppController.getTaskById())")
    public void logBeforeMethodCall() {
        log.info("Before advice: Вызов метода контроллера");
    }

    @AfterThrowing(
            pointcut = "(execution(* ru.babich.t1schoollearn.repo.TaskRepository.*(..)))",
            throwing = "e"
    )
    public void logAfterThrowing(Exception e) {
        log.error("AfterThrowing advice: Ошибка в репозитории - {}", e.getMessage());
    }

    @Around("@annotation(ru.babich.t1schoollearn.annottaion.TrackTrace)")
    public Object logExceptionTrace(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        log.info("TrackTrace advice: Начало выполнения метода {}", methodName);

        try {
            Object result = joinPoint.proceed();
            log.info("TrackTrace advice: Метод {} выполнен успешно", methodName);
            return result;
        } catch (Exception ex) {
            log.error("TrackTrace advice: Ошибка в методе {} - {}", methodName, ex.getMessage());
            throw ex;
        }
    }

    @AfterReturning(
            pointcut = "(execution(* ru.babich.t1schoollearn.controller.AppController.getTaskById()))",
            returning = "result"
    )
    public void logAfterReturning(Object result) {
        log.info("AfterReturning advice: Успешное выполнение запроса по поиску taskById. Результат: {}", result);
    }

    @Around("(execution(* ru.babich.t1schoollearn.repo.TaskRepository.*(..)))")
    public Object logAroundRepositoryMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        log.info("Around advice замера времени (начало): Вызов метода репозитория {}", joinPoint.getSignature().getName());

        try {
            Object result = joinPoint.proceed();
            long endTime = System.currentTimeMillis();
            log.info("Around advice (конец): Метод репозитория {} выполнен за {} мс",
                    joinPoint.getSignature().getName(),
                    (endTime - startTime));
            return result;
        } catch (Exception ex) {
            log.error("Around advice: Ошибка в методе репозитория {}", joinPoint.getSignature().getName());
            throw ex;
        }
    }
}
