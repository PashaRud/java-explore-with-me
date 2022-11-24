package ru.practicum.exploreWithMe.exception;

public class AlreadyExistsException extends RuntimeException  {

    public AlreadyExistsException(String message) {
        super(message);
    }
}
