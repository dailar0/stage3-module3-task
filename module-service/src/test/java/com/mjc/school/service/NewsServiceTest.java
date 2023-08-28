package com.mjc.school.service;

import com.mjc.school.service.DTO.AuthorInputDTO;
import com.mjc.school.service.DTO.AuthorOutputDTO;
import com.mjc.school.service.DTO.NewsInputDTO;
import com.mjc.school.service.DTO.NewsOutputDTO;
import com.mjc.school.service.exception.EntityNotFoundException;
import com.mjc.school.service.exception.ValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NewsServiceTest {
    @Autowired
    private BaseService<NewsInputDTO, NewsOutputDTO, Long> service;
    @Autowired
    private BaseService<AuthorInputDTO, AuthorOutputDTO, Long> authorService;
    private final Random random = new Random();

    private NewsInputDTO getValidDTO() {
        AuthorInputDTO authorInputDTO = new AuthorInputDTO(random.nextLong(), "validName");
        AuthorOutputDTO authorOutputDTO = authorService.create(authorInputDTO);
        return new NewsInputDTO(random.nextLong(),
                "validTitle",
                "validContent",
                authorOutputDTO.getId());
    }


    @Test
    void create() {
        // given
        NewsInputDTO newsInputDTO = getValidDTO();

        // when
        NewsOutputDTO createdNews = service.create(newsInputDTO);

        // then
        assertNotEquals(createdNews.getId(), newsInputDTO.getId());
        assertEquals(newsInputDTO.getTitle(), createdNews.getTitle());
        assertEquals(newsInputDTO.getContent(), createdNews.getContent());
        assertEquals(newsInputDTO.getAuthorId(), createdNews.getAuthorId());
        assertNotNull(createdNews.getCreateDate());
        assertNotNull(createdNews.getLastUpdateDate());
    }

    @Test
    void getAll() {
        List<NewsOutputDTO> freshNewsList = service.readAll();
        assertNotEquals(freshNewsList.size(), 0);

        NewsInputDTO newsInputDTO1 = getValidDTO();
        NewsInputDTO newsInputDTO2 = getValidDTO();
        NewsOutputDTO created1 = service.create(newsInputDTO1);
        NewsOutputDTO created2 = service.create(newsInputDTO2);
        List<NewsOutputDTO> newsList = service.readAll();

        assertTrue(newsList.containsAll(List.of(created1, created2)));
    }

    @Test
    void getById() {
        NewsInputDTO newsInputDTO = getValidDTO();
        NewsOutputDTO createdNews = service.create(newsInputDTO);

        NewsOutputDTO retrievedNews = service.readById(createdNews.getId());

        assertEquals(createdNews, retrievedNews);
    }

    @Test
    void update() throws InterruptedException {
        // given
        NewsInputDTO newsInputDTO = getValidDTO();
        NewsOutputDTO createdNews = service.create(newsInputDTO);
        NewsInputDTO updatedNewsInputDTO = new NewsInputDTO(createdNews.getId(),
                "Updated test news",
                "This is an updated test news",
                newsInputDTO.getAuthorId());
        // when
        Thread.sleep(30);
        NewsOutputDTO updatedNews = service.update(updatedNewsInputDTO);
        // then
        assertEquals(updatedNewsInputDTO.getId(), updatedNews.getId());
        assertEquals(updatedNewsInputDTO.getTitle(), updatedNews.getTitle());
        assertEquals(updatedNewsInputDTO.getContent(), updatedNews.getContent());
        assertEquals(updatedNewsInputDTO.getAuthorId(), updatedNews.getAuthorId());
        assertNotEquals(createdNews.getLastUpdateDate(), updatedNews.getLastUpdateDate());
    }

    @Test
    void delete() {
        // given
        NewsInputDTO newsInputDTO = getValidDTO();
        NewsOutputDTO createdNews = service.create(newsInputDTO);
        // when
        service.deleteById(createdNews.getId());
        List<NewsOutputDTO> newsList = service.readAll();
        // then
        assertFalse(newsList.contains(createdNews));
        assertThrows(EntityNotFoundException.class, () -> service.readById(createdNews.getId()));
    }

    @Test
    public void testCreateNewsWithNonExistingAuthor() {
        // given
        NewsInputDTO newsInputDTO = new NewsInputDTO(random.nextLong(),
                "validTitle",
                "validContent",
                Long.MAX_VALUE);
        // when, then
        assertThrows(ValidationException.class, () -> service.create(newsInputDTO));
    }

    @Test
    public void testUpdateNonExistingNews() {
        // given
        NewsInputDTO validDTO = getValidDTO();
        NewsInputDTO newsInputDTO = new NewsInputDTO(random.nextLong(),
                "validTitle",
                "validContent",
                validDTO.getAuthorId());
        // when, then
        assertThrows(EntityNotFoundException.class, () -> service.update(newsInputDTO));
    }

    @Test
    public void testDeleteNonExistingNews() {
        // given, when, then
        assertFalse(service.deleteById(Long.MAX_VALUE));
    }
    //null author; readAllById

    @Test
    public void testValidation() {
        // given, when, then
        NewsInputDTO newsInputDTO = new NewsInputDTO(1L, "a", "b", null);
        assertThrows(ValidationException.class, () -> service.create(newsInputDTO));
    }
}