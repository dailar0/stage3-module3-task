package com.mjc.school.service.validation;

import com.mjc.school.repository.annotation.Length;

import java.util.Set;

public class LengthValidatorImpl implements Validator<String> {
    @Override
    public Set<String> validate(String string) {
        return null;
    }

    public Set<String> validate(String fieldName, Length length){

        return null;
    }
}
