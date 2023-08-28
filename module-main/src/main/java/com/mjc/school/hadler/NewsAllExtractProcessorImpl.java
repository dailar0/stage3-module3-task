package com.mjc.school.hadler;

import com.mjc.school.controller.annotation.CommandHandler;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

@Component
public class NewsAllExtractProcessorImpl extends EntityProcessor {


    public NewsAllExtractProcessorImpl(ApplicationContext context) {
        super(context, EntityLocation.NEWS);
        methodToInvoke = Arrays.stream(controller.getClass().getMethods())
                .filter(method -> method.isAnnotationPresent(CommandHandler.class) &&
                        Arrays.asList(method.getAnnotation(CommandHandler.class).method()).
                                contains(CommandHandler.RequestMethod.GET) &&
                        method.getParameterCount() == 0)
                .findAny()
                .orElseThrow(() -> new BeanInitializationException("there isn't any suitable method"));
    }

    @Override
    public void process() {
        try {
            String result = ((String) methodToInvoke.invoke(controller));
            System.out.println(result);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
