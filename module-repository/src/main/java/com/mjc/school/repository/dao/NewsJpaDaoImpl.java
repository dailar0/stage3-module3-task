package com.mjc.school.repository.dao;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.News;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class NewsJpaDaoImpl implements BaseRepository<News, Long> {
    private final EntityManagerFactory entityManagerFactory;

    @Override
    public List<News> readAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<News> news = entityManager.createQuery("from News", News.class).getResultList();
        entityManager.close();
        return news;
    }

    @Override
    public Optional<News> readById(Long id) {
        return Optional.empty();
    }

    @Override
    public News create(News entity) {
        return null;
    }

    @Override
    public News update(News entity) {
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }

    @Override
    public boolean existById(Long id) {
        return false;
    }
}
