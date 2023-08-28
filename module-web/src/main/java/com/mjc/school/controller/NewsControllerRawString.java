package com.mjc.school.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjc.school.controller.annotation.CommandBody;
import com.mjc.school.controller.annotation.CommandHandler;
import com.mjc.school.controller.annotation.CommandParam;
import com.mjc.school.service.DTO.NewsInputDTO;
import com.mjc.school.service.DTO.NewsOutputDTO;
import com.mjc.school.service.exception.InternalServerErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@CommandHandler(mainEntityClass = "com.mjc.school.repository.model.News")
public class NewsControllerRawString {
    private final NewsControllerWebImpl webController;
    private final ObjectMapper mapper;

    @CommandHandler(method = CommandHandler.RequestMethod.GET)
    public String readAll() {
        List<NewsOutputDTO> newsOutputDTOS = webController.readAll();
        try {
            return mapper.writeValueAsString(newsOutputDTOS);
        } catch (JsonProcessingException e) {
            throw new InternalServerErrorException("can't serialize object");
        }
    }

    @CommandHandler(method = CommandHandler.RequestMethod.GET)
    public String readById(@CommandParam String id) {
        NewsOutputDTO newsOutputDTO = webController.readById(Long.valueOf(id));
        return convertToJson(newsOutputDTO);
    }

    @CommandHandler(method = CommandHandler.RequestMethod.POST)
    public String create(@CommandBody String createRequest) {
        NewsInputDTO createDTO = convertFromJson(createRequest);
        NewsOutputDTO createdDTO = webController.create(createDTO);
        return convertToJson(createdDTO);
    }

    @CommandHandler(method = CommandHandler.RequestMethod.PUT)
    public String update(@CommandBody String updateRequest) {
        NewsInputDTO updateDTO = convertFromJson(updateRequest);
        NewsOutputDTO updatedDTO = webController.update(updateDTO);
        return convertToJson(updatedDTO);

    }

    @CommandHandler(method = CommandHandler.RequestMethod.DELETE)
    public boolean deleteById(@CommandParam String id) {
        try {
            return webController.deleteById(Long.valueOf(id));
        } catch (NumberFormatException e) {
            throw new InternalServerErrorException(String.format("can't parse provided id: %s", id));
        }
    }

    private String convertToJson(NewsOutputDTO newsOutputDTO) {
        try {
            return mapper.writeValueAsString(newsOutputDTO);
        } catch (JsonProcessingException e) {
            throw new InternalServerErrorException("can't serialize object");
        }
    }

    private NewsInputDTO convertFromJson(String createRequest) {
        try {
            return mapper.readValue(createRequest, NewsInputDTO.class);
        } catch (JsonProcessingException e) {
            throw new InternalServerErrorException("can't deserialize object");
        }
    }
}
