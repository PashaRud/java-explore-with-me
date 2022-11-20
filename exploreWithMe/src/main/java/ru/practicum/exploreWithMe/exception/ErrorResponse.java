package ru.practicum.exploreWithMe.exception;

public class ErrorResponse {
    private String message;
    private String error;

    public ErrorResponse(String message, String error) {
        this.message = message;
        this.error = error;
    }
}
