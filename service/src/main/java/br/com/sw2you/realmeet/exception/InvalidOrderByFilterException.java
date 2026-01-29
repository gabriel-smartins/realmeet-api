package br.com.sw2you.realmeet.exception;

import static br.com.sw2you.realmeet.validator.ValidatorConstants.ALLOCATION_ORDER_BY;
import static br.com.sw2you.realmeet.validator.ValidatorConstants.INVALID;

import br.com.sw2you.realmeet.validator.ValidationError;


public class InvalidOrderByFilterException extends InvalidRequestException {

    public InvalidOrderByFilterException() {
        super(new ValidationError(ALLOCATION_ORDER_BY, ALLOCATION_ORDER_BY + INVALID));
    }
}
