package com.mjc.school.hadler;

import com.mjc.school.controller.annotation.CommandBody;
import com.mjc.school.controller.annotation.CommandHandler;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Scanner;

@Component
public class NewsCreateProcessorImpl extends EntityProcessor {

    public NewsCreateProcessorImpl(ApplicationContext context) {
        super(context,EntityLocation.NEWS);
        methodToInvoke = Arrays.stream(controller.getClass().getMethods())
                .filter(method -> method.isAnnotationPresent(CommandHandler.class))
                .filter(method -> Arrays.asList(method.getAnnotation(CommandHandler.class).method())
                        .contains(CommandHandler.RequestMethod.POST))
                .filter(method -> method.getParameterCount() == 1)
                .filter(method -> method.getParameters()[0].isAnnotationPresent(CommandBody.class) &&
                        method.getParameters()[0].getType().equals(String.class))
                .findAny()
                .orElseThrow(() -> new BeanInitializationException("there isn't any suitable method"));
    }

    @Override
    public void process() {
        System.out.println("enter one-line news in json format");
        String newsString = new Scanner(System.in).nextLine();
        try {
            String result = ((String) methodToInvoke.invoke(controller, newsString));
            System.out.println(result);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
