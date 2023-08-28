package com.mjc.school.service.validation;

import com.mjc.school.service.DTO.NewsInputDTO;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class NewsValidatorImpl implements Validator<NewsInputDTO> {
    private static final String titleMessage = "Title should have length from 5 to 30.";
    private static final String contentMessage = "Content should have length from 5 to 255.";
    private static final String generalMessage = "All fields are required.";

    @Override
    public Set<String> validate(NewsInputDTO newsInputDTO) {
        HashSet<String> violations = new HashSet<>();

        if ((newsInputDTO == null) ||
                (newsInputDTO.getTitle() == null) ||
                (newsInputDTO.getContent() == null)) {
            violations.add(generalMessage);
            return violations;
        }

        String title = newsInputDTO.getTitle();
        if (title.length() < 5 || title.length() > 30) {
            violations.add(titleMessage);
        }
        String content = newsInputDTO.getContent();
        if (content.length() < 5 || content.length() > 255) {
            violations.add(contentMessage);
        }

        return violations;
    }
}
