package ru.practicum.exploreWithMe.exception;

public class NotFoundException extends IllegalArgumentException {

    public NotFoundException(String message) {
        super(message);
    }
}
