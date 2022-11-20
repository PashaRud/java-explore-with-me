package ru.practicum.exploreWithMe.exception;

public class ValidateException extends IllegalArgumentException {

    public ValidateException(String message) {
        super(message);
    }
}