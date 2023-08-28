package com.mjc.school.service.DTO;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.beans.ConstructorProperties;

@Getter
@EqualsAndHashCode
public class NewsInputDTO {
    @Setter
    private Long id;
    private final String title;
    private final String content;
    private final Long authorId;
    @ConstructorProperties({"id","title","content","authorId"})
    public NewsInputDTO(Long id, String title, String content, Long authorId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.authorId = authorId;
    }
}
