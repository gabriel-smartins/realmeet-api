package br.com.sw2you.realmeet.config;

import static br.com.sw2you.realmeet.util.ResponseEntityUtils.notFound;

import br.com.sw2you.realmeet.api.model.ResponseError;
import br.com.sw2you.realmeet.exception.InvalidRequestException;
import br.com.sw2you.realmeet.exception.RoomNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(RoomNotFoundException.class)
    public ResponseEntity<Object> handlerNotFoundException(Exception e) {
        return notFound();
    }

    @ExceptionHandler(InvalidRequestException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public List<ResponseError> handlerInvalidRequestException(InvalidRequestException e) {

        return e
                .getValidationErrors()
                .stream()
                .map(ex -> new ResponseError().field(ex.getField()).errorCode(ex.getErrorCode()))
                .collect(Collectors.toList());
    }
}
