package com.mjc.school.service.mapping;

import com.mjc.school.repository.model.News;
import com.mjc.school.service.DTO.NewsInputDTO;
import com.mjc.school.service.DTO.NewsOutputDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Mapper(componentModel = "spring")
public interface NewsMapper {
    @Mapping(target = "id", source = "dto.id", qualifiedByName = "generateIdIfAbsent")
    @Mapping(target = "createDate", expression = "java(LocalDateTime.now())", dateFormat = "yyyy-MM-dd'T'HH:mm'Z'")
    @Mapping(target = "lastUpdateDate", expression = "java(LocalDateTime.now())", dateFormat = "yyyy-MM-dd'T'HH:mm'Z'")
    News mapCreateToNews(NewsInputDTO dto);

    NewsOutputDTO mapNewsToOutput(News news);

    List<NewsOutputDTO> mapNewsToOutputDTOList(Collection<News> newsCollection);

    @Named("generateIdIfAbsent")
    default Long generateIdIfAbsent(Long id) {
        Random random = new Random();
        return Objects.requireNonNullElseGet(id, random::nextLong);
    }
}

