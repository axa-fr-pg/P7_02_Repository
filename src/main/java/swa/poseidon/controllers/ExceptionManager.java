package swa.poseidon.controllers;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionManager extends ResponseEntityExceptionHandler
{
	public static final String MESSAGE_NOT_SUCH_ELEMENT = "The expected entity was not found in the database";
	public static final String MESSAGE_INVALID_REQUEST = "The request was not consistent";
	
	@Override
	public final ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) 
	{
		ArrayList<String> errorList = new ArrayList<String>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) 
        {
            errorList.add(error.getDefaultMessage());
        }
		return new ResponseEntity<>(errorList, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NoSuchElementException.class)
	public final ResponseEntity<String> handleNoSuchElement() 
	{
		return new ResponseEntity<>(MESSAGE_NOT_SUCH_ELEMENT, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InvalidRequestException.class)
	public final ResponseEntity<String> handleInvalidRequest() {
		return new ResponseEntity<>(MESSAGE_INVALID_REQUEST, HttpStatus.NOT_FOUND);
	}

}
