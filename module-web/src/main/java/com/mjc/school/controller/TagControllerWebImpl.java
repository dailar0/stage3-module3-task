package com.mjc.school.controller;

import com.mjc.school.service.BaseService;
import com.mjc.school.service.DTO.TagInputDTO;
import com.mjc.school.service.DTO.TagOutputDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tag")
public class TagControllerWebImpl implements BaseController<TagInputDTO, TagOutputDTO, Long> {
    private final BaseService<TagInputDTO, TagOutputDTO, Long> tagService;

    @GetMapping
    @Override
    public List<TagOutputDTO> readAll() {
        return tagService.readAll();
    }

    @GetMapping("/{id}")
    @Override
    public TagOutputDTO readById(@PathVariable Long id) {
        return tagService.readById(id);
    }

    @PostMapping
    @Override
    public TagOutputDTO create(@RequestBody TagInputDTO createRequest) {
        return tagService.create(createRequest);
    }

    @PutMapping()
    @Override
    public TagOutputDTO update(@RequestBody TagInputDTO updateRequest) {
        return tagService.update(updateRequest);
    }

    @DeleteMapping("/{id}")
    @Override
    public boolean deleteById(@PathVariable Long id) {
        return tagService.deleteById(id);
    }
}
