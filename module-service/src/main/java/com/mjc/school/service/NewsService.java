package com.mjc.school.service;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.Author;
import com.mjc.school.repository.model.News;
import com.mjc.school.service.DTO.NewsInputDTO;
import com.mjc.school.service.DTO.NewsOutputDTO;
import com.mjc.school.service.exception.EntityNotFoundException;
import com.mjc.school.service.exception.ValidationException;
import com.mjc.school.service.mapping.NewsMapper;
import com.mjc.school.service.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class NewsService implements BaseService<NewsInputDTO, NewsOutputDTO, Long> {
    private final BaseRepository<News, Long> newsDAO;
    private final BaseRepository<Author, Long> authorRepository;
    private final Validator<NewsInputDTO> newsValidator;
    private final NewsMapper mapper;


    @Override
    public NewsOutputDTO create(NewsInputDTO createDTO) {
        validate(createDTO);

        if (createDTO.getId() != null)
            createDTO.setId(null);

        News news = mapper.mapCreateToNews(createDTO);
        News savedNews = newsDAO.create(news);
        return mapper.mapNewsToOutput(savedNews);
    }

    public NewsOutputDTO update(NewsInputDTO updateRequest) {
        validate(updateRequest);
        if (newsDAO.existById(updateRequest.getId())) {
            News mapped = mapper.mapCreateToNews(updateRequest);
            News updated = newsDAO.update(mapped);
            return mapper.mapNewsToOutput(updated);
        } else
            throw new EntityNotFoundException(String.format("News with ID %d not found.", updateRequest.getId()));
    }

    private void validate(NewsInputDTO newsInputDTO) {
        Set<String> violations = newsValidator.validate(newsInputDTO);

        if (newsInputDTO.getAuthorId() != null && !authorRepository.existById(newsInputDTO.getAuthorId())) {
            violations.add(String.format("An author with ID %d should exist", newsInputDTO.getAuthorId()));
        }

        if (!violations.isEmpty()) {
            String message = Validator.formErrorMessage(violations);
            throw new ValidationException(message);
        }
    }

    @Override
    public List<NewsOutputDTO> readAll() {
        return mapper.mapNewsToOutputDTOList(newsDAO.readAll());
    }

    @Override
    public NewsOutputDTO readById(Long id) {
        return newsDAO.readById(id)
                .map(mapper::mapNewsToOutput)
                .orElseThrow(() -> new EntityNotFoundException(String.format("News with ID %d not found.", id)));
    }


    @Override
    public boolean deleteById(Long id) {
        return newsDAO.deleteById(id);
    }

    public List<NewsOutputDTO> readAllByAuthorId(Long authorId) {
        if (authorId == null)
            return Collections.emptyList();
        return newsDAO
                .readAll().stream()
                .filter(news -> authorId.equals(news.getAuthor().getId()))
                .map(mapper::mapNewsToOutput)
                .collect(Collectors.toList());
    }
}
