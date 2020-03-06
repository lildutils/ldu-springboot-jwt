package com.lildutils.springboot.jwt.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.lildutils.springboot.jwt.service.ex.LDuJwtException;

@ControllerAdvice
public class LDuJwtControllerAdvice extends ResponseEntityExceptionHandler
{
	@ExceptionHandler(LDuJwtException.class)
	@ResponseBody
	ResponseEntity<?> handleLDuJWTException( LDuJwtException e )
	{
		return super.handleExceptionInternal( e, e.getMessage(), null, HttpStatus.UNAUTHORIZED, null );
	}

}
