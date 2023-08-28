package com.mjc.school.service.DTO.aspect;

import com.mjc.school.service.DTO.NewsInputDTO;
import com.mjc.school.service.DTO.NewsOutputDTO;
import com.mjc.school.service.NewsService;
import com.mjc.school.service.annotation.OnDelete;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
@RequiredArgsConstructor
public class AuthorAfterDeleteAspect {
    private final NewsService newsService;

    @AfterReturning(pointcut = "execution(* com.mjc.school.service.AuthorService.*(Long)) &&" +
            " args(id) &&" +
            " @annotation(onDelete)",
            returning = "isDeleted")
    public void doCascade(boolean isDeleted, Long id, OnDelete onDelete) {
        if (!isDeleted || id == null)
            return;

        List<NewsOutputDTO> newsOutputDTOS = newsService.readAllByAuthorId(id);
        switch (onDelete.value()) {
            case SET_NULL -> newsOutputDTOS.forEach(newsOutputDTO -> {
                long newsID = newsOutputDTO.getId();
                String title = newsOutputDTO.getTitle();
                String content = newsOutputDTO.getContent();
                NewsInputDTO updateDTO = new NewsInputDTO(newsID, title, content, null);
                newsService.update(updateDTO);
            });
            case REMOVE -> newsOutputDTOS.stream()
                    .map(NewsOutputDTO::getId)
                    .forEach(newsService::deleteById);
            default -> throw new UnsupportedOperationException(
                    String.format("Action for cascade option %s is not implemented", onDelete.value()));
        }
    }
}
