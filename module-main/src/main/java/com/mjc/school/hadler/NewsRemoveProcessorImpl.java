package com.mjc.school.hadler;

import com.mjc.school.controller.annotation.CommandHandler;
import com.mjc.school.controller.annotation.CommandParam;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Scanner;

@Component
public class NewsRemoveProcessorImpl extends EntityProcessor {
    public NewsRemoveProcessorImpl(ApplicationContext context) {
        super(context, EntityLocation.NEWS);
        methodToInvoke = Arrays.stream(controller.getClass().getMethods())
                .filter(method -> method.isAnnotationPresent(CommandHandler.class) &&
                        Arrays.stream(method.getAnnotation(CommandHandler.class).method()).toList()
                                .contains(CommandHandler.RequestMethod.DELETE) &&
                        method.getParameterCount() == 1 &&
                        method.getParameters()[0].isAnnotationPresent(CommandParam.class) &&
                        method.getParameters()[0].getType().equals(String.class))
                .findAny()
                .orElseThrow(() -> new BeanInitializationException("there isn't any suitable method"));
    }

    @Override
    public void process() {
        System.out.println("enter news id");
        String id = new Scanner(System.in).nextLine();
        try {
            Object result = methodToInvoke.invoke(controller, id);
            System.out.println(result);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
