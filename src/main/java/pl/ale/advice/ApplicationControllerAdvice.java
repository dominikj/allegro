package pl.ale.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Collections;
import java.util.Map;

@ControllerAdvice
class ApplicationControllerAdvice {

    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public Map clientExceptionHandler(HttpClientErrorException exception) {

        // TODO: Now all exceptions regarding to communication with github service are "transformed" to HTTP 404.
        return Collections.singletonMap("message","Given user is not found");
    }
}
