package com.morebooks.morebooks.validators;

public interface Validator<T> {

    void validate(T request) throws Exception;

}
