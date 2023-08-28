package com.mjc.school.service.validation;

import java.util.Set;
import java.util.StringJoiner;

public interface Validator<T> {
    //set of violations
    Set<String> validate(T t);

    static String formErrorMessage(Set<String> violations) {
        StringJoiner message = new StringJoiner(" ");
        violations.forEach(message::add);
        return message.toString();
    }
}
