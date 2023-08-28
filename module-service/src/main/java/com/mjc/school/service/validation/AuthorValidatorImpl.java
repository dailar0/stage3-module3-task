package com.mjc.school.service.validation;

import com.mjc.school.service.DTO.AuthorInputDTO;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class AuthorValidatorImpl implements Validator<AuthorInputDTO> {
    //98
    private static final String nameMessage = "Name should have length from 3 to 15.";
    private static final String generalMessage = "All fields are required.";

    @Override
    public Set<String> validate(AuthorInputDTO authorDTO) {
        HashSet<String> violations = new HashSet<>();

        if ((authorDTO == null) || (authorDTO.getName() == null)) {
            violations.add(generalMessage);
            return violations;
        }

        int titleSize = authorDTO.getName().length();
        if (titleSize < 3 || titleSize > 15)
            violations.add(nameMessage);
        return violations;
    }

}
