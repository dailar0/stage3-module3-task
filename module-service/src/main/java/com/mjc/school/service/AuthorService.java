package com.mjc.school.service;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.Author;
import com.mjc.school.service.DTO.AuthorInputDTO;
import com.mjc.school.service.DTO.AuthorOutputDTO;
import com.mjc.school.service.annotation.OnDelete;
import com.mjc.school.service.exception.EntityNotFoundException;
import com.mjc.school.service.exception.ValidationException;
import com.mjc.school.service.mapping.AuthorMapper;
import com.mjc.school.service.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class AuthorService implements BaseService<AuthorInputDTO, AuthorOutputDTO, Long> {
    private final BaseRepository<Author, Long> repository;
    private final AuthorMapper mapper;
    private final Validator<AuthorInputDTO> validator;

    @Override
    public List<AuthorOutputDTO> readAll() {
        return mapper.mapAuthorToOutputList(repository.readAll());
    }

    @Override
    public AuthorOutputDTO readById(Long id) {
        return repository.readById(id)
                .map(mapper::mapAuthorToOutput)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Author with ID %d not found.", id)));
    }

    @Override
    public AuthorOutputDTO create(AuthorInputDTO createRequest) {
        validate(createRequest);

        if (createRequest.getId() != null)
            createRequest.setId(null);

        Author input = mapper.mapCreateToAuthor(createRequest);
        Author created = repository.create(input);
        return mapper.mapAuthorToOutput(created);
    }

    @Override
    public AuthorOutputDTO update(AuthorInputDTO updateRequest) {
        if (!repository.existById(updateRequest.getId())) {
            throw new EntityNotFoundException(String.format("Author with ID %d not found.", updateRequest.getId()));
        }

        validate(updateRequest);
        Author input = mapper.mapCreateToAuthor(updateRequest);
        Author updated = repository.update(input);
        return mapper.mapAuthorToOutput(updated);
    }

    @Override
    @OnDelete(OnDelete.CascadeAction.REMOVE)
    public boolean deleteById(Long id) {
        return repository.deleteById(id);
    }

    private void validate(AuthorInputDTO authorInputDTO) {
        Set<String> violations = validator.validate(authorInputDTO);
        if (!violations.isEmpty()) {
            String message = Validator.formErrorMessage(violations);
            throw new ValidationException(message);
        }
    }
}
