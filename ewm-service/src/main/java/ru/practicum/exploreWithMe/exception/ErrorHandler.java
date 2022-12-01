package ru.practicum.exploreWithMe.exception;

import feign.FeignException;
import ru.practicum.exploreWithMe.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleAlreadyExistsException(final AlreadyExistsException e) {
        log.error("Already Exists - " + e.getMessage());
        return new ErrorResponse(e.getMessage(), "CONFLICT",
                ErrorState.CONFLICT);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(final NotFoundException e) {
        log.error("Not_found " + e.getMessage());
        return new ErrorResponse(e.getMessage(), "NOT_FOUND",
                ErrorState.NOT_FOUND);
    }

    @ExceptionHandler(ValidateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidateException(final ValidateException e) {
        log.error("Validate Exception - " + e.getMessage());
        return new ErrorResponse(e.getMessage(), "BAD_REQUEST",
                ErrorState.BAD_REQUEST);
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleForbiddenException(final ForbiddenException e) {
        log.error("Forbidden - " + e.getMessage());
        return new ErrorResponse(e.getMessage(), "FORBIDDEN",
                ErrorState.FORBIDDEN);
    }

    @ExceptionHandler(FeignException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ErrorResponse handleFeignStatusException(FeignException e, HttpServletResponse response) {
        response.setStatus(e.status());
        log.error("Feign Exception - " + e.getMessage());
        return new ErrorResponse(e.getMessage(), "SERVICE_UNAVAILABLE",
                ErrorState.SERVICE_UNAVAILABLE);
    }
}
