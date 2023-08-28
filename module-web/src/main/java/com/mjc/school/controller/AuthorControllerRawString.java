package com.mjc.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjc.school.controller.annotation.CommandHandler;
import com.mjc.school.controller.annotation.CommandParam;
import com.mjc.school.service.exception.InternalServerErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@CommandHandler(mainEntityClass = "com.mjc.school.repository.model.Author")
public class AuthorControllerRawString {
    private final AuthorControllerWebImpl webController;

    @CommandHandler(method = CommandHandler.RequestMethod.DELETE)
    public boolean deleteById(@CommandParam String id) {
        try {
            return webController.deleteById(Long.valueOf(id));
        } catch (NumberFormatException e) {
            throw new InternalServerErrorException(String.format("can't parse provided id: %s", id));
        }
    }
}
