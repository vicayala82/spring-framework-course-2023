package com.vicayala.demotravel.aspects;

import com.vicayala.demotravel.util.BestTravelUtil;
import com.vicayala.demotravel.util.anotations.Notify;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
@Aspect
public class NotifyAspect {

    private static final String LINE_FORMAT = "At %s new request with size page %s and order %s";

    @After(value = "@annotation(com.vicayala.demotravel.util.anotations.Notify)")
    public void notifyInFile(JoinPoint joinPoint) throws IOException {
        var args = joinPoint.getArgs();
        var size = args[1];
        var order = args[2]== null ? "NONE" : args[2];
        var text = String.format(LINE_FORMAT, LocalDateTime.now(), size.toString(), order);
        var signature = (MethodSignature)joinPoint.getSignature();
        var method = signature.getMethod();
        var annotation = method.getAnnotation(Notify.class);
        BestTravelUtil.writeNotification(text, annotation.value());
    }


}
