package ru.practicum.exploreWithMe.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {
    private String error;
    private String message;
    private ErrorState status;


    public ErrorResponse(String error, String message, ErrorState status) {
        this.error = error;
        this.message = message;
        this.status = status;
    }
}
