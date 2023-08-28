package com.mjc.school.service.mapping;

import com.mjc.school.repository.model.Author;
import com.mjc.school.service.DTO.AuthorInputDTO;
import com.mjc.school.service.DTO.AuthorOutputDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    @Mapping(target = "id", source = "createDTO.id", qualifiedByName = "generateIdIfAbsent")
    @Mapping(target = "createDate", expression = "java(LocalDateTime.now())", dateFormat = "yyyy-MM-dd'T'HH:mm'Z'")
    @Mapping(target = "lastUpdateDate", expression = "java(LocalDateTime.now())", dateFormat = "yyyy-MM-dd'T'HH:mm'Z'")
    Author mapCreateToAuthor(AuthorInputDTO createDTO);

    AuthorOutputDTO mapAuthorToOutput(Author author);

    List<AuthorOutputDTO> mapAuthorToOutputList(Collection<Author> authors);

    @Named("generateIdIfAbsent")
    default Long generateIdIfAbsent(Long id) {
        Random random = new Random();
        return Objects.requireNonNullElseGet(id, random::nextLong);
    }
}
