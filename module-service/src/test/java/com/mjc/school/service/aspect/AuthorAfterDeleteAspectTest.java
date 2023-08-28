package com.mjc.school.service.aspect;

import com.mjc.school.service.AuthorService;
import com.mjc.school.service.DTO.AuthorInputDTO;
import com.mjc.school.service.DTO.AuthorOutputDTO;
import com.mjc.school.service.DTO.NewsInputDTO;
import com.mjc.school.service.DTO.NewsOutputDTO;
import com.mjc.school.service.NewsService;
import com.mjc.school.service.annotation.OnDelete;
import com.mjc.school.service.exception.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthorAfterDeleteAspectTest {
    @Autowired
    private NewsService newsService;
    @Autowired
    private AuthorService authorService;
    private List<Long> newsIDs;
    private Long authorID;

    @BeforeEach
    public void init() {
        AuthorOutputDTO authorOutputDTO = authorService.create(new AuthorInputDTO(null, "Valid name"));
        NewsOutputDTO createdNews1 = newsService.create(new NewsInputDTO(null,
                "valid title",
                "valid content",
                authorOutputDTO.getId()));
        NewsOutputDTO createdNews2 = newsService.create(new NewsInputDTO(null,
                "valid title",
                "valid content",
                authorOutputDTO.getId()));

        authorID = authorOutputDTO.getId();
        newsIDs = new ArrayList<>(List.of(createdNews1.getId(), createdNews2.getId()));

    }

    @Test
    public void when_annotated_than_cascade() throws InvocationTargetException, IllegalAccessException {
        Optional<Method> annotatedMethod = getAnnotatedMethod();

        if (annotatedMethod.isPresent()) {
            Method method = annotatedMethod.get();
            if ((boolean) method.invoke(authorService, authorID)) {
                OnDelete.CascadeAction action = method.getAnnotation(OnDelete.class).value();
                switch (action){
                    case SET_NULL -> newsIDs.forEach(id->
                            assertNull(newsService.readById(id).getAuthorId()));
                    case REMOVE -> newsIDs.forEach(id->
                            assertThrows(EntityNotFoundException.class, ()->newsService.readById(id)));
                }
            }
        }
    }

    private Optional<Method> getAnnotatedMethod() {
        return Arrays.stream(authorService.getClass().getMethods())
                .filter(method -> method.getAnnotation(OnDelete.class) != null)
                .findFirst();
    }


}