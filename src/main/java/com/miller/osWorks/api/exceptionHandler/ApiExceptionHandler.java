package com.miller.osWorks.api.exceptionHandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.miller.osWorks.domain.exception.BussinessException;
import com.miller.osWorks.domain.exception.NotFoundEntityException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	@Autowired
	private MessageSource messageSource;

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		Problem problem = new Problem();
		ArrayList<Problem.Field> fields = new ArrayList<Problem.Field>();
		
		for(ObjectError error : ex.getBindingResult().getAllErrors()) {
			
			String name = ((FieldError) error).getField() ;
			String message = messageSource.getMessage(error, LocaleContextHolder.getLocale());
			
			fields.add(new Problem.Field(name, message));
		}
		
		problem.setStatus(status.value());
		problem.setTitle("Um ou mais campos estão inválidos, preencha coretamente");
		problem.setLocalDateTime(OffsetDateTime.now());
		problem.setFields(fields);

		return super.handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	@ExceptionHandler(BussinessException.class)
	public ResponseEntity<Object> handlerBussiness(BussinessException ex, WebRequest request) {
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		return handleExceptionInternal(ex, createProblem(status, ex, null, null), new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(NotFoundEntityException.class)
	public ResponseEntity<Object> handlerNotFoundEntityException(NotFoundEntityException ex, WebRequest request) {
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		
		return handleExceptionInternal(ex, createProblem(status, ex, null, null), new HttpHeaders(), status, request);
	}
	
	private Problem createProblem(HttpStatus httpStatus, RuntimeException ex, String title, ArrayList<Problem.Field> fields) {
		
		Problem problem = new Problem();
		
		problem.setStatus(httpStatus.value());
		problem.setTitle(ex.getMessage());
		problem.setLocalDateTime(OffsetDateTime.now());
		
		if(title != null) {
			problem.setTitle(title);
		}
		
		if(fields != null) {
			problem.setFields(fields);
		}
		
		return problem;
		
	}

}
