package pl.ale.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Collections;
import java.util.Map;

/**
 * Created by dominik on 04.01.22.
 */
@ControllerAdvice
class ApplicationControllerAdvice {

    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public Map clientExceptionHandler(HttpClientErrorException exception) {

        return Collections.singletonMap("message","Given user is not found");
    }
}
