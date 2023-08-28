package com.mjc.school.hadler;

import com.mjc.school.controller.annotation.CommandHandler;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;
import java.util.Map;

public abstract class EntityProcessor {
    protected final Object controller;
    protected Method methodToInvoke;

    protected EntityProcessor(ApplicationContext context, EntityLocation location){
        Map<String, Object> beansWithAnnotation = context.getBeansWithAnnotation(CommandHandler.class);
        controller = beansWithAnnotation.entrySet().stream().
                filter(entry -> entry
                        .getValue()
                        .getClass()
                        .getDeclaredAnnotation(CommandHandler.class)
                        .mainEntityClass().equals(location.getPath()))
                .findAny()
                .map(Map.Entry::getValue)
                .orElseThrow(() -> new BeanInitializationException("there isn't any suitable controller class"));
    }

    public abstract void process();
}
