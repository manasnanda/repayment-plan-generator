package com.lendico.repayment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Manas Ranjan Nanda
 * Custom exception to throw invalid request parameter exception
 * 
 * **/
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid Request Parameters")
public class InvalidRequestException extends Exception
{
    static final long serialVersionUID = -3387516993334229948L;


    public InvalidRequestException(String message)
    {
        super(message);
    }

}
