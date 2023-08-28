package com.mjc.school.controller;

import com.mjc.school.controller.annotation.CommandBody;
import com.mjc.school.controller.annotation.CommandParam;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.DTO.NewsInputDTO;
import com.mjc.school.service.DTO.NewsOutputDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/news")
public class NewsControllerWebImpl implements BaseController<NewsInputDTO, NewsOutputDTO, Long> {

    private final BaseService<NewsInputDTO, NewsOutputDTO, Long> newsService;

    @Override
    @GetMapping
    public List<NewsOutputDTO> readAll() {
        return newsService.readAll();
    }

    @GetMapping("/{id}")
    @Override
    public NewsOutputDTO readById(@PathVariable @CommandParam Long id) {
        return newsService.readById(id);
    }

    @PostMapping
    @Override
    public NewsOutputDTO create(@RequestBody @CommandBody NewsInputDTO createRequest) {
        return newsService.create(createRequest);
    }

    @PutMapping("/update")
    @Override
    public NewsOutputDTO update(@RequestBody @CommandBody NewsInputDTO updateRequest) {
        return newsService.update(updateRequest);
    }

    @Override
    @DeleteMapping("/{id}")
    public boolean deleteById(@PathVariable @CommandParam Long id) {
        return newsService.deleteById(id);
    }
}
