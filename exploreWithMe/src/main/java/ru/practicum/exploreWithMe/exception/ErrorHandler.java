package ru.practicum.exploreWithMe.exception;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleAlrearyExistsException(AlreadyExistsException e) {
        log.error("Already Exists - " + "\n" + e.getMessage());
        return new ErrorResponse("Validation error", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(NotFoundException e) {
        log.error("Not_found " + "\n" + e.getMessage());
        return new ErrorResponse("NOT_FOUND", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleItemException(ValidateException e) {
        log.error("Validate Exception - " + "\n" + e.getMessage());
        return new ErrorResponse("NOT_AVAILABLE", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleForbiddenException(final ForbiddenException e) {
        log.error("Forbidden - " + "\n" + e.getMessage());
        return new ErrorResponse("NOT_AVAILABLE", e.getMessage());
    }

    @ExceptionHandler(FeignException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ErrorResponse handleFeignStatusException(FeignException e, HttpServletResponse response) {
        response.setStatus(e.status());
        log.error("Feign Exception - " + "\n" + e.getMessage());
        return new ErrorResponse("Feign Exception", e.getMessage());
    }
}
