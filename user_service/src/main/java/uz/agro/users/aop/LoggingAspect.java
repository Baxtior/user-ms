package uz.agro.users.aop;

import uz.agro.users.dto.request.LoggingObject;
import uz.agro.users.service.QueueSender;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Aspect
@Component
public class LoggingAspect {
    @Autowired
    private QueueSender queueSender;

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("@annotation(Log)")
    public void logPointcut(){
    }

    @Around("logPointcut())")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        Object[] args = joinPoint.getArgs();
        long executionTime = System.currentTimeMillis() - startTime;

        LoggingObject loggingObject = new LoggingObject(
                joinPoint.getSignature().getName(),
                args[0].toString(),
                result.toString(),
                LocalDateTime.now(),
                executionTime
        );

        queueSender.sendJsonMessage(loggingObject);
        return result;
    }

}
