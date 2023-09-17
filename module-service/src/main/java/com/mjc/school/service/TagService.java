package com.mjc.school.service;

import com.mjc.school.repository.TagRepository;
import com.mjc.school.repository.model.Tag;
import com.mjc.school.service.DTO.TagInputDTO;
import com.mjc.school.service.DTO.TagOutputDTO;
import com.mjc.school.service.exception.EntityNotFoundException;
import com.mjc.school.service.exception.ValidationException;
import com.mjc.school.service.mapping.TagMapper;
import com.mjc.school.service.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class TagService implements BaseService<TagInputDTO, TagOutputDTO, Long> {
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;
    private final Validator<TagInputDTO> tagValidator;

    @Override
    public List<TagOutputDTO> readAll() {
        List<Tag> tags = tagRepository.readAll();
        return tagMapper.mapTagListToOutList(tags);
    }

    @Override
    public TagOutputDTO readById(Long id) {
        return tagRepository.readById(id)
                .map(tagMapper::mapTagToDTO)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Tag with ID %d not found.", id)));
    }

    @Transactional
    @Override
    public TagOutputDTO create(TagInputDTO createRequest) {
        validate(createRequest);

        if (createRequest.getId() != null)
            createRequest.setId(null);

        Tag tag = tagMapper.mapDtoToTag(createRequest);

        tagRepository.create(tag);
        return tagMapper.mapTagToDTO(tag);
    }

    @Transactional
    @Override
    public TagOutputDTO update(TagInputDTO updateRequest) {
        validate(updateRequest);

        if (!tagRepository.existById(updateRequest.getId())) {
            throw new EntityNotFoundException(String.format("Tag with ID %d not found.", updateRequest.getId()));
        }

        Tag input = tagMapper.mapDtoToTag(updateRequest);
        Tag updated = tagRepository.update(input);
        return tagMapper.mapTagToDTO(updated);
    }

    @Transactional
    @Override
    public boolean deleteById(Long id) {
        return tagRepository.deleteById(id);
    }

    private void validate(TagInputDTO tagInputDTO) {
        Set<String> violations = tagValidator.validate(tagInputDTO);

        if (!violations.isEmpty()) {
            String message = Validator.formErrorMessage(violations);
            throw new ValidationException(message);
        }
    }
}
