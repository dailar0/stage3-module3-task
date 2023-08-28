package com.mjc.school.service.validation;

import com.mjc.school.service.DTO.AuthorInputDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class AuthorValidatorImplTest {
    @Autowired
    private Validator<AuthorInputDTO> validator;

    private static final String generalMessage = "All fields are required.";

    private Long getValidId() {
        return 0L;
    }

    @Test
    void testNullObject() {
        Set<String> violations = validator.validate(null);
        assertTrue(violations.contains(generalMessage));
    }

    @Test
    void testNullName() {
        AuthorInputDTO dto = new AuthorInputDTO(getValidId(), null);
        Set<String> violations = validator.validate(dto);
        assertTrue(violations.contains(generalMessage));
    }

    @Test
    void testValidDTO() {
        AuthorInputDTO dto = new AuthorInputDTO(getValidId(), "John Doe");
        Set<String> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testShortName() {
        AuthorInputDTO dto = new AuthorInputDTO(getValidId(), "ab");
        Set<String> violations = validator.validate(dto);
        assertTrue(violations.contains("Name should have length from 3 to 15."));
    }

    @Test
    void testLongName() {
        AuthorInputDTO dto = new AuthorInputDTO(getValidId(), "abcdefghijklmnopqrstuvwxyz");
        Set<String> violations = validator.validate(dto);
        assertTrue(violations.contains("Name should have length from 3 to 15."));
    }
}