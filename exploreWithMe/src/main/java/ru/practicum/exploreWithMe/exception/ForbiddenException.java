package ru.practicum.exploreWithMe.exception;

public class ForbiddenException extends IllegalArgumentException {

    public ForbiddenException(String message) {
        super(message);
    }
}
