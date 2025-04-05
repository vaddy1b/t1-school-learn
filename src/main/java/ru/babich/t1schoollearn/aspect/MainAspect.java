package ru.babich.t1schoollearn.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.babich.t1schoollearn.T1SchoolLearnApplication;
import ru.babich.t1schoollearn.annottaion.ExceptionTrace;


@Aspect
@Component
public class MainAspect {

    private static final Logger logger = LoggerFactory.getLogger(T1SchoolLearnApplication.class);

    @Before("execution(* ru.babich.t1schoollearn.controller.AppController.getAllTasks())")
    public void logBeforeMethodCall() {
        logger.info("Before advice: Вызов метода контроллера");
    }

    @AfterThrowing(
            pointcut = "(execution(* ru.babich.t1schoollearn.repo.TaskRepository.*(..)))",
            throwing = "e"
    )
    public void logAfterThrowing(Exception e) {
        logger.error("AfterThrowing advice: Ошибка в репозитории - {}", e.getMessage());
    }

    @Around("@annotation(ru.babich.t1schoollearn.annottaion.ExceptionTrace)")
    public Object logExceptionTrace(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        logger.info("ExceptionTrace advice: Начало выполнения метода {}", methodName);

        try {
            Object result = joinPoint.proceed();
            logger.info("ExceptionTrace advice: Метод {} выполнен успешно", methodName);
            return result;
        } catch (Exception ex) {
            logger.error("ExceptionTrace advice: Ошибка в методе {} - {}", methodName, ex.getMessage());
            throw ex;
        }
    }

    @AfterReturning(
            pointcut = "(execution(* ru.babich.t1schoollearn.controller.AppController.getTaskById()))",
            returning = "result"
    )
    public void logAfterReturning(Object result) {
        logger.info("AfterReturning advice: Успешное выполнение GET запроса. Результат: {}", result);
    }

    @Around("(execution(* ru.babich.t1schoollearn.repo.TaskRepository.*(..)))")
    public Object logAroundRepositoryMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        logger.info("Around advice (начало): Вызов метода репозитория {}", joinPoint.getSignature().getName());

        try {
            Object result = joinPoint.proceed();
            long endTime = System.currentTimeMillis();
            logger.info("Around advice (конец): Метод репозитория {} выполнен за {} мс",
                    joinPoint.getSignature().getName(),
                    (endTime - startTime));
            return result;
        } catch (Exception ex) {
            logger.error("Around advice: Ошибка в методе репозитория {}", joinPoint.getSignature().getName());
            throw ex;
        }
    }
}
