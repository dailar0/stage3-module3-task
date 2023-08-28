package com.mjc.school.service.validation;

import com.mjc.school.service.DTO.NewsInputDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NewsValidatorImplTest {
    @Autowired
    Validator<NewsInputDTO> validator;
    Random random = new Random();

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final String titleMessage = "Title should have length from 5 to 30.";
    private static final String contentMessage = "Content should have length from 5 to 255.";
    private static final String generalMessage = "All fields are required.";

    public static String randomString(int minLength, int maxLength) {
        if (minLength > maxLength) {
            throw new IllegalArgumentException("Minimum length cannot be greater than maximum length.");
        }

        Random random = new Random();
        int length = random.nextInt(maxLength - minLength + 1) + minLength;

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }

    private static String getValidTitle() {
        return randomString(5, 30);
    }

    private static String getValidContent() {
        return randomString(5, 255);
    }

    private static long getValidAuthor() {
        return 0L;
    }

    @Test
    public void testShortTitle() {
        NewsInputDTO newsInputDTO = new NewsInputDTO(
                random.nextLong(),
                randomString(0, 4),
                getValidContent(),
                random.nextLong());
        Set<String> violations = validator.validate(newsInputDTO);
        assertTrue(violations.contains(titleMessage));
    }

    @Test
    public void testLongTitle() {
        NewsInputDTO newsInputDTO = new NewsInputDTO(
                random.nextLong(),
                randomString(30, 1000),
                getValidTitle(),
                random.nextLong());
        Set<String> violations = validator.validate(newsInputDTO);
        assertTrue(violations.contains(titleMessage));
    }

    @Test
    public void testShortContent() {
        NewsInputDTO newsInputDTO = new NewsInputDTO(random.nextLong(),
                getValidTitle(),
                randomString(0, 4),
                random.nextLong());
        Set<String> violations = validator.validate(newsInputDTO);
        assertTrue(violations.contains(contentMessage));
    }

    @Test
    public void testLongContent() {
        NewsInputDTO newsInputDTO = new NewsInputDTO(random.nextLong(),
                getValidTitle(),
                randomString(256, 1000),
                getValidAuthor());
        Set<String> violations = validator.validate(newsInputDTO);
        assertTrue(violations.contains(contentMessage));
    }

    @Test
    public void testOtherPropertiesNotViolated() {
        NewsInputDTO newsInputDTO = new NewsInputDTO(random.nextLong(),
                getValidTitle(),
                getValidContent(),
                random.nextLong());
        Set<String> violations = validator.validate(newsInputDTO);
        assertFalse(violations.size() > 0);
    }

    @Test
    public void testViolationSetContainsOneErrorMessagePerType() {
        NewsInputDTO newsInputDTO = new NewsInputDTO(random.nextLong(),
                randomString(0, 4),
                randomString(0, 4),
                getValidAuthor());
        Set<String> violations = validator.validate(newsInputDTO);
        assertEquals(2, violations.size());
        assertTrue(violations.contains(titleMessage));
        assertTrue(violations.contains(contentMessage));
    }

    @Test
    public void testMaxBoundaryValues() {
        NewsInputDTO newsInputDTO = new NewsInputDTO(random.nextLong(),
                randomString(30, 30),
                randomString(255, 255),
                getValidAuthor());
        Set<String> violations = validator.validate(newsInputDTO);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testMinBoundaryValues() {
        NewsInputDTO newsInputDTO = new NewsInputDTO(random.nextLong(),
                randomString(5, 5),
                randomString(5, 5),
                getValidAuthor());
        Set<String> violations = validator.validate(newsInputDTO);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testNullTitle() {
        NewsInputDTO newsInputDTO = new NewsInputDTO(random.nextLong(),
                null,
                getValidContent(),
                getValidAuthor());
        Set<String> violations = validator.validate(newsInputDTO);
        assertTrue(violations.contains(generalMessage));
    }

    @Test
    public void testNullContent() {
        NewsInputDTO newsInputDTO = new NewsInputDTO(random.nextLong(),
                getValidTitle(),
                null,
                getValidAuthor());
        Set<String> violations = validator.validate(newsInputDTO);
        assertTrue(violations.contains(generalMessage));
    }

    @Test
    public void TestNullObject() {
        Set<String> violations1 = validator.validate(null);
        assertTrue(violations1.contains(generalMessage));
    }

}