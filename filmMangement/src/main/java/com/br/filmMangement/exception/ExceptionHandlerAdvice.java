package com.br.filmMangement.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice {

	@SuppressWarnings("rawtypes")
	@ExceptionHandler(ControllerException.class)
	public ResponseEntity handleException(ControllerException e) {
		return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
	}
}
